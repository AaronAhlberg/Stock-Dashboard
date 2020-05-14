package Services;

import java.util.List;
import java.util.concurrent.Future;

import API.IexBatchRequest;
import API.Symbols;
import API.tickerData;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class IexBatchRequestService extends Service<List<Future<tickerData[]>>>{
	private Symbols[] arr;
	private String chartRange;
	private String financialPeriod;
	@Override
	protected Task<List<Future<tickerData[]>>> createTask() {
		return new Task<List<Future<tickerData[]>>>() {
			@Override
			protected List<Future<tickerData[]>> call() throws Exception {
				// TODO Auto-generated method stub
				IexBatchRequest a = new IexBatchRequest();
				return  a.Request(arr, chartRange, financialPeriod);

			}

		};

	}
	public Symbols[] getArr() {
		return arr;
	}
	public void setArr(Symbols[] arr) {
		this.arr = arr;
	}
	public String getChartRange() {
		return chartRange;
	}
	public void setChartRange(String chartRange) {
		this.chartRange = chartRange;
	}
	public String getFinancialPeriod() {
		return financialPeriod;
	}
	public void setFinancialPeriod(String financialPeriod) {
		this.financialPeriod = financialPeriod;
	}
}
