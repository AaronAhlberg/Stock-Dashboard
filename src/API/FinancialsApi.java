package API;

public class FinancialsApi {
    private String symbol;

    private Financials[] financials;

    public String getSymbol ()
    {
        return symbol;
    }

    public void setSymbol (String symbol)
    {
        this.symbol = symbol;
    }

    public Financials[] getFinancials ()
    {
        return financials;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [symbol = "+symbol+", financials = "+financials+"]";
    }
}
