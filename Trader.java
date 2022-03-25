import java.lang.reflect.*;
import java.util.*;

/**
 * Represents a stock trader.
 */
public class Trader implements Comparable<Trader>
{
    private Brokerage brokerage;
    private String screenName, password;
    private TraderWindow myWindow;
    private Queue<String> mailbox;

    // TODO complete class

    public Trader(Brokerage brokerage,
              java.lang.String name,
              java.lang.String pswd)
    {
        this.brokerage = brokerage;
        screenName = name;
        password = pswd;
        mailbox = new LinkedList<String>();
    }

    // getter methods
    public java.lang.String getName()
    {
        return screenName;
    }

    public java.lang.String getPassword()
    {
        return password;
    }

    public int compareTo(Trader other)
    {
        return getName().compareToIgnoreCase(other.getName());
    }

    public boolean equals(java.lang.Object other) {
        if (other instanceof Trader) {
            Trader t = (Trader) other;
            return getName().equals(t.getName());
        }
        else
            throw new ClassCastException(); 
    }

    public void openWindow()
    {
        myWindow = new TraderWindow(this);
        if (mailbox.size() != 0){
            for (String a: mailbox)
            {
                myWindow.showMessage(a);
                mailbox.remove(a);
            }
        }
    }

    public boolean hasMessages()
    {
        return mailbox.size() != 0;
    }

    public void receiveMessage(java.lang.String msg)
    {
        mailbox.add(msg);
        if (myWindow != null)
        {
            for (String a: mailbox)
            {
                myWindow.showMessage(a);
                mailbox.remove(a);
            }
        }
    }

    public void getQuote(java.lang.String symbol)
    {
        brokerage.getQuote(symbol, this);
    }

    public void placeOrder(TradeOrder order)
    {
        brokerage.placeOrder(order);
    }

    public void quit()
    {
        brokerage.logout(this);
        myWindow = null;
    }

    //
    // The following are for test purposes only
    //
    protected Queue<String> mailbox()
    {
        return mailbox;
    }
    
    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this Trader.
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
                if ( field.getType().getName().equals( "Brokerage" ) )
                    str += separator + field.getType().getName() + " "
                        + field.getName();
                else
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
