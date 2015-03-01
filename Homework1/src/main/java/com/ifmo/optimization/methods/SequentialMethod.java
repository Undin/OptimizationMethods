package com.ifmo.optimization.methods;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by warrior on 27.02.15.
 */
public abstract class SequentialMethod extends Method {

    private final List<Double> leftPoints = new ArrayList<>();
    private final List<Double> rightPoints = new ArrayList<>();

    public SequentialMethod(Function<Double, Double> function, double left, double right, double eps) {
        super(function, left, right, eps);
        leftPoints.add(left);
        rightPoints.add(right);
    }

    protected void step(double l, double r) {
        iteration();
        leftPoints.add(l);
        rightPoints.add(r);
    }

    public double getLeft(int i) {
        return leftPoints.get(i);
    }

    public double getRight(int i) {
        return rightPoints.get(i);
    }

}
