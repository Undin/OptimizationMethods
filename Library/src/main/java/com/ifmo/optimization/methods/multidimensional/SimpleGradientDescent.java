package com.ifmo.optimization.methods.multidimensional;

import java.util.function.Function;

/**
 * Created by warrior on 02.03.15.
 */
public class SimpleGradientDescent extends GradientDescent {

    private double alpha = 0.5;
    private double alphaFactor = 0.7;

    public SimpleGradientDescent(Function<Point, Double> function, Function<Point, Point> gradient, Point leftBottom, Point rightTop, Point startPoint, double eps) {
        super(function, gradient, leftBottom, rightTop, startPoint, eps);
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setAlphaFactor(double alphaFactor) {
        this.alphaFactor = alphaFactor;
    }

    @Override
    protected double getAlpha(Point p) {
        Point gradient = computeGradient(p);
        double currentValue = computeFunction(p);
        Point nextPoint = p.plus(gradient.multiply(-alpha));
        while (!inArea(nextPoint)) {
            alpha *= alphaFactor;
            nextPoint = p.plus(gradient.multiply(-alpha));
        }
        double nextValue = computeFunction(nextPoint);
        while (nextValue > currentValue) {
            alpha *= alphaFactor;
            nextPoint = p.plus(gradient.multiply(-alpha));
            nextValue = computeFunction(nextPoint);
        }
        return alpha;
    }
}
