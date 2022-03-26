import java.lang.reflect.*;
import java.util.*;

/**
 * Represents a stock trader.
 *
 * @author Andrew Yuan
 * @author Eric Shen
 * @author Sophia Yang
 * @version 2022-03-09
 * @author Assignment: JM Chapter 19 - SafeTrade
 * @author Sources: N/A
 */
public class Trader implements Comparable<Trader> {
    private Brokerage     brokerage;
    private String        screenName;
    private String        password;
    private TraderWindow  myWindow;
    private Queue<String> mailbox;

    /**
     * Trader class constructor
     * @param brokerage brokerage of trader 
     * @param name trader screen name
     * @param pswd account password
     */
    public Trader(Brokerage brokerage, String name, String pswd) {
        this.brokerage = brokerage;
        screenName = name;
        password = pswd;
        mailbox = new LinkedList<String>();
    }


    // getter methods
    /**
     * Returns name of trader
     * @return screenname
     */
    public java.lang.String getName() {
        return screenName;
    }

    /**
     * Returns user password
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }

    /**
     * Compare traders
     * @param other the trader to compare to
     * @return int compare
     */
    public int compareTo(Trader other) {
        return getName().compareToIgnoreCase(other.getName());
    }

    /**
     * Checks if traders are equal
     * @param other the trader to compare to
     * @return true if equal
     */
    public boolean equals(Object other) {
        if (other instanceof Trader) {
            Trader t = (Trader) other;
            return getName().equals(t.getName());
        }
        else {
            throw new ClassCastException();
        }
    }

    /**
     * Opens trader window
     */
    public void openWindow() {
        myWindow = new TraderWindow(this);
        if (mailbox.size() != 0) {
            for (String a : mailbox) {
                myWindow.showMessage(a);
                mailbox.remove(a);
            }
        }
    }

    /**
     * check if mailbox empty
     * @return true if not empty
     */
    public boolean hasMessages() {
        return mailbox.size() != 0;
    }

    /**
     * Recieves message
     * @param msg message in mailbox
     */
    public void receiveMessage(String msg) {
        mailbox.add(msg);
        if (myWindow != null) {
            for (String a : mailbox) {
                myWindow.showMessage(a);
                mailbox.remove(a);
            }
        }
    }

    /**
     * Gets quote
     * @param symbol of company
     */
    public void getQuote(String symbol)
    {
        brokerage.getQuote(symbol, this);
    }

    /**
     * Places order
     * @param order trade order
     */
    public void placeOrder(TradeOrder order) {
        brokerage.placeOrder(order);
    }

    /**
     * Quits trader
     */
    public void quit() {
        brokerage.logout(this);
        myWindow = null;
    }


    //
    // The following are for test purposes only
    //
    /**
     * testing 
     * @return mailbox
     */
    protected Queue<String> mailbox() {
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
    public String toString() {
        String str = this.getClass().getName() + "[";
        String separator = "";

        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                if (field.getType().getName().equals("Brokerage")) {
                    str += separator + field.getType().getName() + " "
                        + field.getName();
                }
                else {
                    str += separator + field.getType().getName() + " "
                        + field.getName() + ":" + field.get(this);
                }
            }
            catch (IllegalAccessException ex) {
                System.out.println(ex);
            }

            separator = ", ";
        }

        return str + "]";
    }
}
