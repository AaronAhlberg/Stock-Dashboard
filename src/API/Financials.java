package API;

import java.text.NumberFormat;
import java.util.Locale;

public class Financials {
	 private String reportDate;
	 
	 private String costOfRevenue;

	    private String grossProfit;

	    private String netIncome;

	    private String operatingExpense;

	    private String currentDebt;

	    private String totalDebt;

	    private String researchAndDevelopment;

	    private String totalRevenue;

	    private String operatingIncome;

	    private String operatingRevenue;

	    private String cashFlow;

	    private String currentCash;

	    private String operatingGainsLosses;

	    private String shareholderEquity;

	    private String totalCash;

	    private String totalAssets;

	    private String totalLiabilities;

	    private String currentAssets;

	   

	    private String cashChange;

	    public String getCostOfRevenue ()
	    {
	        return format(costOfRevenue);
	    }

	    public String getGrossProfit ()
	    {
	        return format(grossProfit);
	    }

	    public String getNetIncome ()
	    {
	        return format(netIncome);
	    }

	    public String getOperatingExpense ()
	    {
	        return format(operatingExpense);
	    }

	    public String getCurrentDebt ()
	    {
	        return format(currentDebt);
	    }

	    public String getTotalDebt ()
	    {
	        return format(totalDebt);
	    }

	    public String getResearchAndDevelopment ()
	    {
	        return format(researchAndDevelopment);
	    }

	    public String getTotalRevenue ()
	    {
	        return format(totalRevenue);
	    }

	    public String getOperatingIncome ()
	    {
	        return format(operatingIncome);
	    }

	    public String getOperatingRevenue ()
	    {
	        return format(operatingRevenue);
	    }

	    public String getCashFlow ()
	    {
	        return format(cashFlow);
	    }

	    public String getCurrentCash ()
	    {
	        return format(currentCash);
	    }

	    public String getOperatingGainsLosses ()
	    {
	        return format(operatingGainsLosses);
	    }

	    public String getShareholderEquity ()
	    {
	        return format(shareholderEquity);
	    }

	    public String getTotalCash ()
	    {
	        return format(totalCash);
	    }

	    public String getTotalAssets ()
	    {
	        return format(totalAssets);
	    }

	    public String getTotalLiabilities ()
	    {
	        return format(totalLiabilities);
	    }

	    public String getCurrentAssets ()
	    {
	        return format(currentAssets);
	    }

	    public String getReportDate ()
	    {
	        return reportDate;
	    }

	    public String getCashChange ()
	    {
	        return format(cashChange);
	    }
	    public String format(String arg) {
			if(arg==null) {
				return "";
			}
			return ""+NumberFormat.getNumberInstance(Locale.US).format(Long.parseLong(arg));
			
			
		}

	    @Override
	    public String toString()
	    {
	        return "[costOfRevenue = "+costOfRevenue+", grossProfit = "+grossProfit+", netIncome = "+netIncome+", operatingExpense = "+operatingExpense+", currentDebt = "+currentDebt+", totalDebt = "+totalDebt+", researchAndDevelopment = "+researchAndDevelopment+", totalRevenue = "+totalRevenue+", operatingIncome = "+operatingIncome+", operatingRevenue = "+operatingRevenue+", cashFlow = "+cashFlow+", currentCash = "+currentCash+", operatingGainsLosses = "+operatingGainsLosses+", shareholderEquity = "+shareholderEquity+", totalCash = "+totalCash+", totalAssets = "+totalAssets+", totalLiabilities = "+totalLiabilities+", currentAssets = "+currentAssets+", reportDate = "+reportDate+", cashChange = "+cashChange+"]";
	    }
}
