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
    }

    //getter methods
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
        int x = other.screenName.toLowerCase().hashCode();
        int y = screenName.toLowerCase().hashCode();
        return x-y;
        
    }

    public boolean equals(java.lang.Object other){
        if (other instanceof Trader){
            Trader t= (Trader) other;
            return (t.screenName.equals( this.screenName ));
        }
        else
        throw new ClassCastException(); 
    }

    public void openWindow()
    {
        
        myWindow = new TraderWindow(this);
        if (mailbox.size()!=0){
        for (String a: mailbox)
        {
        myWindow.showMessage(a);
        mailbox.remove(a);
        }
    }
    }

    public boolean hasMessages()
    {
        if (mailbox.size()!=0)
            return true;
        return false;

    }

    public void receiveMessage(java.lang.String msg)
    {
        mailbox.add(msg);
        if (myWindow!=null)
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
        Brokerage.getQuote(symbol);

    }

    public void placeOrder(TradeOrder order)
    {
        Brokerage.placeOrder(order);
    }

    public void quit()
    {
        Brokerage.logout();
        TraderWindow.getWindows() = null;
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
