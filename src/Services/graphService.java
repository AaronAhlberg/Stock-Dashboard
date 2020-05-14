package Services;

import API.StockGraph;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;

public class graphService extends Service<XYChart.Series<String, Number>> {

	private String ticker = "";
	private String period = "";

	public final void setPeriodParameter(String arg) {
		period = arg;
		System.out.println(period);
	}

	public final void setTicker(String a) {
		ticker = a;
		System.out.println(ticker);
	}

	@Override
	protected Task<XYChart.Series<String, Number>> createTask() {
		return new Task<XYChart.Series<String, Number>>() {
			@Override
			protected XYChart.Series<String, Number> call() throws Exception {
				// TODO Auto-generated method stub
				StockGraph test = new StockGraph();
				return test.getGraphSeries(ticker, period);

			}

		};

	}
}
