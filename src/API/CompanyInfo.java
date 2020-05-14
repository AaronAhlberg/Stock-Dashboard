package API;

public class CompanyInfo {
	 private String[] tags;

	    private String sector;

	    private String symbol;

	    private String website;

	    private String CEO;

	    private String description;

	    private String issueType;

	    private String industry;

	    private String companyName;

	    private String exchange;

	    public String[] getTags ()
	    {
	        return tags;
	    }


	    public String getSector ()
	    {
	        return sector;
	    }


	    public String getSymbol ()
	    {
	        return symbol;
	    }


	    public String getWebsite ()
	    {
	        return website;
	    }


	    public String getCEO ()
	    {
	        return CEO;
	    }


	    public String getDescription ()
	    {
	        return description;
	    }


	    public String getIssueType ()
	    {
	        return issueType;
	    }


	    public String getIndustry ()
	    {
	        return industry;
	    }


	    public String getCompanyName ()
	    {
	        return companyName;
	    }


	    public String getExchange ()
	    {
	        return exchange;
	    }


	    @Override
	    public String toString()
	    {
	        return "ClassPojo [tags = "+tags+", sector = "+sector+", symbol = "+symbol+", website = "+website+", CEO = "+CEO+", description = "+description+", issueType = "+issueType+", industry = "+industry+", companyName = "+companyName+", exchange = "+exchange+"]";
	    }
}
