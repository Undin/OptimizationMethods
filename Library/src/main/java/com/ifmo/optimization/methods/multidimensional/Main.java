package com.ifmo.optimization.methods.multidimensional;

import java.util.function.Function;

/**
 * Created by warrior on 02.03.15.
 */
public class Main {

    private static final Function<Point, Double> FUNCTION = p -> Math.pow(p.x, 4) + Math.pow(p.y, 4) - 5 * (p.x * p.y - Math.pow(p.x * p.y, 2));
    private static final Function<Point, Point> GRADIENT = p -> {
        double x = 4 * Math.pow(p.x, 3) - 5 * (p.y - 2 * p.x * Math.pow(p.y, 2));
        double y = 4 * Math.pow(p.y, 3) - 5 * (p.x - 2 * p.y * Math.pow(p.x, 2));
        return new Point(x, y);
    };
    private static final Point LEFT_BOTTOM = new Point(0, 0);
    private static final Point RIGHT_TOP = new Point(1, 1);
    private static final Point START_POINT = new Point(0.2, 0.8);
    private static final double EPS = 0.0000001;

    public static void main(String[] args) {
        {
            GradientDescent gradientDescent = new SimpleGradientDescent(FUNCTION, GRADIENT, LEFT_BOTTOM, RIGHT_TOP, START_POINT, EPS);
            Point point = gradientDescent.findMinimum();
            System.out.println(point);
            System.out.println(FUNCTION.apply(START_POINT));
            System.out.println(FUNCTION.apply(point));
            System.out.println(gradientDescent.getFunctionComputations());
            System.out.println(gradientDescent.getGradientComputations());
        }
        {
            GradientDescent gradientDescent = new FastGradientDescent(FUNCTION, GRADIENT, LEFT_BOTTOM, RIGHT_TOP, START_POINT, EPS);
            Point point = gradientDescent.findMinimum();
            System.out.println(point);
            System.out.println(FUNCTION.apply(START_POINT));
            System.out.println(FUNCTION.apply(point));
            System.out.println(gradientDescent.getFunctionComputations());
            System.out.println(gradientDescent.getGradientComputations());
        }
    }
}
