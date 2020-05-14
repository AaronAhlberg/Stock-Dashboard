package application;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import API.IexBatchRequest;
import API.Symbols;
import API.tickerData;
import Services.PoolingJsonRequestService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Controller {
	//private String TRADE_HOME = "https://us.etrade.com/e/t/user/login";
	 private String TRADE_HOME="https://gdcdyn.interactivebrokers.com/sso/Login";
	@FXML
	public TabPane searchTabPane;
	@FXML
	private TextField searchBar;
	@FXML
	private ListView<String> watchingListView;
	@FXML
	private AnchorPane tradePane;
	@FXML
	private SplitPane splitPane;
	private AppBrowser trade;
	@FXML
	private Button watchButton;
	static NewTabController control;

	@FXML
	public void initialize() throws IOException, InterruptedException, URISyntaxException {
		// sets listener for enter key with searchbar
		searchBar.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				createTab();
			}
		});
		searchTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
				try {
				String ticker = searchTabPane.getSelectionModel().getSelectedItem().getText().toUpperCase();
				control.setTicker(ticker);
				searchBar.setText(ticker);
				//changes the listener and label for watch button 
				watchButtonClick();
				}
				catch(NullPointerException e) {
					searchBar.setText("");
					watchButton.setText("");
					watchButton.setOnAction((event) -> {
					});
				}
		
			}
		});
		trade = new AppBrowser(tradePane);
		trade.loadURL(TRADE_HOME);
		loadPref();
	

	}
	@FXML
	private void stockScreener() {

		
	}
	@FXML
	private void searchSymbol() throws IOException {
		createTab();
	}

	@FXML
	private void minimize() {
		splitPane.setDividerPositions(.99);
	}

	@FXML
	private void maximize() {
		splitPane.setDividerPositions(.01);
	
	}

	@FXML
	private void restore() {
		splitPane.setDividerPositions(.6);
	}

	@FXML

	private void loadPref() {

	}
	@FXML
	private void watchButtonClick() {
		String ticker=searchTabPane.getSelectionModel().getSelectedItem().getText().toUpperCase();
		
		if(!watchingListView.getItems().contains(ticker)){
			watchButton.setText("Watch");
			watchButton.setOnAction((event) -> {
				addToWatchList(ticker);
				  watchButton.setText("UnWatch");
				  watchButtonClick();
			});
		}
		else {
			watchButton.setText("Unwatch");
			watchButton.setOnAction((event) -> {
				removeFromWatchList(ticker);
				  watchButton.setText("Watch");
				  watchButtonClick();
			});
		}
		

	}
	private void addToWatchList(String tick) {
		watchingListView.getItems().add(tick);
	}

	private void removeFromWatchList(String ticker) {
		if (watchingListView.getItems().contains(ticker)) {
			watchingListView.getItems().remove(ticker);
		}
	}

	@FXML
	public void handleMouseClick(MouseEvent arg0) {
		String text = watchingListView.getSelectionModel().getSelectedItem();
		if(text!=null)
		createTab(text);
	}
	//returns index of opened tab else returns -1 if tab doesnt exist
	private int tabOpened(String ticker) {
		for (int i = 0; i < searchTabPane.getTabs().size(); i++) {
			if (searchTabPane.getTabs().get(i).getText().equalsIgnoreCase(ticker)) {
				System.out.println(ticker + "is already created");
				return i;
			}
		}

		return -1;
	}

	public void createTab() {
		String ticker = searchBar.getText().toUpperCase();
		int temp = tabOpened(ticker);
		if (temp != -1) {
			searchTabPane.getSelectionModel().select(searchTabPane.getTabs().get(temp));
		} else {
			try {
				Tab tab = new Tab(ticker);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("NewTab.fxml"));
				Parent root;
				root = loader.load();
				control = loader.getController();
				control.loadPage(searchBar.getText().toUpperCase());
				tab.setContent(root);
				searchTabPane.getTabs().add(tab);
				searchTabPane.getSelectionModel().select(tab);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void createTab(String ticker) {
		ticker.toUpperCase();
		int temp = tabOpened(ticker);
		if (temp != -1) {
			searchTabPane.getSelectionModel().select(searchTabPane.getTabs().get(temp));
		} else {
			try {
				Tab tab = new Tab(ticker);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("NewTab.fxml"));
				Parent root;
				root = loader.load();
				control = loader.getController();
				control.loadPage(ticker);
				tab.setContent(root);
				searchTabPane.getTabs().add(tab);
				searchTabPane.getSelectionModel().select(tab);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
