package Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import com.google.gson.Gson;

import API.tickerData;

public class myCallable implements Callable<tickerData[]>{

    private final CloseableHttpClient httpClient;
    private final HttpContext context;
    private final HttpGet httpget;
    private tickerData temp;
    private  String[] tickList;
    private long startTime;


    public myCallable(CloseableHttpClient httpClient, HttpGet httpget, String[] strings) {
    	 startTime = System.currentTimeMillis();
    	this.tickList=strings;
        this.httpClient = httpClient;
        this.context = HttpClientContext.create();
        this.httpget = httpget;

    }

	@Override
	public tickerData[] call() throws Exception {
		tickerData[] dataList= new tickerData[100];
        try {
        
        	//create and retrieve response
        	startTime=System.currentTimeMillis();
            CloseableHttpResponse response = httpClient.execute(
                    httpget, context);
            try {
            	StringBuilder returnValue = new StringBuilder(6000000);
            	BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    			String userInput;
    			while ((userInput = reader.readLine()) != null) {
    				returnValue.append(userInput);
    			}
    		
    			
    			//parse response from json to array of tickerData objects
    			Gson gson = new Gson();
				JSONObject obj = new JSONObject(returnValue.toString());
				int i=0;
				for(String ticker:tickList)
				 {
					if(ticker!=null) {
					 temp = gson.fromJson(obj.get(ticker).toString(), tickerData.class);
					 System.out.println(temp.getCompany().getSymbol());
					 dataList[i]=temp;
					 i++;
					}
				}
				
				System.out.println("thread execution time "+(System.currentTimeMillis()-startTime));
				
              
            } finally {
                response.close();
            }
        } catch (ClientProtocolException ex) {
            // Handle protocol errors
        } catch (IOException ex) {
            // Handle I/O errors
        }
		return dataList;
	}

}
