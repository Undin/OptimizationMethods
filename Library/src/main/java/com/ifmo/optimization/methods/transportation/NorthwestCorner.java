package com.ifmo.optimization.methods.transportation;

/**
 * Created by Whiplash on 19.03.2015.
 */
public class NorthwestCorner extends TransportMethod {

    public NorthwestCorner(double[] provider, double[] consumer, double[][] cost) {
        super(provider, consumer, cost);
    }

    @Override
    public double[][] find() {
        double[] tmpProvider = copy1DArray(provider);
        double[] tmpConsumer = copy1DArray(consumer);
        double[][] initialSolution = new double[provider.length][consumer.length];
        for (int x = 0; x < consumer.length; x++) {
            for (int y = 0; y < provider.length; y++) {
                double minGoods = Math.min(tmpProvider[y], tmpConsumer[x]);
                initialSolution[y][x] = minGoods;
                tmpConsumer[x] -= minGoods;
                tmpProvider[y] -= minGoods;
            }
        }
        return initialSolution;
    }
}
