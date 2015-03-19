package com.ifmo.optimization.methods.transportation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Whiplash on 19.03.2015.
 */
public class PotentialsMethod extends TransportMethod {

    private static final double EPS = 1E-6;

    private final double[][] solution;
    private final int iter;
    private int curIter = 0;

    public PotentialsMethod(double[] provider, double[] consumer, double[][] cost, double[][] initialSolution, int iter) {
        super(provider, consumer, cost);
        this.solution = initialSolution;
        this.iter = iter;
    }

    @Override
    public double[][] find() {
        try {
            if (curIter >= iter) {
                return solution;
            }
            curIter++;
            correctSolution();
            Double[] u = new Double[provider.length];
            Double[] v = new Double[consumer.length];
            calculatePotentials(u, v);
            double[][] deltas = new double[u.length][v.length];
            Point minPoint;
            if ((minPoint = checkOptimalOfSolution(u, v, deltas)) == null) {
                return solution;
            } else {
                List<Point> graph = buildGraph(minPoint);
                recalculateSolution(graph);
                return find();
            }
        } catch (Exception e) {
            return find();
        }
    }

    private void recalculateSolution(List<Point> graph) {
        Point min = graph.get(1);
        for (int i = 3; i < graph.size(); i += 2) {
            Point p = graph.get(i);
            if (solution[min.y][min.x] > solution[p.y][p.x]) {
                min = p;
            }
        }
        double minValue = solution[min.y][min.x];
        for (int i = 0; i < graph.size(); i += 2) {
            Point p = graph.get(i);
            solution[p.y][p.x] += minValue;
        }
        for (int i = 1; i < graph.size(); i += 2) {
            Point p = graph.get(i);
            solution[p.y][p.x] -= minValue;
        }
    }

    private List<Point> buildGraph(Point first) {
        List<Point> graph = new ArrayList<>();
        graph.add(first);
        List<Point> row = buildGraphRow(graph, first);
        if (row != null) {
            row.remove(row.size() - 1);
            return row;
        }
        throw new IllegalStateException("Incorrect job (can't build graph)");
    }

    private List<Point> buildGraphRow(List<Point> graph, Point first) {
        Point last = graph.get(graph.size() - 1);
        for (int x = 0; x < solution[0].length; x++) {
            Point p = new Point(x, last.y);
            if (!p.equals(last) && solution[last.y][x] > 0) {
                List<Point> g = new ArrayList<>(graph);
                g.add(p);
                g = buildGraphColumn(g, first);
                if (g != null) {
                    return g;
                }
            }
            if (!p.equals(last) && p.equals(first)) {
                graph.add(p);
                return graph;
            }
        }
        return null;
    }

    private List<Point> buildGraphColumn(List<Point> graph, Point first) {
        Point last = graph.get(graph.size() - 1);
        for (int y = 0; y < solution.length; y++) {
            Point p = new Point(last.x, y);
            if (!p.equals(last) && solution[y][last.x] > 0) {
                List<Point> g = new ArrayList<>(graph);
                g.add(p);
                g = buildGraphRow(g, first);
                if (g != null) {
                    return g;
                }
            }
            if (!p.equals(last) && p.equals(first)) {
                graph.add(p);
                return graph;
            }
        }
        return null;
    }

    private Point checkOptimalOfSolution(Double[] u, Double[] v, double[][] deltas) {
        double min = 0;
        Point minPoint = new Point(0, 0);
        for (int y = 0; y < u.length; y++) {
            for (int x = 0; x < v.length; x++) {
                if (solution[y][x] > 0) {
                    deltas[y][x] = 0;
                } else {
                    deltas[y][x] = cost[y][x] - u[y] - v[x];
                    if (min > deltas[y][x]) {
                        min = deltas[y][x];
                        minPoint = new Point(x, y);
                    }
                }
            }
        }
        return min >= 0 ? null : minPoint;
    }

    private void calculatePotentials(Double[] u, Double[] v) {
        u[0] = 0.;
        calculatePotentialsRow(u, v, 0);
    }

    private void calculatePotentialsRow(Double[] u, Double[] v, int row) {
        if (u[row] != null) {
            for (int x = 0; x < v.length; x++) {
                if (solution[row][x] > 0) {
                    if (v[x] == null) {
                        v[x] = cost[row][x] - u[row];
                        calculatePotentialsColumn(u, v, x);
                    }
                }
            }
        }
    }

    private void calculatePotentialsColumn(Double[] u, Double[] v, int column) {
        if (v[column] != null) {
            for (int y = 0; y < u.length; y++) {
                if (solution[y][column] > 0) {
                    if (u[y] == null) {
                        u[y] = cost[y][column] - v[column];
                        calculatePotentialsRow(u, v, y);
                    }
                }
            }
        }
    }

    private void clearSolution() {
        for (int y = 0; y < solution.length; y++) {
            for (int x = 0; x < solution[0].length; x++) {
                if (solution[y][x] == EPS) {
                    solution[y][x] = 0;
                }
            }
        }
    }

    private void correctSolution() {
        clearSolution();
        while (provider.length + consumer.length - 1 > numberOfBasisCells(solution)) {
            Random random = new Random();
            while (true) { // it's ok... i'm so lazy to code fast solution of this problem...
                int y = random.nextInt(provider.length);
                int x = random.nextInt(provider.length);
                if (solution[y][x] == 0) {
                    solution[y][x] = EPS;
                    break;
                }
            }
        }
    }

    private int numberOfBasisCells(double[][] solution) {
        int counter = 0;
        for (double[] row : solution) {
            for (double cell : row) {
                if (cell > 0) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }
    }
}
