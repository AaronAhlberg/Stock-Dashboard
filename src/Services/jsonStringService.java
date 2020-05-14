package Services;

import API.JSONrequest;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class jsonStringService extends Service<String> {

	private String url="";
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
			@Override
			protected String call() throws Exception {
				// TODO Auto-generated method stub
				JSONrequest jreq = new JSONrequest();
				return  jreq.getJsonString(url);

			}

		};

	}



}
