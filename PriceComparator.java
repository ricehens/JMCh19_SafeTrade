/**
 * A price comparator for trade orders.
 *
 * @author Andrew Yuan
 * @author Eric Shen
 * @author Sophia Yang
 * @version 2022-03-09

 */
public class PriceComparator implements java.util.Comparator<TradeOrder> {

    private boolean ascending;
    
    /**
     * Constructs a price comparator that compares two orders 
     * in ascending order.
     */
    public PriceComparator() {
        ascending = true;
    }

    /**
     * Constructs a price comparator that compares two orders 
     * in ascending or descending order.
     * @param asc if true, make an ascending comparator; 
     * otherwise make a descending comparator.
     */
    public PriceComparator(boolean asc) {
        ascending = asc;
    }

    /**
     * Compares two trade orders.
     * @param order1 - the first order
     * @param order2 - the second order
     * @return 0 if both orders are market orders
     * <li>-1 if order1 is market and order2 is limit;</li>
     * <li>1 if order1 is limit and order2 is market;</li>
     * <li>the difference in prices, rounded to the nearest cent, 
     * if both order1 and order2 are limit orders.</li>
     */
    public int compare(TradeOrder order1, TradeOrder order2) {
        if (order1.isMarket() && order2.isMarket()) {
            return 0;
        }

        else if (order1.isMarket() && order2.isLimit()) {
            return -1;
        }

        else if (order1.isLimit() && order2.isMarket()) {
            return 1;
        }

        else {
            if (ascending) {
                return (int) ((order1.getPrice() - order2.getPrice()) * 100);
            }
            else {
                return (int) ((order2.getPrice() - order1.getPrice()) * 100);
            }
        }
    }
}
