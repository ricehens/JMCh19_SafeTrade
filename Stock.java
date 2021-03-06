import java.util.*;
import java.lang.reflect.*;
// import java.text.DecimalFormat;

/**
 * Represents a stock in the SafeTrade project
 *
 * @author Andrew Yuan
 * @author Eric Shen
 * @author Sophia Yang
 * @version 2022-03-09
 */
public class Stock {
    // public static DecimalFormat money = new DecimalFormat("0.00");

    private String stockSymbol;
    private String companyName;
    private double loPrice;
    private double hiPrice;
    private double lastPrice;
    private int volume;
    private PriorityQueue<TradeOrder> buyOrders;
    private PriorityQueue<TradeOrder> sellOrders;

    /**
     * Constructs a new stock with a given symbol, company name, and starting
     * price.
     *
     * @param symbol the given symbol
     * @param name the company name
     * @param price the starting price
     */
    public Stock(String symbol, String name, double price) {
        stockSymbol = symbol;
        companyName = name;
        loPrice = price;
        hiPrice = price;
        lastPrice = price;
        volume = 0;

        sellOrders = new PriorityQueue<>(new PriceComparator(true));
        buyOrders = new PriorityQueue<>(new PriceComparator(false));
    }

    /**
     * Executes as many pending orders as possible.
     */
    protected void executeOrders() {
        while (true) {
            TradeOrder buy = buyOrders.peek();
            TradeOrder sell = sellOrders.peek();
            if (buy == null || sell == null) {
                break;
            }

            double price;

            if (buy.isLimit()) {
                if (sell.isLimit()) {
                    if (buy.getPrice() < sell.getPrice()) {
                        break;
                    }
                    price = sell.getPrice();
                }
                else { // sell is market
                    price = buy.getPrice();
                }
            }
            else { // buy is market
                if (sell.isLimit()) {
                    price = sell.getPrice();
                }
                else { // sell is market
                    price = lastPrice;
                }
            }

            int shares = Math.min(buy.getShares(), sell.getShares());
            buy.subtractShares(shares);
            sell.subtractShares(shares);
            if (buy.getShares() == 0) {
                buyOrders.poll();
            }
            if (sell.getShares() == 0) {
                sellOrders.poll();
            }

            loPrice = Math.min(loPrice, price);
            hiPrice = Math.max(hiPrice, price);
            volume += shares;

            String msg = String.format("%d %s at %.2f amt %.2f",
                    shares, stockSymbol, price, shares * price);
            buy.getTrader().receiveMessage("You bought: " + msg);
            sell.getTrader().receiveMessage("You sold: " + msg);
        }
    }

    /**
     * Returns a quote string for this stock.
     *
     * @return a quote string for this stock
     */
    public String getQuote() {
        String init = String.format("%s (%s)%n"
                + "Price: %.2f  hi: %.2f  lo: %.2f  vol: %d%n",
                companyName, stockSymbol, lastPrice, hiPrice, loPrice, volume);
        String ask = buyOrders.isEmpty() ? "Ask: none"
            : String.format("Ask: %.2f size: %d",
                    buyOrders.peek().getPrice(), buyOrders.peek().getShares());
        String bid = sellOrders.isEmpty() ? "Bid: none"
            : String.format("Bid: %.2f size: %d",
                    sellOrders.peek().getPrice(),
                    sellOrders.peek().getShares());
        return init + ask + "  " + bid;
    }

    /*
    public static void main(String[] args) {
        Stock stock = new Stock("GGGL", "Giggle.com", 1.00);
        System.out.println(stock.getQuote());
    }
    */

    /**
     * Places a trading order for this stock.
     *
     * @param order the order
     */
    public void placeOrder(TradeOrder order) {
        if (order.isBuy()) { 
            buyOrders.offer(order);
        }
        else {
            sellOrders.offer(order);
        }

        order.getTrader().receiveMessage(
                String.format("New order:  %s %s (%s)%n%d shares at %s",
                    order.isBuy() ? "Buy" : "Sell", stockSymbol, companyName,
                    order.getShares(),
                    order.isMarket() ? "market"
                    : String.format("$%.2f", order.getPrice())));

        executeOrders();
    }

    //
    // The following are for test purposes only
    //

    /**
     * Returns the stock symbol.
     * @return stockSymbol
     */
    protected String getStockSymbol() {
        return stockSymbol;
    }

    /**
     * Returns the company name.
     * @return companyName
     */
    protected String getCompanyName() {
        return companyName;
    }

    /**
     * Returns the low price.
     * @return loPrice
     */
    protected double getLoPrice() {
        return loPrice;
    }

    /**
     * Returns the high price.
     * @return hiPrice
     */
    protected double getHiPrice() {
        return hiPrice;
    }

    /**
     * Returns the last price.
     * @return lastPrice
     */
    protected double getLastPrice() {
        return lastPrice;
    }

    /**
     * Returns the volume.
     * @return volume
     */
    protected int getVolume() {
        return volume;
    }

    /**
     * Returns the buy orders.
     * @return buyOrders
     */
    protected PriorityQueue<TradeOrder> getBuyOrders() {
        return buyOrders;
    }

    /**
     * Returns the sell orders.
     * @return sellOrders
     */
    protected PriorityQueue<TradeOrder> getSellOrders() {
        return sellOrders;
    }

    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this Stock.
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
