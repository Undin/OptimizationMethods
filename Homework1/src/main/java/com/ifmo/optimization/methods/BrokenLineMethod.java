package com.ifmo.optimization.methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Created by warrior on 01.03.15.
 */
public class BrokenLineMethod extends Method {

    private final double l;

    private List<Double> x;
    private List<Double> y;

    public BrokenLineMethod(Function<Double, Double> function, double l, double left, double right, double eps) {
        super(function, left, right, eps);
        this.l = l;
    }

    @Override
    public double findMinimum() {

        double currentX = (left + right) / 2;
        double currentF = calculateFunction(currentX);
        double b0 = currentF - l * currentX;
        double b1 = currentF + l * currentX;

        x = new ArrayList<>(Arrays.asList(left, currentX, right));
        y = new ArrayList<>(Arrays.asList(l * left + b0, currentF, -l * right + b1));
        final List<Double> b = new ArrayList<>(Arrays.asList(b0, b1));

        int currentIndex = minElementIndex(y);
        currentX = x.get(currentIndex);
        currentF = calculateFunction(currentX);

        while (currentF - y.get(currentIndex) > eps) {

            if (currentIndex < x.size() - 1) {
                double nextB = b.get(currentIndex);
                double newNextB = l * currentX + currentF;
                double newNextX = (newNextB - nextB) / (2 * l);
                double newNextY = -l * newNextX + newNextB;
                x.add(currentIndex + 1, newNextX);
                y.add(currentIndex + 1, newNextY);
                b.add(currentIndex + 1, newNextB);
            }

            if (currentIndex > 0) {
                double prevB = b.get(currentIndex - 1);
                double newPrevB = -l * currentX + currentF;
                double newPrevX = (prevB - newPrevB) / (2 * l);
                double newPrevY = -l * newPrevX + prevB;
                x.add(currentIndex, newPrevX);
                y.add(currentIndex, newPrevY);
                b.add(currentIndex, newPrevB);
                currentIndex++;
            }

            y.set(currentIndex, currentF);
            currentIndex = minElementIndex(y);
            currentX = x.get(currentIndex);
            currentF = calculateFunction(currentX);
            iteration();
        }

        return currentX;
    }

    public List<Double> getXList() {
        return x;
    }

    public List<Double> getYList() {
        return y;
    }

    private int minElementIndex(List<Double> list) {
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) < list.get(index)) {
                index = i;
            }
        }
        return index;
    }
}
