package com.ifmo.optimization.methods.onedimensional;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

/**
 * Created by warrior on 01.03.15.
 */
public class SequentialMethodTest {
    
    private static final int MIN_POW = 1;
    private static final int MAX_POW = 6;

    private static final Function<Double, Double> FUNCTION1 = arg -> arg * arg - 3 * arg + 2;
    private static final double LEFT1 = 0;
    private static final double RIGHT1 = 2;
    private static final double ANSWER1 = 1.5;

    private static final Function<Double, Double> FUNCTION2 = arg -> {
        if (arg < 0) {
            return Math.pow(arg, 4) + 3;
        } else {
            return Math.pow(arg, 3) + 3;
        }
    };
    private static final double LEFT2 = -1;
    private static final double RIGHT2 = 1;
    private static final double ANSWER2 = 0;

    @Test
    public void dichotomyTest1() {
        for (int i = MIN_POW; i < MAX_POW; i++) {
            double eps = Math.pow(10, -i);
            Method method = new Dichotomy(FUNCTION1, LEFT1, RIGHT1, eps);
            double res = method.findMinimum();
            Assert.assertTrue(Math.abs(ANSWER1 - res) < eps);
        }
    }

    @Test
    public void dichotomyTest2() {
        for (int i = MIN_POW; i < MAX_POW; i++) {
            double eps = Math.pow(10, -i);
            Method method = new Dichotomy(FUNCTION2, LEFT2, RIGHT2, eps);
            double res = method.findMinimum();
            Assert.assertTrue(Math.abs(ANSWER2 - res) < eps);
        }
    }

    @Test
    public void goldenSectionTest1() {
        for (int i = MIN_POW; i < MAX_POW; i++) {
            double eps = Math.pow(10, -i);
            Method method = new GoldenSectionMethod(FUNCTION1, LEFT1, RIGHT1, eps);
            double res = method.findMinimum();
            Assert.assertTrue(Math.abs(ANSWER1 - res) < eps);
        }
    }

    @Test
    public void goldenSectionTest2() {
        for (int i = MIN_POW; i < MAX_POW; i++) {
            double eps = Math.pow(10, -i);
            Method method = new GoldenSectionMethod(FUNCTION2, LEFT2, RIGHT2, eps);
            double res = method.findMinimum();
            Assert.assertTrue(Math.abs(ANSWER2 - res) < eps);
        }
    }

    @Test
    public void fibonacciTest1() {
        for (int i = MIN_POW; i < MAX_POW; i++) {
            double eps = Math.pow(10, -i);
            Method method = new FibonacciMethod(FUNCTION1, LEFT1, RIGHT1, eps);
            double res = method.findMinimum();
            Assert.assertTrue(Math.abs(ANSWER1 - res) < eps);
        }
    }

    @Test
    public void fibonacciTest2() {
        for (int i = MIN_POW; i < MAX_POW; i++) {
            double eps = Math.pow(10, -i);
            Method method = new FibonacciMethod(FUNCTION2, LEFT2, RIGHT2, eps);
            double res = method.findMinimum();
            Assert.assertTrue(Math.abs(ANSWER2 - res) < eps);
        }
    }

    @Test
    public void uniformSearchTest1() {
        for (int i = MIN_POW; i < MAX_POW; i++) {
            double eps = Math.pow(10, -i);
            Method method = new UniformSearchMethod(FUNCTION1, LEFT1, RIGHT1, eps, 5);
            double res = method.findMinimum();
            Assert.assertTrue(Math.abs(ANSWER1 - res) < eps);
        }
    }

    @Test
    public void uniformSearchTest2() {
        for (int i = MIN_POW; i < MAX_POW; i++) {
            double eps = Math.pow(10, -i);
            Method method = new UniformSearchMethod(FUNCTION2, LEFT2, RIGHT2, eps, 5);
            double res = method.findMinimum();
            Assert.assertTrue(Math.abs(ANSWER2 - res) < eps);
        }
    }
}
