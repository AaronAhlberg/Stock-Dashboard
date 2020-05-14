package API;

public class tickerData {
	 private EarningsApi earnings;

	    private CompanyInfo company;

	    private Chart[] chart;

	    private Financials financials;

	    public EarningsApi getEarnings ()
	    {
	        return earnings;
	    }

	    public void setEarnings (EarningsApi earnings)
	    {
	        this.earnings = earnings;
	    }

	    public CompanyInfo getCompany ()
	    {
	        return company;
	    }

	    public void setCompany (CompanyInfo company)
	    {
	        this.company = company;
	    }

	    public Chart[] getChart ()
	    {
	        return chart;
	    }

	    public void setChart (Chart[] chart)
	    {
	        this.chart = chart;
	    }

	    public Financials getFinancials ()
	    {
	        return financials;
	    }

	    public void setFinancials (Financials financials)
	    {
	        this.financials = financials;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [earnings = "+earnings+", company = "+company+", chart = "+chart+", financials = "+financials+"]";
	    }
}
