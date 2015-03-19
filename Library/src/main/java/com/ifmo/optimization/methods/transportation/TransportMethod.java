package com.ifmo.optimization.methods.transportation;

/**
 * Created by Whiplash on 19.03.2015.
 */
public abstract class TransportMethod {

    protected final double[] provider;
    protected final double[] consumer;
    protected final double[][] cost;

    public TransportMethod(double[] provider, double[] consumer, double[][] cost) {
        if (provider.length != cost.length || consumer.length != cost[0].length) {
            throw new IllegalArgumentException("Incorrect cost matrix");
        }
        if (sum(provider) != sum(consumer)) {
            throw new IllegalStateException("Incorrect task: sum(consumer) != sum(provider)");
        }
        this.provider = provider;
        this.consumer = consumer;
        this.cost = cost;
    }

    protected double[] copy1DArray(double[] array) {
        double[] copiedArray = new double[array.length];
        System.arraycopy(array, 0, copiedArray, 0, copiedArray.length);
        return copiedArray;
    }

    protected double[][] copy2DArray(double[][] array) {
        double[][] copiedArray = new double[array.length][array[0].length];
        for (int y = 0; y < copiedArray.length; y++) {
            System.arraycopy(array[y], 0, copiedArray[y], 0, copiedArray[0].length);
        }
        return copiedArray;
    }

    private double sum(double[] vec) {
        double sum = 0;
        for (double v : vec) {
            sum += v;
        }
        return sum;
    }

    public abstract double[][] find();
}
