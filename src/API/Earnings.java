package API;

public class Earnings {
	 private String announceTime;

	    private String numberOfEstimates;

	    private String yearAgoChangePercent;

	    private String estimatedEPS;

	    private String actualEPS;

	    private String fiscalEndDate;

	    private String symbolId;

	    private String EPSSurpriseDollar;

	    private String EPSReportDate;

	    private String estimatedChangePercent;

	    private String yearAgo;

	    private String fiscalPeriod;

	    private String consensusEPS;

	    public String getAnnounceTime ()
	    {
	        return announceTime;
	    }

	    public String getNumberOfEstimates ()
	    {
	        return numberOfEstimates;
	    }

	    public String getYearAgoChangePercent ()
	    {
	        return yearAgoChangePercent;
	    }

	    public String getEstimatedEPS ()
	    {
	        return estimatedEPS;
	    }

	    public String getActualEPS ()
	    {
	        return actualEPS;
	    }

	    public String getFiscalEndDate ()
	    {
	        return fiscalEndDate;
	    }

	    public String getSymbolId ()
	    {
	        return symbolId;
	    }

	    public String getEPSSurpriseDollar ()
	    {
	        return EPSSurpriseDollar;
	    }

	    public String getEPSReportDate ()
	    {
	        return EPSReportDate;
	    }


	    public String getEstimatedChangePercent ()
	    {
	        return estimatedChangePercent;
	    }

	    public String getYearAgo ()
	    {
	        return yearAgo;
	    }

	    public String getFiscalPeriod ()
	    {
	        return fiscalPeriod;
	    }

	    public String getConsensusEPS ()
	    {
	        return consensusEPS;
	    }


	    @Override
	    public String toString()
	    {
	        return "ClassPojo [announceTime = "+announceTime+", numberOfEstimates = "+numberOfEstimates+", yearAgoChangePercent = "+yearAgoChangePercent+", estimatedEPS = "+estimatedEPS+", actualEPS = "+actualEPS+", fiscalEndDate = "+fiscalEndDate+", symbolId = "+symbolId+", EPSSurpriseDollar = "+EPSSurpriseDollar+", EPSReportDate = "+EPSReportDate+", estimatedChangePercent = "+estimatedChangePercent+", yearAgo = "+yearAgo+", fiscalPeriod = "+fiscalPeriod+", consensusEPS = "+consensusEPS+"]";
	    }

}
