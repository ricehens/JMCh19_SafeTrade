import java.lang.reflect.*;
import java.util.*;

/**
 * Represents a stock exchange. A <code>StockExchange</code> keeps a
 * <code>HashMap</code> of stocks, keyed by a stock symbol. It has methods to
 * list a new stock, request a quote for a given stock symbol, and to place a
 * specified trade order.
 * @author Andrew Yuan
 * @author Eric Shen
 * @author Sophia Yang
 * @version 2022-03-09
 */
public class StockExchange
{
    private Map<String, Stock> listedStocks;

    /**
     * Constructs a StockExchange.
     */
    public StockExchange()
    {
        listedStocks = new HashMap<String, Stock>();
    }

    /**
     * Returns a quote for a given stock.
     * @param symbol stock symbol
     * @return a text message that contains the quote.
     */
    public String getQuote(String symbol)
    {
        if (listedStocks.containsKey(symbol)) {
            return listedStocks.get(symbol).getQuote();
        }
        return symbol + " not found";
    }

    /**
     * Adds a new stock with given parameters to the listed stocks.
     * @param symbol stock symbol
     * @param name full company name
     * @param price opening stock price
     */
    public void listStock(String symbol, String name, double price)
    {
        Stock stock = new Stock(symbol, name, price);
        listedStocks.put(symbol, stock);
    }

    /**
     * Places a trade order by calling <code>stock.placeOrder</code>
     * for the stock specified by the stock symbol in the trade order.
     * @param order a trading order to be placed with this stock exchange
     */
    public void placeOrder(TradeOrder order)
    {
        if (listedStocks.containsKey(order.getSymbol())) {
            Stock stock = listedStocks.get(order.getSymbol());
            stock.placeOrder(order);
        }
        else {
            order.getTrader().receiveMessage(order.getSymbol() + " not found");
        }
    }

    //
    // The following are for test purposes only
    //
    
    /**
     * Returns the listed stocks.
     * @return listedStocks
     */
    protected Map<String, Stock> getListedStocks()
    {
        return listedStocks;
    }
    
    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this StockExchange.
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
