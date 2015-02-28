package com.ifmo.optimization.methods;

import java.util.function.Function;

/**
 * Created by warrior on 27.02.15.
 */
public class Main {

    private static final Function<Double, Double> FUNCTION = arg -> arg * arg - 3 * arg + 2;
    private static final double LEFT = 0;
    private static final double RIGHT = 2;
    private static final double EPS = 0.000001;

    public static void main(String[] args) {
        {
            SequentialMethod method = new FibonacciMethod(FUNCTION, LEFT, RIGHT, EPS);
            double res = method.findMinimum();
            int steps = method.getSteps();
            System.out.println(res);
            System.out.println(Math.abs(1.5 - res) / EPS);
            System.out.println(steps);
            System.out.println(method.getFunctionComputations());
        }
        {
            SequentialMethod method = new GoldenSectionMethod(FUNCTION, LEFT, RIGHT, EPS);
            double res = method.findMinimum();
            int steps = method.getSteps();
            System.out.println(res);
            System.out.println(Math.abs(1.5 - res) / EPS);
            System.out.println(steps);
            System.out.println(method.getFunctionComputations());
        }

        {
            SequentialMethod method = new Dichotomy(FUNCTION, LEFT, RIGHT, EPS);
            double res = method.findMinimum();
            int steps = method.getSteps();
            System.out.println(res);
            System.out.println(Math.abs(1.5 - res) / EPS);
            System.out.println(steps);
            System.out.println(method.getFunctionComputations());
        }
    }
}
