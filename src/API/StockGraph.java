package API;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.scene.chart.XYChart;

public class StockGraph {

	XYChart.Series<String, Number> series;
	private double lowest = 9999999.0;
	private double highest = 0.0;
	// gets graph data for specified periods, checks the data and set the lowest and
	// highest value to be passed for y axis upper and lower bounds.
	// Acceptable arguments:
	// /stock/aapl/chart/5y
	// /stock/aapl/chart/2y
	// /stock/aapl/chart/1y
	// /stock/aapl/chart/ytd
	// /stock/aapl/chart/6m
	// /stock/aapl/chart/3m
	// /stock/aapl/chart/1m
	// /stock/aapl/chart/1d
	// /stock/aapl/chart/date/20180129
	// /stock/aapl/chart/dynamic

	public XYChart.Series<String, Number> getGraphSeries(String ticker, String period) {

		JSONrequest jreq = new JSONrequest();
		String stringToParse = "";
		series = new XYChart.Series<>();
		try {
			stringToParse = jreq.getJsonString("https://api.iextrading.com/1.0/stock/" + ticker + "/chart/" + period);
		} catch (Exception e) {
			System.out.println("caught exception getting stock data");
			e.printStackTrace();
		}
		
		JSONArray arr = new JSONArray(stringToParse);
		if (!period.equals("1d")) {
			for (int i = 0; i < arr.length(); i++) {

				String x = arr.getJSONObject(i).get("date").toString();
				Double y = Double.parseDouble(arr.getJSONObject(i).get("close").toString());
				// check for highest and lowest value in series
				if (checkHighestLowest(y))
					series.getData().add(new XYChart.Data<>(x, y));
			}

		} else {
			for (int i = 0; i < arr.length(); i++) {

				String x = arr.getJSONObject(i).get("minute").toString();
				Double y = Double.parseDouble(arr.getJSONObject(i).get("marketClose").toString());
				// check for highest and lowest value in series
				if (checkHighestLowest(y))
					series.getData().add(new XYChart.Data<>(x, y));
			}
		}

		return series;
	}

	private boolean checkHighestLowest(Double y) {
		// check for highest and lowest value in series
		if (y < lowest) {
			lowest = y;
		}
		if (y > highest) {
			highest = y;
		}
		// wont add to series in case y value of -1 is returned. This happens on the
		// server side
		// in case of a blank time period.
		if (y > 0)
			return true;
		else
			return false;
	}

	public void setSeries(XYChart.Series<String, Number> ser) {
		series = ser;
		for (int i = 0; i < series.getData().size(); i++) {
			checkHighestLowest((Double) series.getData().get(i).getYValue());
		}
	}

	public Double getHighest() {
		return highest;
	}

	public Double getLowest() {
		return lowest;
	}

	public XYChart.Series<String, Number> getSeries() {
		return series;
	}

}
