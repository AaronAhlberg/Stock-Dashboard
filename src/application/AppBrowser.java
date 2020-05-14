package application;


import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class AppBrowser {
	private WebView myWebView;
	private WebEngine engine;
	//mobile user agent
	private String userAgent = "Mozilla/5.0 (Linux; Android 7.0; SM-G930V Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.125 Mobile Safari/537.36";
	private String URL="";

	@SuppressWarnings("static-access")
	AppBrowser(AnchorPane pane) {
	
		
		VBox vbox= new VBox();
		vbox.fillWidthProperty().set(true);
		vbox.setMinHeight(Control.USE_COMPUTED_SIZE);
		vbox.setPrefHeight(Control.USE_COMPUTED_SIZE);
		vbox.setPrefWidth(Control.USE_COMPUTED_SIZE);
		myWebView = new WebView();
		engine = myWebView.getEngine();
		myWebView.setMinWidth(10.0);
		myWebView.setMinHeight(700.0);
		myWebView.setPrefWidth(Control.USE_COMPUTED_SIZE);
		myWebView.setPrefHeight(Control.USE_COMPUTED_SIZE);
		ToolBar bar = new ToolBar();
		bar.setMinWidth(Control.USE_COMPUTED_SIZE);
		bar.setMinHeight(Control.USE_COMPUTED_SIZE);
		bar.setPrefWidth(Control.USE_COMPUTED_SIZE);
		bar.setPrefHeight(Control.USE_COMPUTED_SIZE);
		//add back btn and set onclick listener to execute script
		Button backBtn = new Button("back");
		backBtn.setOnAction((event) -> {
			   Platform.runLater(() -> {
			        engine.executeScript("history.back()");
			    });
		});
		//add foward btn and set onclick listener to execute script
		Button fowardBtn = new Button("forward");
		fowardBtn.setOnAction((event) -> {
			   Platform.runLater(() -> {
			        engine.executeScript("history.forward()");
			    });
		});
		//add refresh btn and set onclick listener to execute script
		Button refresh = new Button("refresh");
		refresh.setOnAction((event) -> {
			   Platform.runLater(() -> {
			     // loadURL(getURL());
				   engine.executeScript("location.reload()");
			    });
		});
		TextField urlBox = new TextField();
	
		urlBox.setEditable(true);
		urlBox.setPrefHeight(Control.USE_COMPUTED_SIZE);
		urlBox.setPrefWidth(400);
		//urlBox.textProperty().bind(engine.locationProperty());
		  engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
	            if (Worker.State.SUCCEEDED.equals(newValue)) {
	                urlBox.setText(engine.getLocation());
	            }
	        });

		
		bar.getItems().addAll(backBtn,fowardBtn,refresh,urlBox);
		pane.setLeftAnchor(vbox, 0.0);
		pane.setRightAnchor(vbox, 0.0);
		pane.setBottomAnchor(vbox, 0.0);
		pane.setTopAnchor(vbox, 0.0);
		vbox.getChildren().addAll(bar,myWebView);
		
		pane.getChildren().add(vbox);
		

	}

	public void loadURL(String url) {
		setURL(url);
		engine.setUserAgent(userAgent);
		engine.load(url);
	}
	

	public WebView getMyWebView() {
		return myWebView;
	}

	public void setMyWebView(WebView myWebView) {
		this.myWebView = myWebView;
	}

	public WebEngine getEngine() {
		return engine;
	}

	public void setEngine(WebEngine engine) {
		this.engine = engine;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getURL() {
		URL =engine.getLocation();
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

}
