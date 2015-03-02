package com.ifmo.optimization.methods.homework2;

import com.ifmo.optimization.methods.multidimensional.Point;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
 * Created by Whiplash on 01.03.2015.
 */
public class Controller implements Initializable {

    private static final Function<Point, Double> FUNCTION = (v) -> Math.pow(v.x, 4) + Math.pow(v.y, 4) - 5 * (v.x * v.y - Math.pow(v.x * v.y, 2));
//    private static final Function<Double, Double, Double> FUNCTION = (x, y) -> x + y;

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

    private double x0Value;
    private double xlValue;
    private double xrValue;
    private double y0Value;
    private double ylValue;
    private double yrValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        x0.textProperty().addListener((observable, oldValue, newValue) -> changeValues());
        xl.textProperty().addListener((observable, oldValue, newValue) -> changeValues());
        xr.textProperty().addListener((observable, oldValue, newValue) -> changeValues());
        y0.textProperty().addListener((observable, oldValue, newValue) -> changeValues());
        yl.textProperty().addListener((observable, oldValue, newValue) -> changeValues());
        yr.textProperty().addListener((observable, oldValue, newValue) -> changeValues());
        run.setOnAction(event -> runMethod());
        changeValues();
    }

    private void runMethod() {
        //TODO run method
        buildFunctionGradient(plot, new Point(xlValue, ylValue), new Point(xrValue, yrValue), null);
    }


    private void buildFunctionGradient(ImageView plot, Point l, Point r, List<Point> v) {
        int prefWidth = 600;
        int prefHeight = 600;
        int width = (int) (prefWidth * (r.x - l.x < r.y - l.y ? (r.x - l.x) / (r.y - l.y) : 1));
        int height = (int) (prefHeight * (r.x - l.x > r.y - l.y ? (r.y - l.y) / (r.x - l.x) : 1));
        double xStep = (r.x - l.x) / width;
        double yStep = (r.y - l.y) / height;
        double[][] pixels = new double[height][width];
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        for (int xi = 0; xi < prefWidth; xi++) {
            for (int yi = 0; yi < prefHeight; yi++) {
                if (xi < width && yi < height) {
                    pixels[yi][xi] = FUNCTION.apply(new Point(l.x + xi * xStep, l.y + yi * yStep));
                    max = Math.max(pixels[yi][xi], max);
                    min = Math.min(pixels[yi][xi], min);
                }
            }
        }
        double length = max - min;
        WritableImage img = new WritableImage(prefWidth, prefHeight);
        PixelWriter pw = img.getPixelWriter();
        for (int xi = 0; xi < prefWidth; xi++) {
            for (int yi = 0; yi < prefHeight; yi++) {
                if (xi < width && yi < height) {
                    double mul = (pixels[yi][xi] - min) / length;
                    pw.setColor(xi, height - 1 - yi, Color.rgb((int) (255 * mul), (int) (255 * (1 - mul)), (int) (255 * (1 - mul))));
                } else {
                    pw.setColor(xi, height - 1 - yi, Color.WHITE);
                }
            }
        }
        plot.setImage(img);
        linePlot.getChildren().clear();
        for (int i = 0; i < v.size() - 1; i++) {
            double sx = (v.get(i).x - l.x) / xStep;
            double sy = (v.get(i).y - l.y) / yStep;
            double ex = (v.get(i + 1).x - l.x) / xStep;
            double ey = (v.get(i + 1).y - l.y) / yStep;
            Line line = new Line(sx, height - sy, ex, height - ey);
            linePlot.getChildren().add(line);
        }
    }

    private void changeValues() {
        try {
            x0Value = Double.parseDouble(x0.getText());
            xlValue = Double.parseDouble(xl.getText());
            xrValue = Double.parseDouble(xr.getText());
            y0Value = Double.parseDouble(y0.getText());
            ylValue = Double.parseDouble(yl.getText());
            yrValue = Double.parseDouble(yr.getText());
        } catch (Exception ignored) {
        }
    }

}
