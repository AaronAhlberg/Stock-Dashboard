package API;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import Services.myCallable;


public class IexBatchRequest {

	private String[] urlList = new String[100];
	private ArrayList<String[]> tickerList = new ArrayList<String[]>();
	PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();

	public List<Future<tickerData[]>> Request(Symbols[] arr, String chartRange, String financialPeriod) throws URISyntaxException {
		// set connection pool
		PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
		pool.setDefaultMaxPerRoute(10);
		pool.setMaxTotal(20);
		// creates list of urls that contain a batch request for 100 symbols
		String symbols = "";
		String[] tempArr = new String[100];
		int count = 0;
		int cnt = 0;
		System.out.println(arr.length);
		int i = 0;
		long StartTime = System.currentTimeMillis();
		while (arr[i] != null) {
			String ticker = "";
			ticker = arr[i].getSymbol();

			if (!ticker.contains("#") && !ticker.contains("+") && !ticker.contains("^")) {
				symbols += ticker + ",";
				tempArr[count] = ticker;
				count++;
			}
			if (i % 99 == 0 && i != 0) {
				String URL = "https://api.iextrading.com/1.0/stock/market/batch?symbols=" + symbols
						+ "&types=earnings,company,financials,chart&period=annual&range=1y";
				urlList[cnt] = URL;
				tickerList.add(tempArr);
				tempArr = new String[100];
				symbols = "";
				count = 0;
				cnt++;
			}
			if (i == arr.length - 1) {
				String URL = "https://api.iextrading.com/1.0/stock/market/batch?symbols=" + symbols
						+ "&types=earnings,company,financials,chart&period=annual&range=1y";
				urlList[i] = URL;
				tickerList.add(tempArr);
				symbols = "";
			}
			i++;
		}
	
		int x = 0;

		ExecutorService executor = Executors.newFixedThreadPool(12);
		// create a list to hold the Future object associated with Callable
		List<Future<tickerData[]>> list = new ArrayList<Future<tickerData[]>>();

		for (String obj : urlList) {
			if (obj != null) {
				// create new http client
				RequestConfig customizedRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES)
						.build();
				HttpClientBuilder customizedClientBuilder = HttpClients.custom()
						.setDefaultRequestConfig(customizedRequestConfig);
				CloseableHttpClient httpclient = customizedClientBuilder.setConnectionManager(pool).build();
				HttpGet httpget = new HttpGet(new URI(obj));
				String[] temp = tickerList.get(x);
				// Create MyCallable instance
				Callable<tickerData[]> callable = new myCallable(httpclient, httpget, temp);
				// submit Callable tasks to be executed by thread pool
				Future<tickerData[]> future = executor.submit(callable);
				// add Future to the list, we can get return value using Future
				list.add(future);
				x++;
			}
		}
	     for(Future<tickerData[]> fut : list){
	            
	                //print the return value of Future, notice the output delay in console
	                // because Future.get() waits for task to get completed
	            	tickerData[] temp;
						try {
							temp = fut.get();
//							for(tickerData z: temp) {
//						
//							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
	              
	     }
	     System.out.println(list.size());
	 	System.out.println("while loop time" + (System.currentTimeMillis() - StartTime));
	        //shut down the executor service now
	        executor.shutdown();
	        return list;
	}



}

// https://api.iextrading.com/1.0/stock/market/batch?symbols=&types=company,earnings,financials,chart&period=annual&range=1y
//
// String
// URL="https://api.iextrading.com/1.0/stock/market/batch?symbols="+symbols+"&types=company,earnings,financials,chart&period="+financialPeriod+"&range="+chartRange;
//
//
//

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
