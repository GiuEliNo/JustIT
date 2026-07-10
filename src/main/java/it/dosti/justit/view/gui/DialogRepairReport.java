package it.dosti.justit.view.gui;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class DialogRepairReport extends Dialog<ButtonType> {

    private final TextArea techNotesArea;
    private final Spinner<Double> laborHoursSpinner;
    private final Spinner<Double> partCostsSpinner;

    public DialogRepairReport() {
        setTitle("Repair Report");
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        VBox vbox = new VBox(10);
        vbox.setPrefWidth(400);

        techNotesArea = new TextArea();
        techNotesArea.setPromptText("Technical notes...");
        techNotesArea.setWrapText(true);
        techNotesArea.setPrefHeight(150);

        laborHoursSpinner = new Spinner<>();
        laborHoursSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(
                        0.0,
                        1000.0,
                        0.0,
                        0.5
                )
        );
        laborHoursSpinner.setEditable(true);

        partCostsSpinner = new Spinner<>();
        partCostsSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(
                        0.0,
                        100000.0,
                        0.0,
                        1.0
                )
        );
        partCostsSpinner.setEditable(true);

        vbox.getChildren().addAll(
                new Label("Technical Notes"),
                techNotesArea,

                new Label("Labor Hours"),
                laborHoursSpinner,

                new Label("Part Costs (€)"),
                partCostsSpinner
        );

        getDialogPane().setContent(vbox);
    }

    public String getTechNotes() {
        return techNotesArea.getText();
    }

    public double getLaborHours() {
        return laborHoursSpinner.getValue();
    }

    public double getPartCosts() {
        return partCostsSpinner.getValue();
    }
}