package tw.com.oscar.v8.lambda.fromyoutube;

/**
 * Created by oscarwei on 2015/1/10.
 */
public class StockInfo {

    public final String ticker;
    public final double price;

    public StockInfo(final String symbol, final double thePrice) {
        ticker = symbol;
        price = thePrice;
    }

    public String toString() {
        return String.format("ticker: %s price: %g", ticker, price);
    }
}
