package com.ifmo.optimization.methods.onedimensional;

import java.util.function.Function;

/**
 * Created by warrior on 27.02.15.
 */
public class Main {

    private static final Function<Double, Double> FUNCTION = arg -> arg * arg - 3 * arg + 2;
    private static final double LEFT = 0;
    private static final double RIGHT = 2;
    private static final double EPS = 0.00001;

    public static void main(String[] args) {
        {
            Method method = new FibonacciMethod(FUNCTION, LEFT, RIGHT, EPS);
            double res = method.findMinimum();
            int steps = method.getIterations();
            System.out.println(res);
            System.out.println(Math.abs(1.5 - res) / EPS);
            System.out.println(steps);
            System.out.println(method.getFunctionComputations());
        }
        {
            Method method = new GoldenSectionMethod(FUNCTION, LEFT, RIGHT, EPS);
            double res = method.findMinimum();
            int steps = method.getIterations();
            System.out.println(res);
            System.out.println(Math.abs(1.5 - res) / EPS);
            System.out.println(steps);
            System.out.println(method.getFunctionComputations());
        }

        {
            Method method = new Dichotomy(FUNCTION, LEFT, RIGHT, EPS);
            double res = method.findMinimum();
            int steps = method.getIterations();
            System.out.println(res);
            System.out.println(Math.abs(1.5 - res) / EPS);
            System.out.println(steps);
            System.out.println(method.getFunctionComputations());
        }

        {
            Method method = new UniformSearchMethod(FUNCTION, LEFT, RIGHT, EPS, 5);
            double res = method.findMinimum();
            int steps = method.getIterations();
            System.out.println(res);
            System.out.println(Math.abs(1.5 - res) / EPS);
            System.out.println(steps);
            System.out.println(method.getFunctionComputations());
        }

        {
            Method method = new BrokenLineMethod(FUNCTION, 3, LEFT, RIGHT, EPS);
            double res = method.findMinimum();
            int steps = method.getIterations();
            System.out.println(res);
            System.out.println(Math.abs(1.5 - res) / EPS);
            System.out.println(steps);
            System.out.println(method.getFunctionComputations());
        }
    }
}
