import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.regex.*;

import org.junit.*;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

/**
 * SafeTrade tests:
 *   TradeOrder
 *   PriceComparator
 *   Trader
 *   Brokerage
 *   StockExchange
 *   Stock
 *
 * @author Andrew Yuan
 * @author Eric Shen
 * @author Sophia Yang
 * @version 2022-03-09
 * @author Assignment: JM Chapter 19 - SafeTrade
 * 
 * @author Sources: N/A
 *
 */
public class JUSafeTradeTest
{
    // --Test TradeOrder
    /**
     * TradeOrder tests:
     *   TradeOrderConstructor - constructs TradeOrder and then compare toString
     *   TradeOrderGetTrader - compares value returned to constructed value
     *   TradeOrderGetSymbol - compares value returned to constructed value
     *   TradeOrderIsBuy - compares value returned to constructed value
     *   TradeOrderIsSell - compares value returned to constructed value
     *   TradeOrderIsMarket - compares value returned to constructed value
     *   TradeOrderIsLimit - compares value returned to constructed value
     *   TradeOrderGetShares - compares value returned to constructed value
     *   TradeOrderGetPrice - compares value returned to constructed value
     *   TradeOrderSubtractShares - subtracts known value & compares result
     *     returned by getShares to expected value
     */
    private String symbol = "GGGL";
    private boolean buyOrder = true;
    private boolean marketOrder = true;
    private int numShares = 123;
    private int numToSubtract = 24;
    private double price = 123.45;

    @Test
    public void tradeOrderConstructor()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        String toStr = to.toString();

