package com.ifmo.optimization.methods.onedimensional;

import java.util.function.Function;

/**
* Created by warrior on 27.02.15.
*/
public class Dichotomy extends SequentialMethod {

    private final double delta;

    public Dichotomy(Function<Double, Double> function, double left, double right, double eps) {
        super(function, left, right, eps);
        delta = eps / 3;
    }

    @Override
    public double findMinimum() {
        double l = left;
        double r = right;
        while (r - l > eps) {
            double m = (r + l) / 2;
            double fl = calculateFunction(m - delta);
            double fr = calculateFunction(m + delta);
            if (fl < fr) {
                r = m + delta;
            } else {
                l = m - delta;
            }
            step(l, r);
        }
        return (l + r) / 2;
    }
}
