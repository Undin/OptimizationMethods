package com.ifmo.optimization.methods.multidimensional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by warrior on 02.03.15.
 */
public abstract class GradientDescent {

    private final Function<Point, Double> function;
    private final Function<Point, Point> gradient;

    protected final Point leftBottom;
    protected final Point rightTop;
    protected final Point startPoint;

    protected final double eps;

    private int functionComputations;
    private int gradientComputations;

    private final List<Point> points = new ArrayList<>();

    private Point functionLastPoint;
    private double functionLastValue;
    private Point gradientLastPoint;
    private Point gradientLastValue;

    public GradientDescent(Function<Point, Double> function, Function<Point, Point> gradient, Point leftBottom, Point rightTop, Point startPoint, double eps) {
        this.function = function;
        this.gradient = gradient;
        this.leftBottom = leftBottom;
        this.rightTop = rightTop;
        this.startPoint = startPoint;
        this.eps = eps;

        points.add(startPoint);
    }

    protected double computeFunction(double x, double y) {
        return computeFunction(new Point(x, y));
    }

    protected double computeFunction(Point p) {
        if (p.equals(functionLastPoint)) {
            return functionLastValue;
        }
        functionComputations++;
        functionLastPoint = p;
        functionLastValue = function.apply(p);
        return functionLastValue;
    }

    protected Point computeGradient(double x, double y) {
        return computeGradient(new Point(x, y));
    }

    protected Point computeGradient(Point p) {
        if (p.equals(gradientLastPoint)) {
            return gradientLastValue;
        }
        gradientComputations++;
        gradientLastPoint = p;
        gradientLastValue = gradient.apply(p);
        return gradientLastValue;
    }

    public int getFunctionComputations() {
        return functionComputations;
    }

    public int getGradientComputations() {
        return gradientComputations;
    }

    public List<Point> getPoints() {
        return points;
    }

    public Point findMinimum() {
        Point nextPoint = startPoint;
        Point currentPoint;
        double nextValue = computeFunction(nextPoint);
        double currentValue;
        do {
            currentPoint = nextPoint;
            currentValue = nextValue;
            nextPoint = nextPoint(currentPoint);
            nextValue = computeFunction(nextPoint);
            points.add(nextPoint);
        } while (Math.abs(currentValue - nextValue) > eps);

        return nextPoint;
    }

    protected Point nextPoint(Point p) {
        double alpha = getAlpha(p);
        return p.plus(computeGradient(p).multiply(-alpha));
    }

    protected boolean inArea(Point p) {
        return leftBottom.x <= p.x && p.x <= rightTop.x && leftBottom.y <= p.y && p.y <= rightTop.y;
    }

    protected abstract double getAlpha(Point p);
}
