package API;

public class Symbols {
	  private String iexId;

	    private String symbol;

	    private String name;

	    private String type;

	    private String date;

	    private String isEnabled;

	    public String getIexId ()
	    {
	        return iexId;
	    }

	    public void setIexId (String iexId)
	    {
	        this.iexId = iexId;
	    }

	    public String getSymbol ()
	    {
	        return symbol;
	    }

	    public void setSymbol (String symbol)
	    {
	        this.symbol = symbol;
	    }

	    public String getName ()
	    {
	        return name;
	    }

	    public void setName (String name)
	    {
	        this.name = name;
	    }

	    public String getType ()
	    {
	        return type;
	    }

	    public void setType (String type)
	    {
	        this.type = type;
	    }

	    public String getDate ()
	    {
	        return date;
	    }

	    public void setDate (String date)
	    {
	        this.date = date;
	    }

	    public String getIsEnabled ()
	    {
	        return isEnabled;
	    }

	    public void setIsEnabled (String isEnabled)
	    {
	        this.isEnabled = isEnabled;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [iexId = "+iexId+", symbol = "+symbol+", name = "+name+", type = "+type+", date = "+date+", isEnabled = "+isEnabled+"]";
	    }

}
