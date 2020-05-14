package API;

public class EarningsApi {
	private Earnings[] earnings;

    private String symbol;

    public Earnings[] getEarnings ()
    {
        return earnings;
    }


    public String getSymbol ()
    {
        return symbol;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [earnings = "+earnings+", symbol = "+symbol+"]";
    }
}
