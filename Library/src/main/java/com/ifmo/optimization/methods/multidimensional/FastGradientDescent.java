package com.ifmo.optimization.methods.multidimensional;

import com.ifmo.optimization.methods.onedimensional.GoldenSectionMethod;

import java.util.function.Function;

import static java.lang.Math.*;

/**
 * Created by warrior on 02.03.15.
 */
public class FastGradientDescent extends GradientDescent {

    private double alphaPrecision = 0.001;

    public FastGradientDescent(Function<Point, Double> function, Function<Point, Point> gradient, Point leftBottom, Point rightTop, Point startPoint, double eps) {
        super(function, gradient, leftBottom, rightTop, startPoint, eps);
    }

    public void setAlphaPrecision(double alphaPrecision) {
        this.alphaPrecision = alphaPrecision;
    }

    @Override
    protected double getAlpha(Point p) {
        Point grad = computeGradient(p);
        double alphaX1 = max(0, (p.x - leftBottom.x) / grad.x);
        double alphaX2 = max(0, (p.x - rightTop.x) / grad.x);
        double alphaY1 = max(0, (p.y - leftBottom.y) / grad.y);
        double alphaY2 = max(0, (p.y - rightTop.y) / grad.y);
        double left = max(min(alphaX1, alphaX2), min(alphaY1, alphaY2));
        double right = min(max(alphaX1, alphaX2), max(alphaY1, alphaY2));
        Function<Double, Double> fun = a -> computeFunction(p.plus(grad.multiply(-a)));
        return new GoldenSectionMethod(fun, left, right, alphaPrecision).findMinimum();
    }
}
