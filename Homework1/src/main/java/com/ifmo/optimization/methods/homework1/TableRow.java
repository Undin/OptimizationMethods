package com.ifmo.optimization.methods.homework1;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableRow {

    private final int step;
    private final double left;
    private final double right;
    private final double range;
    private final String quotient;

    public TableRow(int step, double left, double right, double prevQuotient) {
        this.step = step;
        this.left = left;
        this.right = right;
        this.range = right - left;
        if (prevQuotient == 0) {
            this.quotient = "-";
        } else {
            this.quotient = String.valueOf(range / prevQuotient);
        }
    }

    public int getStep() {
        return step;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public double getRange() {
        return range;
    }

    public String getQuotient() {
        return quotient;
    }

    public static void setConfig(TableView<TableRow> table) {
        table.getColumns().clear();
        table.getColumns().add(generateColumn("#", "step", 70));
        table.getColumns().add(generateColumn("Left", "left", 80));
        table.getColumns().add(generateColumn("Right", "right", 80));
        table.getColumns().add(generateColumn("Range", "range", 80));
        table.getColumns().add(generateColumn("Quot.", "quotient", 80));
    }

    private static <V> TableColumn<TableRow, V> generateColumn(String name, String fieldName, int width) {
        TableColumn<TableRow, V> column = new TableColumn<>(name);
        column.setMinWidth(width);
        column.setMaxWidth(width);
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        return column;
    }

}
