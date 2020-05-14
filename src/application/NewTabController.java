package application;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import API.CompanyInfo;
import API.Earnings;
import API.EarningsApi;
import API.Financials;
import API.FinancialsApi;
import API.StockGraph;
import Services.graphService;
import Services.jsonStringService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class NewTabController {

	private static String TICKER;
	private static boolean watchListProperty;
	AppBrowser wiki, seekAlpha, googleNews, companySite, transcript;
	XYChart.Series<String, Number> stockGraphSeries = new XYChart.Series<>();
	XYChart.Series<String, Number> earningsGraphSeries = new XYChart.Series<>();

	@FXML
	private NumberAxis yAxisStockGraph;
	@FXML
	private NumberAxis yAxisEarningsGraph;
	@FXML
	private CategoryAxis xAxisEarningsGraph;
	@FXML
	private CategoryAxis xAxisStockGraph;
	@FXML
	private LineChart<String, Number> stockGraph, earningsGraph;
	@FXML
	private TextField searchBar;
	@FXML
	private Text timeText;
	@FXML
	private ListView<Text> listView;
	// @FXML
	// private TableView<FinancialTableObj> tableViewFinancials;
	@FXML
	private AnchorPane wikiAnchorPane, seekAlphaPane, googleNewsPane, companySitePane, transcriptPane;
	@FXML
	private GridPane financialGridPane;
	graphService service;

	@FXML
	public void initialize() throws IOException {

		
		wiki = new AppBrowser(wikiAnchorPane);
		companySite= new AppBrowser(companySitePane);
		googleNews = new AppBrowser(googleNewsPane);
		stockGraph.setAnimated(false);
	
	}

	public String getTicker() {
		return TICKER;
	}

	public void setTicker(String txt) {
		TICKER = txt;
	}

	public void loadPage(String ticker) {
		setStockGraphSeries(ticker, "1y", ticker.toUpperCase() + ": " + "1 Year");
		setCompanyInfo(ticker);
		setCompanyFinancials(ticker + "/financials/");
		setEarnings(ticker);
		getWikiPage(ticker);
		getGoogleNews(ticker);
		getCompanySite(ticker);

	}



	public void setStockGraphSeries(String newTicker, String arg, String label) {
		StockGraph test = new StockGraph();
		graphService service = new graphService();
		service.setTicker(newTicker);
		service.setPeriodParameter(arg);
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@SuppressWarnings("unchecked")
			@Override
			public void handle(final WorkerStateEvent workerStateEvent) {

				if (workerStateEvent.getSource().getValue() != null) {
					// get series and set data on graph.
					stockGraph.getData().clear();
					stockGraphSeries.getData().clear();
					stockGraphSeries = (XYChart.Series<String, Number>) workerStateEvent.getSource().getValue();
					stockGraph.getData().add(stockGraphSeries);
					test.setSeries(stockGraphSeries);
					// set axis bounds
					yAxisStockGraph.setLowerBound(test.getLowest());
					yAxisStockGraph.setUpperBound(test.getHighest());
					xAxisStockGraph.setTickLabelsVisible(false);
					xAxisStockGraph.setTickMarkVisible(false);
					// set the newticker
					timeText.setText(label);

				}

			}

		});
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(final WorkerStateEvent workerStateEvent) {
				alertBox("data not available for symbol:" + newTicker.toUpperCase());

			}

		});
		service.start();
	}

	public void setCompanyInfo(String ticker) {

		Gson gson = new Gson();
		jsonStringService service = new jsonStringService();
		service.setUrl("https://api.iextrading.com/1.0/stock/" + ticker + "/company");
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(final WorkerStateEvent workerStateEvent) {
				String stringToParse = (String) workerStateEvent.getSource().getValue();
				CompanyInfo companyInfo = gson.fromJson(stringToParse, CompanyInfo.class);
				ObservableList<Text> names = FXCollections.observableArrayList(
						new Text("Company Name: " + companyInfo.getCompanyName()),
						new Text("CEO: " + companyInfo.getCEO()), new Text("Exchange: " + companyInfo.getExchange()),
						new Text("Industry: " + companyInfo.getIndustry()),
						new Text("Sector: " + companyInfo.getSector()),
						new Text("Website: " + companyInfo.getWebsite()));
				Text text = new Text("Description: " + companyInfo.getDescription());
				text.wrappingWidthProperty().bind(listView.widthProperty());

				listView.setItems(names);
				listView.getItems().add(text);

			}
		});
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(final WorkerStateEvent workerStateEvent) {

			}
		});

		service.start();

	}

	public void setCompanyFinancials(String ticker) {

		Gson gson = new Gson();
		jsonStringService service = new jsonStringService();
		service.setUrl("https://api.iextrading.com/1.0/stock/" + ticker);
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(final WorkerStateEvent workerStateEvent) {
				Label label0, label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11,
						label12, label13, label14, label15, label16, label17, label18, label19;
				String[] nameArr = { "Report Date", "Cost of Revenue", "Gross Profit", "Net Income",
						"Operating Expense", "Current Debt", "Total Debt", "Research and Development", "Total Revenue",
						"Operating Income", "Operating Revenue", "Cash Flow", "Current Cash", "Operating Gains Losses",
						"Shareholder Equity", "Total Cash", "Total assests", "Total Liabilities", "Current Assets",
						"Cash Change",

				};

				financialGridPane.getChildren().clear();
				String stringToParse = (String) workerStateEvent.getSource().getValue();
				// serialise Json String into FinancialsApi object and gets anrray of Financial
				// Objects containing table data
				FinancialsApi finApi = gson.fromJson(stringToParse, FinancialsApi.class);
				Financials[] finarr = finApi.getFinancials();
				int count = 1;
				ArrayList<Label> list = new ArrayList<>();
				for (int i = 0; i < 20; i++) {
					Label label = new Label(nameArr[i]);
					label.setFont(new Font("Cambria", 15));
					financialGridPane.add(label, 0, i);
				}
				for (int i = finarr.length - 1; i >= 0; i--) {
					// create the new labels to be added to the pane
					label0 = new Label(finarr[i].getReportDate());
					label1 = new Label(finarr[i].getCostOfRevenue());
					label2 = new Label(finarr[i].getGrossProfit());
					label3 = new Label(finarr[i].getNetIncome());
					label4 = new Label(finarr[i].getOperatingExpense());
					label5 = new Label(finarr[i].getCurrentDebt());
					label6 = new Label(finarr[i].getTotalDebt());
					label7 = new Label(finarr[i].getResearchAndDevelopment());
					label8 = new Label(finarr[i].getTotalRevenue());
					label9 = new Label(finarr[i].getOperatingIncome());
					label10 = new Label(finarr[i].getOperatingRevenue());
					label11 = new Label(finarr[i].getCashFlow());
					label12 = new Label(finarr[i].getCurrentCash());
					label13 = new Label(finarr[i].getOperatingGainsLosses());
					label14 = new Label(finarr[i].getShareholderEquity());
					label15 = new Label(finarr[i].getTotalCash());
					label16 = new Label(finarr[i].getTotalAssets());
					label17 = new Label(finarr[i].getTotalLiabilities());
					label18 = new Label(finarr[i].getCurrentAssets());
					label19 = new Label(finarr[i].getCashChange());
					// add nodes to pane
					financialGridPane.add(label0, count, 0);
					financialGridPane.add(label1, count, 1);
					financialGridPane.add(label2, count, 2);
					financialGridPane.add(label3, count, 3);
					financialGridPane.add(label4, count, 4);
					financialGridPane.add(label5, count, 5);
					financialGridPane.add(label6, count, 6);
					financialGridPane.add(label7, count, 7);
					financialGridPane.add(label8, count, 8);
					financialGridPane.add(label9, count, 9);
					financialGridPane.add(label10, count, 10);
					financialGridPane.add(label11, count, 11);
					financialGridPane.add(label12, count, 12);
					financialGridPane.add(label13, count, 13);
					financialGridPane.add(label14, count, 14);
					financialGridPane.add(label15, count, 15);
					financialGridPane.add(label16, count, 16);
					financialGridPane.add(label17, count, 17);
					financialGridPane.add(label18, count, 18);
					financialGridPane.add(label19, count, 19);

					count++;
					// set font
					label0.setFont(new Font("Cambria", 25));
					label1.setFont(new Font("Cambria bold", 15));
					label2.setFont(new Font("Cambria bold", 15));
					label3.setFont(new Font("Cambria bold", 15));
					label4.setFont(new Font("Cambria bold", 15));
					label5.setFont(new Font("Cambria bold", 15));
					label6.setFont(new Font("Cambria bold", 15));
					label7.setFont(new Font("Cambria bold", 15));
					label8.setFont(new Font("Cambria bold", 15));
					label9.setFont(new Font("Cambria bold", 15));
					label10.setFont(new Font("Cambria bold", 15));
					label11.setFont(new Font("Cambria bold", 15));
					label12.setFont(new Font("Cambria bold", 15));
					label13.setFont(new Font("Cambria bold", 15));
					label14.setFont(new Font("Cambria bold", 15));
					label15.setFont(new Font("Cambria bold", 15));
					label16.setFont(new Font("Cambria bold", 15));
					label17.setFont(new Font("Cambria bold", 15));
					label18.setFont(new Font("Cambria bold", 15));
					label19.setFont(new Font("Cambria bold", 15));
				}

			}
		});
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(final WorkerStateEvent workerStateEvent) {

			}
		});

		service.start();

	}

	public void setEarnings(String ticker) {

		Gson gson = new Gson();
		jsonStringService service = new jsonStringService();
		service.setUrl("https://api.iextrading.com/1.0/stock/" + ticker + "/earnings");
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(final WorkerStateEvent workerStateEvent) {
				double highest = 0.0;
				double lowest = 999999.0;
				String stringToParse = (String) workerStateEvent.getSource().getValue();
				// serialise Json String into FinancialsApi object and gets anrray of Financial
				// Objects containing table data
				EarningsApi finApi = gson.fromJson(stringToParse, EarningsApi.class);
				Earnings[] earnings = finApi.getEarnings();
				// add date to series and format graph bounds
				earningsGraphSeries.getData().clear();
				for (int i = earnings.length - 1; i >= 0; i--) {
					String x = earnings[i].getEPSReportDate();
					double y = Double.parseDouble(earnings[i].getActualEPS());
					if (y < lowest) {
						lowest = y;
					}
					if (y > highest) {
						highest = y;
					}
					earningsGraphSeries.getData().add(new XYChart.Data<>(x, y));
				}
				// set axis bounds
				yAxisEarningsGraph.setLowerBound(lowest);
				yAxisEarningsGraph.setUpperBound(highest);
				earningsGraph.getData().add(earningsGraphSeries);
			}
		});
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(final WorkerStateEvent workerStateEvent) {

			}
		});

		service.start();

	}
	public void getCompanySite(String ticker) {
		Gson gson = new Gson();
		jsonStringService service = new jsonStringService();
		service.setUrl("https://api.iextrading.com/1.0/stock/" + ticker + "/company");
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(final WorkerStateEvent workerStateEvent) {
				String stringToParse = (String) workerStateEvent.getSource().getValue();
				CompanyInfo companyInfo = gson.fromJson(stringToParse, CompanyInfo.class);
				companySite.loadURL(companyInfo.getWebsite());

			}
		});
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(final WorkerStateEvent workerStateEvent) {
				// handle case if failed
			}
		});

		service.start();
	}
	public void getWikiPage(String ticker) {
		// gets company name from IEX api in background thread to search wikipedia
		Gson gson = new Gson();
		jsonStringService service = new jsonStringService();
		service.setUrl("https://api.iextrading.com/1.0/stock/" + ticker + "/company");
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(final WorkerStateEvent workerStateEvent) {
				String stringToParse = (String) workerStateEvent.getSource().getValue();
				CompanyInfo companyInfo = gson.fromJson(stringToParse, CompanyInfo.class);
				wiki.loadURL("https://en.wikipedia.org/wiki/" + companyInfo.getCompanyName());

			}
		});
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(final WorkerStateEvent workerStateEvent) {
				// handle case if failed
			}
		});

		service.start();
	}

	private void getGoogleNews(String ticker) {
		googleNews.loadURL("https://news.google.com/search?q=" + ticker.toLowerCase() + "&hl=en-US&gl=US&ceid=US%3Aen");

	}

	public void getSeekAlphaPage(String ticker) {
		seekAlpha.loadURL("https://seekingalpha.com/symbol/" + ticker.toUpperCase());
	}

	public void alertBox(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("ERROR!");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@FXML
	private void set5y() {
		setStockGraphSeries(TICKER, "5y", TICKER.toUpperCase() + ": " + "5 Year");

	}

	@FXML
	private void set2y() {
		setStockGraphSeries(TICKER, "2y", TICKER.toUpperCase() + ": " + "2 Year");
	}

	@FXML
	private void set1y() {
		setStockGraphSeries(TICKER, "1y", TICKER.toUpperCase() + ": " + "1 Year");
	}

	@FXML
	private void set6m() {
		setStockGraphSeries(TICKER, "6m", TICKER.toUpperCase() + ": " + "6 Months");
	}

	@FXML
	private void set3m() {
		setStockGraphSeries(TICKER, "3m", TICKER.toUpperCase() + ": " + "3 Months");
	}

	@FXML
	private void set1m() {
		setStockGraphSeries(TICKER, "1m", TICKER.toUpperCase() + ": " + "1 Month");
	}

	@FXML
	// 1 minute data need to be handeled differently when parsing json
	private void set1d() {
		 setStockGraphSeries(TICKER, "1d", TICKER.toUpperCase() + ": " + "1 day");
	}

	@FXML
	private void setFinancialsAnnual() {
		setCompanyFinancials(TICKER + "/financials/" + "?period=annual");
	}

	@FXML
	private void setFinancialsQuarterly() {
		setCompanyFinancials(TICKER + "/financials/");
	}

	public static boolean isWatchListProperty() {
		return watchListProperty;
	}

	public static void setWatchListProperty(boolean watchListProperty) {
		NewTabController.watchListProperty = watchListProperty;
	}

}
