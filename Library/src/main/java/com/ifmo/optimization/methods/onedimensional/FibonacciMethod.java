package com.ifmo.optimization.methods.onedimensional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by warrior on 27.02.15.
 */
public class FibonacciMethod extends SequentialMethod {

    private final double length;
    private final List<Double> fibonacciRow = new ArrayList<>();

    public FibonacciMethod(Function<Double, Double> function, double left, double right, double eps) {
        super(function, left, right, eps);
        length = right - left;
        fibonacciRow.add(1D);
        fibonacciRow.add(1D);
        int size = 2;
        while (length / fibonacciRow.get(size - 1) > eps) {
            fibonacciRow.add(fibonacciRow.get(size - 1) + fibonacciRow.get(size - 2));
            size++;
        }
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

        int n = fibonacciRow.size() - 2;

        for (int k = 1; k < n; k++) {
            if (!leftCalculated) {
                x1 = l + length * fibonacciRow.get(n - k) / fibonacciRow.get(n + 1);
                fx1 = calculateFunction(x1);
                leftCalculated = true;
            }
            if (!rightCalculated) {
                x2 = l + length * fibonacciRow.get(n + 1 - k) / fibonacciRow.get(n + 1);
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
