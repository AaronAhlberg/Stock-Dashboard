package application;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.json.JSONArray;

import com.google.gson.Gson;

import API.IexBatchRequest;
import API.JSONrequest;
import API.Symbols;
import Services.IexBatchRequestService;
import Services.jsonStringService;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public class screenerController {
	

	// https://api.iextrading.com/1.0/stock/market/batch?symbols=aapl,fb&types=quote,chart&range=1y

	public void getSymbols() {
		Gson gson = new Gson();
		jsonStringService service = new jsonStringService();
		//fetches all symbols on IEX
		service.setUrl("https://api.iextrading.com/1.0/ref-data/symbols");
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(final WorkerStateEvent workerStateEvent) {
				IexBatchRequestService data = new IexBatchRequestService();			
				Symbols[] list= new Symbols[9000];
				String stringToParse = (String) workerStateEvent.getSource().getValue();
				JSONArray arr = new JSONArray(stringToParse);
				//add data into list of symbol objects
				for (int i = 0; i < arr.length(); i++) {
					Symbols sym = gson.fromJson(arr.get(i).toString(), Symbols.class);
				
					list[i]=sym;
					
				}
				
					System.out.println(arr.length());
					data.setArr(list);
					data.setChartRange("1y");
					data.setFinancialPeriod("annual");
					data.start();
			}
			
		});
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(final WorkerStateEvent workerStateEvent) {

			}
		});

		service.start();

	}
	public void getSymbols1() throws IOException, URISyntaxException {
		Gson gson = new Gson();
				Symbols[] list= new Symbols[9000];
				IexBatchRequest data = new IexBatchRequest();
				JSONrequest jreq= new JSONrequest();
				String stringToParse = jreq.getJsonString("https://api.iextrading.com/1.0/ref-data/symbols");
				JSONArray arr = new JSONArray(stringToParse);
				//add data into list of symbol objects
				for (int i = 0; i < arr.length(); i++) {
					Symbols sym = gson.fromJson(arr.get(i).toString(), Symbols.class);
				
					list[i]=sym;
					
					
				}
				try {
					System.out.println(arr.length());
					data.Request(list, "1y", "annual");
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


	}
	


}
