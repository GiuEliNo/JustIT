package it.dosti.justit.view.gui;

import it.dosti.justit.bean.RepairReportBean;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class DialogViewRepairReport extends Dialog<ButtonType> {

    public DialogViewRepairReport(RepairReportBean report) {
        setTitle("Repair Report");
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        VBox container = new VBox(12);
        container.setPrefWidth(420);

        TextArea notesArea = new TextArea(report.getTechNotes());
        notesArea.setEditable(false);
        notesArea.setWrapText(true);
        notesArea.setPrefHeight(140);

        GridPane costsGrid = new GridPane();
        costsGrid.setHgap(12);
        costsGrid.setVgap(8);

        costsGrid.add(new Label("Labor hours:"), 0, 0);
        costsGrid.add(new Label(String.valueOf(report.getLaborHours())), 1, 0);
        costsGrid.add(new Label("Hourly cost:"), 0, 1);
        costsGrid.add(new Label(formatCurrency(report.getCostHours())), 1, 1);
        costsGrid.add(new Label("Part costs:"), 0, 2);
        costsGrid.add(new Label(formatCurrency(report.getPartCosts())), 1, 2);
        costsGrid.add(new Label("Total:"), 0, 3);
        costsGrid.add(new Label(formatCurrency(calculateTotal(report))), 1, 3);

        container.getChildren().addAll(new Label("Technical Notes"), notesArea, costsGrid);
        VBox.setVgrow(notesArea, Priority.ALWAYS);

        getDialogPane().setContent(container);
    }

    private double calculateTotal(RepairReportBean report) {
        return report.getPartCosts() + report.getLaborHours() * report.getCostHours();
    }

    private String formatCurrency(double amount) {
        return String.format("€ %.2f", amount);
    }
}
