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
    public Brokerage(StockExchange exchange) {
        traders = new TreeMap<String, Trader>();
        loggedTraders = new TreeSet<Trader>();
        this.exchange = exchange;
    }

    public int addUser(String name, String password) {
        if (name.length() < 4 || name.length() > 10) {
            return 1;
        }
        if (password.length() < 2 || password.length() > 10) {
            return 2;
        }
        if (traders.containsKey(name)) {
            return 3;
        }
        Trader trader = new Trader(this, name, password);
        traders.put(name, trader);
        return 0;
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
