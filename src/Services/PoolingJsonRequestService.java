package Services;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import API.JSONrequest;
import application.screenerController;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class PoolingJsonRequestService extends Service<String>{
	private HttpGet httpget;
	private CloseableHttpClient httpclient;
	private HttpClientContext context;
	

	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
			@Override
			protected String call() throws Exception {
				// TODO Auto-generated method stub
				
				return  null;

			}

		};

	}

}
