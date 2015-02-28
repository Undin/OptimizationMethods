package com.ifmo.optimization.methods;

import java.util.function.Function;

/**
 * Created by warrior on 27.02.15.
 */
public class GoldenSectionMethod extends SequentialMethod {

    public static final double PHI = (Math.sqrt(5) - 1) / 2;

    public GoldenSectionMethod(Function<Double, Double> function, double left, double right, double eps) {
        super(function, left, right, eps);
    }

    @Override
    public double findMinimum() {
        double l = left;
        double r = right;

        double x1 = 0;
        double x2 = 0;
        double fx1 = 0;
        double fx2 = 0;
        boolean leftCalculated = false;
        boolean rightCalculated = false;

        while (r - l > eps) {
            double delta = PHI * (r - l);
            if (!leftCalculated) {
                x1 = r - delta;
                fx1 = calculateFunction(x1);
                leftCalculated = true;
            }
            if (!rightCalculated) {
                x2 = l + delta;
                fx2 = calculateFunction(x2);
                rightCalculated = true;
            }
            if (fx1 < fx2) {
                r = x2;
                x2 = x1;
                fx2 = fx1;
                leftCalculated = false;
            } else {
                l = x1;
                x1 = x2;
                fx1 = fx2;
                rightCalculated = false;
            }
            step(l, r);
        }
        return (l + r) / 2;
    }
}
