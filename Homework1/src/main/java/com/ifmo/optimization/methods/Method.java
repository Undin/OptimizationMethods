package com.ifmo.optimization.methods;

import java.util.function.Function;

/**
 * Created by warrior on 27.02.15.
 */
public abstract class Method {

    protected final double left;
    protected final double right;
    protected final double eps;

    private final Function<Double, Double> function;

    protected int iterations = 0;
    private int functionComputations = 0;

    public Method(Function<Double, Double> function, double left, double right, double eps) {
        this.function = function;
        this.left = left;
        this.right = right;
        this.eps = eps;
    }

    public int getIterations() {
        return iterations;
    }

    public int getFunctionComputations() {
        return functionComputations;
    }

    protected double calculateFunction(double arg) {
        functionComputations++;
        return function.apply(arg);
    }

    public abstract double findMinimum();
}
