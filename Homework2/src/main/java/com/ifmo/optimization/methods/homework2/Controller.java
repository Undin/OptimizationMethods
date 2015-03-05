package com.ifmo.optimization.methods.homework2;

import com.ifmo.optimization.methods.multidimensional.FastGradientDescent;
import com.ifmo.optimization.methods.multidimensional.GradientDescent;
import com.ifmo.optimization.methods.multidimensional.Point;
import com.ifmo.optimization.methods.multidimensional.SimpleGradientDescent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by Whiplash on 01.03.2015.
 */
public class Controller implements Initializable {

    private static final int PREF_WIDTH = 600;
    private static final int PREF_HEIGHT = 600;

    private static final int CONTOUR_LEVELS = 25;

    private static final Function<Point, Double> FUNCTION = (v) -> Math.pow(v.x, 4) + Math.pow(v.y, 4) - 5 * (v.x * v.y - Math.pow(v.x * v.y, 2));
    private static final Function<Point, Point> GRADIENT = p -> {
        double x = 4 * Math.pow(p.x, 3) - 5 * (p.y - 2 * p.x * Math.pow(p.y, 2));
        double y = 4 * Math.pow(p.y, 3) - 5 * (p.x - 2 * p.y * Math.pow(p.x, 2));
        return new Point(x, y);
    };
//    private static final Function<Point, Double, Double> FUNCTION = (v) -> v.x + v.y;

    @FXML
    private ImageView plot;
    @FXML
    private Pane linePlot;
    @FXML
    private ChoiceBox<String> methodSelector;
    @FXML
    private Button run;
    @FXML
    private Label result;
    @FXML
    private Label fComp;
    @FXML
    private Label gComp;

    @FXML
    private TextField x0;
    @FXML
    private TextField xl;
    @FXML
    private TextField xr;
    @FXML
    private TextField y0;
    @FXML
    private TextField yl;
    @FXML
    private TextField yr;
    @FXML
    private TextField eps;

    private double x0Value;
    private double xlValue;
    private double xrValue;
    private double y0Value;
    private double ylValue;
    private double yrValue;
    private double epsValue;

    private double xStep;
    private double yStep;
    private int height;
    private int width;

    private Set<Double> levelStorage = new HashSet<>();

    private Render render = (startX, startY, endX, endY, contourLevel) -> {
        drawLine(startX, startY, endX, endY, Color.GRAY, 1);
        if (!levelStorage.contains(contourLevel) && isCorrectHeight(startY, endY)) {
            drawText(startX, startY, Color.BLACK, String.format("%.2f", contourLevel));
            levelStorage.add(contourLevel);
        }
    };

    private boolean isCorrectHeight(double sY, double eY) {
        double up = (ylValue + yrValue) * 0.55;
        double down = (ylValue + yrValue) * 0.45;
        return (up >= sY && sY >= down) || (up >= eY && eY >= down);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        x0.textProperty().addListener((observable, oldValue, newValue) -> changeValues());
        xl.textProperty().addListener((observable, oldValue, newValue) -> changeValues());
        xr.textProperty().addListener((observable, oldValue, newValue) -> changeValues());
        y0.textProperty().addListener((observable, oldValue, newValue) -> changeValues());
        yl.textProperty().addListener((observable, oldValue, newValue) -> changeValues());
        yr.textProperty().addListener((observable, oldValue, newValue) -> changeValues());
        eps.textProperty().addListener((observable, oldValue, newValue) -> changeValues());
        run.setOnAction(event -> runMethod());
        changeValues();
    }

    private void runMethod() {
        width = PREF_WIDTH;
        height = PREF_HEIGHT;

        double realWidth = xrValue - xlValue;
        double realHeight = yrValue - ylValue;

        if (realWidth < realHeight) {
            width = (int) (PREF_WIDTH * realWidth / realHeight);
        } else {
            height = (int) (PREF_HEIGHT * realHeight / realWidth);
        }
        xStep = realWidth / width;
        yStep = realHeight / height;

        Point rightTop = new Point(xrValue, yrValue);
        Point leftBottom = new Point(xlValue, ylValue);
        Point start = new Point(x0Value, y0Value);
        GradientDescent method = getMethod(leftBottom, rightTop, start, epsValue);
        Point min = method.findMinimum();
        result.setText(String.format("f(%.3f, %.3f) = %.3f", min.x, min.y, FUNCTION.apply(min)));
        fComp.setText("" + method.getFunctionComputations());
        gComp.setText("" + method.getGradientComputations());

        levelStorage.clear();
        linePlot.getChildren().clear();
        drawFunctionContourMap();
        drawMethodSteps(method.getPoints());
    }

    private GradientDescent getMethod(Point left, Point right, Point start, double eps) {
        switch (methodSelector.getSelectionModel().getSelectedIndex()) {
            case 0:
                return new SimpleGradientDescent(FUNCTION, GRADIENT, left, right, start, eps);
            case 1:
                return new FastGradientDescent(FUNCTION, GRADIENT, left, right, start, eps);
        }
        return null;
    }

    private void drawMethodSteps(List<Point> path) {
        for (int i = 0; i < path.size() - 1; i++) {
            drawLine(path.get(i), path.get(i + 1), Color.ORANGERED, 2);
        }
    }

    private void drawFunctionContourMap() {
        double[] x = new double[width];
        double[] y = new double[height];
        Arrays.setAll(x, i -> xlValue + i * xStep);
        Arrays.setAll(y, i -> ylValue + i * yStep);

        double[][] pixels = new double[height][width];
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = FUNCTION.apply(new Point(x[i], y[j]));
                max = Math.max(pixels[i][j], max);
                min = Math.min(pixels[i][j], min);
            }
        }

        double[] z = new double[CONTOUR_LEVELS];
        double zStep = (max - min) / CONTOUR_LEVELS;
        double finalMin = min;
        Arrays.setAll(z, i -> finalMin * 1.05 + i * zStep);

        Conrec conrec = new Conrec(render);
        conrec.contour(pixels, 0, width - 1, 0, height - 1, x, y, z.length, z);
    }

    private void drawLine(Point start, Point end, Color color, double width) {
        drawLine(start.x, start.y, end.x, end.y, color, width);
    }

    private void drawLine(double startX, double startY, double endX, double endY, Color color, double width) {
        double sx = (startX - xlValue) / xStep;
        double sy = (startY - ylValue) / yStep;
        double ex = (endX - xlValue) / xStep;
        double ey = (endY - ylValue) / yStep;
        Line line = new Line(sx, height - sy, ex, height - ey);
        line.setStroke(color);
        line.setStrokeWidth(width);
        linePlot.getChildren().add(line);
    }

    private void drawText(double startX, double startY, Color color, String textValue) {
        double sx = (startX - xlValue) / xStep;
        double sy = (startY - ylValue) / yStep;
        Text text = new Text(sx, height - sy, textValue);
        text.setFill(color);
        linePlot.getChildren().add(text);
    }

    private void changeValues() {
        try {
            x0Value = Double.parseDouble(x0.getText());
            xlValue = Double.parseDouble(xl.getText());
            xrValue = Double.parseDouble(xr.getText());
            y0Value = Double.parseDouble(y0.getText());
            ylValue = Double.parseDouble(yl.getText());
            yrValue = Double.parseDouble(yr.getText());
            epsValue = Double.parseDouble(eps.getText());
        } catch (Exception ignored) {
        }
    }
}
