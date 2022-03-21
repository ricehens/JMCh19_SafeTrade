import java.lang.reflect.*;


//data carrier object to pass data about a trade order to each other

/**
 * Represents a buy or sell order for trading a given number of shares of a
 * specified stock.
 * Uses a priority queue<tradeorder>
 */
public class TradeOrder
{
    private Trader trader;
    private String symbol;
    private boolean buyOrder;
    private boolean marketOrder;
    private int numShares;
    private double price;

   // PriorityQueue<TradeOrder> pq = new PriorityQueue<TradeOrder>();
    //compare two tradeorder objects based on prices, ascending 
    //or descending comparator


    // TODO complete class

    public Trader getTrader()
    {
        return trader;
    }

    public java.lang.String getSymbol()
    {
        return symbol;
    }

    public boolean isBuy()
    {
        return buyOrder;
    
    }
    public boolean isSell()
    {
        return !buyOrder;
    }

    public boolean isMarket()
    {
        return marketOrder;
    }

    public boolean isLimit()
    {
        return !marketOrder;
    }

    public int getShares()
    {
        return numShares;
    }

    //return price per share for trade order
    public double getPrice()
    {
        return price;
    }
    
    public void subtractShares(int shares)
    {
        if (shares>numShares)
            throw new IllegalArgumentException(); 
        else
        {
            numShares-= shares;
        }
        }


    //
    // The following are for test purposes only
    //
    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this TradeOrder.
     */
    public String toString()
    {
        String str = this.getClass().getName() + "[";
        String separator = "";

        Field[] fields = this.getClass().getDeclaredFields();

        for ( Field field : fields )
        {
            try
            {
                str += separator + field.getType().getName() + " "
                    + field.getName() + ":" + field.get( this );
            }
            catch ( IllegalAccessException ex )
            {
                System.out.println( ex );
            }

            separator = ", ";
        }

        return str + "]";
    }
}
