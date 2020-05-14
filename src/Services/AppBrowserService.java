package Services;

import application.AppBrowser;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class AppBrowserService extends Service<String> {

private AppBrowser appBrowser;
private String url="";

public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}

public AppBrowser getAppBrowser() {
	return appBrowser;
}

public void setAppBrowser(AppBrowser appBrowser) {
	this.appBrowser = appBrowser;
}
	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
			@Override
			protected String call() throws Exception {
				appBrowser.loadURL(url);
				System.out.println("service started");
				return null;
				
			}
		};
	}

}