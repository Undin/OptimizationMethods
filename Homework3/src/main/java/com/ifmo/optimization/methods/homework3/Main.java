package com.ifmo.optimization.methods.homework3;

import com.ifmo.optimization.methods.transportation.NorthwestCorner;
import com.ifmo.optimization.methods.transportation.PotentialsMethod;
import com.ifmo.optimization.methods.transportation.TransportMethod;

import java.util.Arrays;

/**
 * Created by Whiplash on 19.03.2015.
 */
public class Main {

    private static final int ITER = 10;

    //    private static final double[] PROVIDER = {30, 40, 20};
//    private static final double[] CONSUMER = {20, 30, 30, 10};
//    private static final double[][] COST = {
//            {2, 3, 2, 4},
//            {3, 2, 5, 1},
//            {4, 3, 2, 6}
//    };
    private static final double[] PROVIDER = {5, 10};
    private static final double[] CONSUMER = {10, 5};
    private static final double[][] COST = {
            {1, 0},
            {0, 1}
    };

    public static void main(String[] args) {
        TransportMethod transportMethod = new NorthwestCorner(PROVIDER, CONSUMER, COST);
        double[][] initialSolution = transportMethod.find();
        double[][] solution = new PotentialsMethod(PROVIDER, CONSUMER, COST, initialSolution, ITER).find();
        for (double[] row : solution) {
            System.out.println(Arrays.toString(row));
        }
    }

}