        assertTrue( "<< Invalid TradeOrder Constructor >>",
                    toStr.contains( "TradeOrder[Trader trader:null" )
                        && toStr.contains( "java.lang.String symbol:" + symbol )
                        && toStr.contains( "boolean buyOrder:" + buyOrder )
                        && toStr.contains( "boolean marketOrder:" + marketOrder )
                        && toStr.contains( "int numShares:" + numShares )
                        && toStr.contains( "double price:" + price ) );
    }
    
    @Test
    public void TradeOrderToString()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertNotNull( to.toString() );
    }

    @Test
    public void tradeOrderGetTrader()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertNull( "<< TradeOrder: " + to.getTrader() + " should be null >>",
                    to.getTrader() );
    }

    @Test
    public void tradeOrderGetSymbol()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertEquals( "<< TradeOrder: " + to.getTrader() + " should be "
            + symbol + " >>", symbol, to.getSymbol() );
    }

    @Test
    public void tradeOrderIsBuy()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );

        assertTrue( "<< TradeOrder: " + to.isBuy() + " should be " + buyOrder
            + " >>", to.isBuy() );
    }

    @Test
    public void tradeOrderIsSell()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertFalse( "<< TradeOrder: " + to.isSell() + " should be "
            + !buyOrder + " >>", to.isSell() );
    }

    @Test
    public void tradeOrderIsMarket()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertTrue( "<< TradeOrder: " + to.isMarket() + " should be "
            + marketOrder + " >>", to.isMarket() );
    }

    @Test
    public void tradeOrderIsLimit()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );

        assertFalse( "<< TradeOrder: " + to.isLimit() + " should be "
            + !marketOrder + ">>", to.isLimit() );
    }

    @Test
    public void tradeOrderGetShares()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertTrue( "<< TradeOrder: " + to.getShares() + " should be "
            + numShares + ">>", numShares == to.getShares()
            || ( numShares - numToSubtract ) == to.getShares() );
    }

    @Test
    public void tradeOrderGetPrice()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertEquals( "<< TradeOrder: " + to.getPrice() + " should be " + price
            + ">>", price, to.getPrice(), 0.0 );
    }

    @Test
    public void tradeOrderSubtractShares()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        to.subtractShares( numToSubtract );
        assertEquals( "<< TradeOrder: subtractShares(" + numToSubtract
            + ") should be " + ( numShares - numToSubtract ) + ">>", numShares
            - numToSubtract, to.getShares() );
    }
    
    // --Test TraderWindow Stub
    @Test
    public void traderWindowConstructor()
    {
        TraderWindow tw = new TraderWindow( null );
        assertNotNull( tw );
    }

    @Test
    public void traderWindowShowMessage()
    {
        TraderWindow tw = new TraderWindow( null );
        assertNotNull( tw );
        tw.showMessage( null );
    }

    //  --Test PriceComparator
    
    @Test
    public void priceComparatorNoArgsConstructor()
    {
        PriceComparator pc = new PriceComparator();
        assertNotNull(pc);
    }

    @Test
    public void priceComparatorConstructor()
    {
        PriceComparator pc = new PriceComparator(false);
        assertNotNull(pc);
    }

    @Test
    public void priceComparatorCompareBothMarket()
    {
        PriceComparator pc = new PriceComparator();
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        Trader trader1 = new Trader(bk, "name1", "pswd1");
        Trader trader2 = new Trader(bk, "name2", "pswd2");
        TradeOrder order1 = new TradeOrder(trader1, symbol, buyOrder, marketOrder, numShares, price);
        TradeOrder order2 = new TradeOrder(trader2, symbol, buyOrder, marketOrder, numShares, price);
        assertEquals( "<< PriceComparator: compare(" + order1 + ", " + order2 
            + ") should be " + 0 + ">>", 0, pc.compare(order1, order2) );
    }
    
    @Test
    public void priceComparatorCompareFirstMarket()
    {
        PriceComparator pc = new PriceComparator();
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        Trader trader1 = new Trader(bk, "name1", "pswd1");
        Trader trader2 = new Trader(bk, "name2", "pswd2");
        TradeOrder order1 = new TradeOrder(trader1, symbol, buyOrder, marketOrder, numShares, price);
        TradeOrder order2 = new TradeOrder(trader2, symbol, buyOrder, !marketOrder, numShares, price);
        assertEquals( "<< PriceComparator: compare(" + order1 + ", " + order2 
            + ") should be " + -1 + ">>", -1, pc.compare(order1, order2) );
    }

    @Test
    public void priceComparatorCompareSecondMarket()
    {
        PriceComparator pc = new PriceComparator();
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        Trader trader1 = new Trader(bk, "name1", "pswd1");
        Trader trader2 = new Trader(bk, "name2", "pswd2");
        TradeOrder order1 = new TradeOrder(trader1, symbol, buyOrder, !marketOrder, numShares, price);
        TradeOrder order2 = new TradeOrder(trader2, symbol, buyOrder, marketOrder, numShares, price);
        assertEquals( "<< PriceComparator: compare(" + order1 + ", " + order2 
            + ") should be " + 1 + ">>", 1, pc.compare(order1, order2) );
    }

    @Test
    public void priceComparatorCompareBothLimitAscending()
    {
        PriceComparator pc = new PriceComparator();
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        Trader trader1 = new Trader(bk, "name1", "pswd1");
        Trader trader2 = new Trader(bk, "name2", "pswd2");
        TradeOrder order1 = new TradeOrder(trader1, symbol, buyOrder, !marketOrder, numShares, price);
        double diff = 1.23;
        TradeOrder order2 = new TradeOrder(trader2, symbol, buyOrder, !marketOrder, numShares, price + diff);
        assertEquals( "<< PriceComparator: compare(" + order1 + ", " + order2 
            + ") should be " + (-diff * 100) + ">>", (int)(-diff * 100), pc.compare(order1, order2) );
    }

    @Test
    public void priceComparatorCompareBothLimitDescending()
    {
        PriceComparator pc = new PriceComparator(false);
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        Trader trader1 = new Trader(bk, "name1", "pswd1");
        Trader trader2 = new Trader(bk, "name2", "pswd2");
        TradeOrder order1 = new TradeOrder(trader1, symbol, buyOrder, !marketOrder, numShares, price);
        double diff = 1.23;
        TradeOrder order2 = new TradeOrder(trader2, symbol, buyOrder, !marketOrder, numShares, price + diff);
        assertEquals( "<< PriceComparator: compare(" + order1 + ", " + order2 
            + ") should be " + (diff * 100) + ">>", (int)(diff * 100), pc.compare(order1, order2) );
    }
    
    // --Test Trader

    @Test
    public void traderConstructor()
    {
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        String name = "John";
        String pswd = "pass";
        Trader t = new Trader(bk, name, pswd);
        assertNotNull(t);
        // String toStr = to.toString();

        // assertTrue( "<< Invalid Trader Constructor >>",
        //             toStr.contains( "Trader[Brokerage brokerage:null" )
        //                 && toStr.contains( "java.lang.String screenName:" + screenName )
        //                 && toStr.contains( "java.lang.String password:" + password ));
    }
    @Test
    public void traderGetName()
    {
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        String name = "John";
        String pswd = "pass";
        Trader t = new Trader(bk, name, pswd);

        assertEquals( "<< Trader: " + t.getName() + " should be "
             + name + " >>", name, t.getName());
    }

    @Test
    public void traderGetPassword()
    {
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        String name = "John";
        String pswd = "pass";
        Trader t = new Trader(bk , name, pswd);

        assertEquals( "<< Trader: " + t.getPassword() + " should be "
             + pswd + " >>", pswd, t.getPassword());
    }

    @Test
    public void traderCompareTo()
    {
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        String name = "John";
        String pswd = "pass";
        Trader t = new Trader(bk, name, pswd);
        Trader t2 = new Trader(bk, name, pswd);

        assertEquals( "<< Trader: " + t2.getName() + " should be "
             + name + " >>", 0, t2.compareTo(t));
    }

    @Test
    public void traderEquals()
    {
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        String name = "John";
        String pswd = "pass";
        Trader t = new Trader(bk, name, pswd);
        Trader t2 = new Trader(bk, name, pswd);

        assertTrue( "<< Trader: " + t.getName()+ " should be "
             + name + " >>", t.equals(t2));
    }

    @Test
    public void traderHasMessages()
    {
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        String name = "John";
        String pswd = "pass";
        Trader t = new Trader(bk, name, pswd);

        assertFalse( "<< Trader: " + t.getName() + " should have no messages "
             +" >>", t.hasMessages());
    }
    
    // --Test Brokerage
    
    public void brokerageConstructor()
    {
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        assertNotNull(bk);
    }

    @Test
    public void brokerageAddUserErr1()
    {
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        String name = "Joe";
        String password = "p";
        assertEquals( "<< Brokerage: addUser(" + name + ", " + password 
            + ") should be " + -1 + ">>", -1, bk.addUser(name, password) );
    }

    @Test
    public void brokerageAddUserErr2()
    {
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        String name = "John";
        String password = "p";
        assertEquals( "<< Brokerage: addUser(" + name + ", " + password 
            + ") should be " + -2 + ">>", -2, bk.addUser(name, password) );
    }

    @Test
    public void brokerageAddUserErr3()
    {
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        String name = "John";
        String password = "pass";
        bk.addUser(name, password);
        assertEquals( "<< Brokerage: addUser(" + name + ", " + password 
            + ") should be " + -3 + ">>", -3, bk.addUser(name, password) );
    }

    @Test
    public void brokerageAddUser()
    {
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        String name = "John";
        String password = "pass";
        assertEquals( "<< Brokerage: addUser(" + name + ", " + password 
            + ") should be " + 0 + ">>", 0, bk.addUser(name, password) );
    }

    @Test
    public void brokerageGetQuote()
    {
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        Trader trader = new Trader(bk, "name", "pswd");
        bk.getQuote(symbol, trader);
        assertTrue( "<< Brokerage: " + trader.hasMessages() + " should be " + true
            + " >>", trader.hasMessages() );
    }

    @Test
    public void brokerageLoginErr1()
    {
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        String name = "John";
        String password = "pass";
        assertEquals( "<< Brokerage: login(" + name + ", " + password 
            + ") should be " + -1 + ">>", -1, bk.login(name, "asdf") );
    }

    @Test
    public void brokerageLoginErr2()
    {
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        String name = "John";
        String password = "pass";
        bk.addUser(name, password);
        assertEquals( "<< Brokerage: login(" + name + ", " + "passw" 
            + ") should be " + -2 + ">>", -2, bk.login(name, "passw") );
    }

    @Test
    public void brokerageLoginErr3()
    {
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        String name = "John";
        String password = "pass";
        bk.addUser(name, password);
        bk.login(name, password);
        assertEquals( "<< Brokerage: login(" + name + ", " + password 
            + ") should be " + -3 + ">>", -3, bk.login(name, password) );
    }

    @Test
    public void brokerageLogin()
    {
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        String name = "John";
        String password = "pass";
        bk.addUser(name, password);
        assertEquals( "<< Brokerage: login(" + name + ", " + password 
            + ") should be " + 0 + ">>", 0, bk.login(name, password) );
    }
    
    @Test
    public void brokerageLogout()
    {
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        bk.addUser("John", "pass");
        bk.login("John", "pass");
        Trader trader = new Trader(bk, "John", "pass");
        bk.logout(trader);
        assertTrue( "<< Brokerage: " + bk.getLoggedTraders().contains(trader) + 
            " should be " + false + " >>", !bk.getLoggedTraders().contains(trader) );
    }

    @Test
    public void brokeragePlaceOrder()
    {
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        Trader trader = new Trader(bk, "John", "pass");
        TradeOrder to = new TradeOrder(trader, symbol, buyOrder, marketOrder, numShares, price);
        bk.placeOrder(to);
        assertTrue( "<< Brokerage: " + exchange.getQuote(symbol).equals(symbol + " not found") + 
            " should be " + false + " >>", !exchange.getQuote(symbol).equals(symbol + " not found"));
    }
    
    // --Test StockExchange
    
    @Test
    public void stockExchangeConstructor() {
        StockExchange exchange = new StockExchange();
        assertNotNull(exchange);
    }
    
    // --Test Stock
    
    @Test
    public void stockConstructor() {
        Stock stock = new Stock(symbol, "Giggle.com", price);
        assertNotNull(stock);
    }

    @Test
    public void stockGetQuote() {
        Stock stock = new Stock(symbol, "Giggle.com", price);
        assertEquals(stock.getQuote(),
                String.format("Giggle.com (%s)%n"
                    + "Price: %.2f  hi: %.2f  lo: %.2f  vol: 0%n"
                    + "Ask: none  Bid: none",
                    symbol, price, price, price));
    }

    @Test
    public void stockPlaceOrder() {
        Stock stock = new Stock(symbol, "Giggle.com", price);
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        Trader trader = new Trader(bk, "John", "pass");
        TradeOrder to = new TradeOrder(trader, symbol, buyOrder,
                marketOrder, numShares, price);

        stock.placeOrder(to);
        assertTrue(stock.getBuyOrders().size() == 1);
    }

    @Test
    public void stockExecuteOrders() {
        Stock stock = new Stock(symbol, "Giggle.com", price);
        StockExchange exchange = new StockExchange();
        Brokerage bk = new Brokerage(exchange);
        Trader trader1 = new Trader(bk, "John", "pass");
        Trader trader2 = new Trader(bk, "Jane", "word");
        TradeOrder buy = new TradeOrder(trader, symbol, true,
                marketOrder, numShares, price);
        TraderOrder sell = new TradeOrder(trader, symbol, false,
                marketOrder, numShares, price);

        stock.placeOrder(buy);
        stock.placeOrder(sell);
        // will call execute order

        assertTrue(stock.getVolume() == 1);
    }
    
    // Remove block comment below to run JUnit test in console
/*
    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( JUSafeTradeTest.class );
    }
    
    public static void main( String args[] )
    {
        org.junit.runner.JUnitCore.main( "JUSafeTradeTest" );
    }
*/
}
