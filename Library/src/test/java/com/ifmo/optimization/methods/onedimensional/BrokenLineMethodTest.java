package com.ifmo.optimization.methods.onedimensional;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.function.Function;

/**
 * Created by warrior on 01.03.15.
 */
public class BrokenLineMethodTest {

    private static final int MIN_POW = 1;
    private static final int MAX_POW = 6;

    private static final Function<Double, Double> FUNCTION1 = arg -> arg * arg - 3 * arg + 2;
    private static final double LEFT1 = 0;
    private static final double RIGHT1 = 2;
    private static final double ANSWER1 = 1.5;
    private static final double L1 = 3;

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
    private static final double L2 = 4;

    private static final Function<Double, Double> FUNCTION3 = Math::cos;
    private static final double LEFT3 = 0;
    private static final double RIGHT3 = 2 * Math.PI;
    private static final double ANSWER3 = Math.PI;
    private static final double L3 = 1;

    @Test
    public void brokenLineMethodTest1() {
        for (int i = MIN_POW; i < MAX_POW; i++) {
            double eps = Math.pow(10, -i);
            Method method = new BrokenLineMethod(FUNCTION1, L1, LEFT1, RIGHT1, eps);
            double res = method.findMinimum();
            Assert.assertTrue(Math.abs(FUNCTION1.apply(ANSWER1) - FUNCTION1.apply(res)) < eps);
        }
    }

    @Ignore
    @Test
    public void brokenLineMethodTest2() {
        for (int i = MIN_POW; i < MAX_POW; i++) {
            double eps = Math.pow(10, -i);
            Method method = new BrokenLineMethod(FUNCTION2, L2, LEFT2, RIGHT2, eps);
            double res = method.findMinimum();
            Assert.assertTrue(Math.abs(FUNCTION2.apply(ANSWER2) - FUNCTION2.apply(res)) < eps);
        }
    }

    @Test
    public void brokenLineMethodTest3() {
        for (int i = MIN_POW; i < MAX_POW; i++) {
            double eps = Math.pow(10, -i);
            Method method = new BrokenLineMethod(FUNCTION3, L3, LEFT3, RIGHT3, eps);
            double res = method.findMinimum();
            Assert.assertTrue(Math.abs(FUNCTION3.apply(ANSWER3) - FUNCTION3.apply(res)) < eps);
        }
    }
}
