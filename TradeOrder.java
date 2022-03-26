import java.lang.reflect.*;


//data carrier object to pass data about a trade order to each other

/**
 * Represents a buy or sell order for trading a given number of shares of a
 * specified stock.
 * Uses a priority queue<tradeorder>
 * @author Andrew Yuan
 * @author Eric Shen
 * @author Sophia Yang
 * @version 2022-03-09
 * @author Assignment: JM Chapter 19 - SafeTrade
 * @author Sources: N/A
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
    // compare two tradeorder objects based on prices, ascending 
    // or descending comparator

    // TODO complete class
    /**
     * tradeorder constructor
     * @param trader this trader
     * @param symbol company symbol
     * @param buyOrder buy an order
     * @param marketOrder market order
     * @param numShares num of shares
     * @param price share price
     */
    public TradeOrder(Trader trader, String symbol, 
                    boolean buyOrder, boolean marketOrder, 
                    int numShares, double price) {
        this.trader = trader;
        this.symbol = symbol;
        this.buyOrder = buyOrder;
        this.marketOrder = marketOrder;
        this.numShares = numShares;
        this.price = price;
    }
    /**
     * get the trader
     * @return trader 
     */
    public Trader getTrader()
    {
        return trader;
    }

    /**
     * get symbol
     * @return symbol
     */
    public java.lang.String getSymbol()
    {
        return symbol;
    }

    /**
     * is buyorder
     * @return buyorder
     */
    public boolean isBuy()
    {
        return buyOrder;
    }
    
    /**
     * is sell order
     * @return true if not a buy order
     */
    public boolean isSell()
    {
        return !buyOrder;
    }

    /**
     * is market order
     * @return true if marketorder
     */
    public boolean isMarket()
    {
        return marketOrder;
    }

    /**
     * is limit order
     * @return not marketorder
     */
    public boolean isLimit()
    {
        return !marketOrder;
    }

    /**
     * get shares
     * @return num of shares
     */
    public int getShares()
    {
        return numShares;
    }

    /**
     * price per share
     * @return price per share for trade order
     */
    public double getPrice()
    {
        return price;
    }
    
    /**
     * subtract shares
     * @param shares recent shares
     */
    public void subtractShares(int shares)
    {
        if (shares > numShares)
        {
            throw new IllegalArgumentException(); 
        }
        else
        {
            numShares -= shares;
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
