package com.ifmo.optimization.methods;

import java.util.function.Function;

/**
 * Created by Whiplash on 01.03.2015.
 */
public class SearchMethod extends SequentialMethod {

    private final int n;

    public SearchMethod(Function<Double, Double> function, double left, double right, double eps, int n) {
        super(function, left, right, eps);
        this.n = n + 1;
    }

    @Override
    public double findMinimum() {
        double l = left;
        double r = right;

        double[] f = new double[n + 1];
        f[0] = calculateFunction(l);
        f[n] = calculateFunction(r);
        while (r - l > eps) {
            double dist = (r - l) / n;
            for (int i = 1; i < f.length - 1; i++) {
                f[i] = calculateFunction(l + dist * i);
            }
            int index = minElementIndex(f);
            if (index == 0 || index == n) {
                return f[index];
            }
            r = l + (index + 1) * dist;
            l = l + (index - 1) * dist;
            f[0] = f[index - 1];
            f[n] = f[index + 1];
            step(l, r);
        }
        return (r + l) / 2;
    }

    private int minElementIndex(double[] arr) {
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] <= arr[index]) {
                index = i;
            }
        }
        return index;
    }
}
