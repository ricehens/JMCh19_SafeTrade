import java.lang.reflect.*;
import java.util.*;

/**
 * Represents a brokerage.
 */
public class Brokerage implements Login
{
    private Map<String, Trader> traders;
    private Set<Trader> loggedTraders;
    private StockExchange exchange;

    // TODO complete class

    /**
     * Constructs a Brokerage as part of a certain StockExchange.
     * @param exchange the StockExchange it is for
     */
    public Brokerage(StockExchange exchange) {
        traders = new TreeMap<String, Trader>();
        loggedTraders = new TreeSet<Trader>();
        this.exchange = exchange;
    }
    
    /**
     * Registers a new trader with a given name and password.
     * @param name the screen name of the trader
     * @param password the password for the trader
     * @return 0 if successful, -1 if invalid screen name, 
     * -2 if invalid password, -3 if screen name already taken
     */
    public int addUser(String name, String password) {
        if (name.length() < 4 || name.length() > 10) {
            return -1;
        }
        if (password.length() < 2 || password.length() > 10) {
            return -2;
        }
        if (traders.containsKey(name)) {
            return -3;
        }
        Trader trader = new Trader(this, name, password);
        traders.put(name, trader);
        return 0;
    }

    /**
     * Requests a quote for a given stock from the stock 
     * exchange and passes it along to the trader
     * @param symbol the symbol of the Stock to request
     * @param trader the trader who requested a quote
     */
    public void getQuote(String symbol, Trader trader) {
        trader.receiveMessage(symbol);
    }
    
    /**
     * Logs in a trader with a given name and password.
     * @param name the screen name of the trader
     * @param password the password for the trader
     * @return 0 if successful, -1 if screen name not found, 
     * -2 if invalid password, -3 if user already logged in
     */
    public int login(String name, String password) {
        if (!traders.containsKey(name)) {
            return -1;
        }
        Trader trader = traders.get(name);
        if (!password.equals(trader.getPassword())) {
            return -2;
        }
        if (loggedTraders.contains(trader)) {
            return -3;
        }
        loggedTraders.add(trader);
        if (trader.hasMessages()) {
            trader.receiveMessage("Welcome to SafeTrade!");
            trader.openWindow();
        }
        return 0;
    }

    /**
     * Logs a trader out of the system.
     * @param trader the trader to log out
     */
    public void logout(Trader trader) {
        loggedTraders.remove(trader);
    }
    
    /**
     * Places an order on the StockExchange.
     * @param order the TradeOrder to place
     */
    public void placeOrder(TradeOrder order) {
        exchange.placeOrder(order);
    }

    //
    // The following are for test purposes only
    //
    protected Map<String, Trader> getTraders()
    {
        return traders;
    }

    protected Set<Trader> getLoggedTraders()
    {
        return loggedTraders;
    }

    protected StockExchange getExchange()
    {
        return exchange;
    }

    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this Brokerage.
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
