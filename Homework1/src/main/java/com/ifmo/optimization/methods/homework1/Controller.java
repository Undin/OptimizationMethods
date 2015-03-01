package com.ifmo.optimization.methods.homework1;

import com.ifmo.optimization.methods.onedimensional.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    private static final String[] METHODS_NAME = {"Dichotomy", "Fibonacci", "Golden Section", "Uniform Sequential Search", "Broken Line Search"};
    private static final String[] PLOT_NAME = {"Sequential", "Broken Line"};
    private static final Function<Double, Double> FUNCTION = x -> x * x - 3 * x + 2;
    private static final String FUNCTION_TEXT = "f(x) = x * x - 3 * x + 2";

    @FXML
    private TableView<TableRow> table;
    @FXML
    private LineChart<Double, Double> plot;
    @FXML
    private TextField eps;
    @FXML
    private TextField left;
    @FXML
    private TextField right;
    @FXML
    private TextField nl;
    @FXML
    private Label nlLabel;
    @FXML
    private Label iter;
    @FXML
    private Label numbComp;
    @FXML
    private Label result;
    @FXML
    private Label function;
    @FXML
    private ChoiceBox<String> methodSelector;
    @FXML
    private ChoiceBox<String> plotSelector;

    private double epsValue;
    private double leftValue;
    private double rightValue;
    private double nlValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        methodSelector.setItems(FXCollections.observableArrayList(METHODS_NAME));
        plotSelector.setItems(FXCollections.observableArrayList(PLOT_NAME));
        TableRow.setConfig(table);
        nlLabel.setVisible(false);
        nl.setVisible(false);
        function.setText(FUNCTION_TEXT);
        methodSelector.getSelectionModel().selectedIndexProperty().addListener(new MethodSelectorChanger());
        plotSelector.getSelectionModel().selectedIndexProperty().addListener(new PlotSelectorChanger());
        eps.textProperty().addListener((observable, oldValue, newValue) -> changeValues());
        left.textProperty().addListener((observable, oldValue, newValue) -> changeValues());
        right.textProperty().addListener((observable, oldValue, newValue) -> changeValues());
        nl.textProperty().addListener((observable, oldValue, newValue) -> changeValues());
        changeValues();
    }

    private Method getCurrentMethod(int index) {
        switch (index) {
            case 0:
                return new Dichotomy(FUNCTION, leftValue, rightValue, epsValue);
            case 1:
                return new FibonacciMethod(FUNCTION, leftValue, rightValue, epsValue);
            case 2:
                return new GoldenSectionMethod(FUNCTION, leftValue, rightValue, epsValue);
            case 3:
                return new UniformSearchMethod(FUNCTION, leftValue, rightValue, epsValue, (int) nlValue);
            case 4:
                return new BrokenLineMethod(FUNCTION, nlValue, leftValue, rightValue, epsValue);
        }
        return null;
    }

    private void changeValues() {
        try {
            epsValue = Double.parseDouble(eps.getText());
            leftValue = Double.parseDouble(left.getText());
            rightValue = Double.parseDouble(right.getText());
            nlValue = Double.parseDouble(nl.getText());
            new MethodExecutor(getCurrentMethod(methodSelector.getSelectionModel().getSelectedIndex())).run();
            buildPlot(plotSelector.getSelectionModel().getSelectedIndex());
        } catch (Exception ignored) {
        }
    }

    private void updateValues(Method method, double resultValue, List<TableRow> rows) {
        iter.setText("" + method.getIterations());
        numbComp.setText("" + method.getFunctionComputations());
        result.setText("" + resultValue);
        if (rows != null) {
            table.setItems(FXCollections.observableArrayList(rows));
        }
    }

    private void buildPlot(int position) {
        switch (position) {
            case 0:
                methodSelector.getSelectionModel().select(0);
                new SeqPlotBuilder(leftValue, rightValue).run();
                break;
            case 1:
                methodSelector.getSelectionModel().select(4);
                new BrokenLinePlotBuilder(leftValue, rightValue, epsValue, nlValue).run();
                break;
        }
    }

    private LineChart.Series<Double, Double> getSeries(String name, List<Double> xAxis, List<Double> yAxis) {
        LineChart.Series<Double, Double> series = new LineChart.Series<>();
        series.setName(name);
        for (int i = 0; i < xAxis.size(); i++) {
            series.getData().add(new XYChart.Data<>(xAxis.get(i), yAxis.get(i)));
        }
        return series;
    }

    private void setPlots(LineChart<Double, Double> plot, LineChart.Series<Double, Double>... series) {
        ObservableList<XYChart.Series<Double, Double>> lineChartData = FXCollections.observableArrayList();
        lineChartData.addAll(series);
        plot.setData(lineChartData);
    }

    private class BrokenLinePlotBuilder implements Runnable {
        private final double left;
        private final double right;
        private final double eps;
        private final double l;

        public BrokenLinePlotBuilder(double left, double right, double eps, double l) {
            this.left = left;
            this.right = right;
            this.eps = eps;
            this.l = l;
        }

        @Override
        public void run() {
            BrokenLineMethod method = new BrokenLineMethod(FUNCTION, l, left, right, eps);
            method.findMinimum();
            List<Double> f = method.getXList().stream().map(FUNCTION::apply).collect(Collectors.toList());
            setPlots(plot, getSeries("Graphic", method.getXList(), f),
                    getSeries("Broken line", method.getXList(), method.getYList()));
        }
    }

    private class SeqPlotBuilder implements Runnable {
        private final double left;
        private final double right;

        public SeqPlotBuilder(double left, double right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public void run() {
            List<Double> xAxis = new ArrayList<>();
            List<Double> yAxis1 = new ArrayList<>();
            List<Double> yAxis2 = new ArrayList<>();
            List<Double> yAxis3 = new ArrayList<>();
            for (double eps = 0.1; eps >= 1e-7; eps /= 2) {
                xAxis.add(-Math.log(eps));
                yAxis1.add(getIteration(new Dichotomy(FUNCTION, left, right, eps)));
                yAxis2.add(getIteration(new FibonacciMethod(FUNCTION, left, right, eps)));
                yAxis3.add(getIteration(new GoldenSectionMethod(FUNCTION, left, right, eps)));
            }
            setPlots(plot, getSeries(METHODS_NAME[0], xAxis, yAxis1),
                    getSeries(METHODS_NAME[1], xAxis, yAxis2),
                    getSeries(METHODS_NAME[2], xAxis, yAxis3));
        }

        private double getIteration(Method method) {
            method.findMinimum();
            return method.getFunctionComputations();
        }

    }

    private class MethodExecutor implements Runnable {
        private Method method;

        public MethodExecutor(Method method) {
            this.method = method;
        }

        @Override
        public void run() {
            double result = method.findMinimum();
            final List<TableRow> rows = new ArrayList<>();
            if (method instanceof SequentialMethod) {
                SequentialMethod seqMethod = (SequentialMethod) method;
                double prevRange = 0;
                for (int i = 0; i < seqMethod.getIterations(); i++) {
                    rows.add(new TableRow(i, seqMethod.getLeft(i), seqMethod.getRight(i), prevRange));
                    prevRange = seqMethod.getRight(i) - seqMethod.getLeft(i);
                }
            }
            updateValues(method, result, rows);
        }
    }

    private class MethodSelectorChanger implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            nlLabel.setVisible(true);
            nl.setVisible(true);
            switch ((int) newValue) {
                case 3:
                    setPlots(plot);
                    nlLabel.setText("N = ");
                    break;
                case 4:
                    plotSelector.getSelectionModel().select(1);
                    nlLabel.setText("L = ");
                    break;
                default:
                    plotSelector.getSelectionModel().select(0);
                    nlLabel.setVisible(false);
                    nl.setVisible(false);
            }
            new MethodExecutor(getCurrentMethod((int) newValue)).run();
        }
    }

    private class PlotSelectorChanger implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            buildPlot((int) newValue);
        }
    }

}
