import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Created by MichaelBick on 7/29/15.
 */
public class Features {
    private static final int SPREAD = 10;

    private static final int PAST_DAYS = 3;

    private Symbol sp500;
    private HashMap<Integer, BigDecimal> market = new HashMap<Integer, BigDecimal>();

    public Features() throws IOException{
        sp500 = new Symbol("^GSPC");
    }

    private double getSP500(int daysAgo) throws IOException{
        BigDecimal price = market.get(daysAgo);

        if (price == null) {
            price = sp500.getAdjClose(daysAgo);
            market.put(daysAgo, price);
        }

        return price.doubleValue();
    }

    public double[] getFeatures(Symbol stock, int daysAgo) throws IOException {
        double[] features = new double[5];

        features[0] = 1.0;

        features[1] = stock.getAdjClose(daysAgo).doubleValue();
        
        features[features.length - 3] = stock.getMA(daysAgo, 50).doubleValue() - stock.getMA(daysAgo, 200).doubleValue();
        
        features[features.length - 2] = stock.getMA(daysAgo, 50).doubleValue();
        features[features.length - 1] = stock.getMA(daysAgo, 200).doubleValue();

        return features;
    }
}
