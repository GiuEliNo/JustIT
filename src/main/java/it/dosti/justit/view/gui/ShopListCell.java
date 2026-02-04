package it.dosti.justit.view.gui;

import it.dosti.justit.model.Shop;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ShopListCell extends ListCell<Shop> {
    @Override
    protected void updateItem(Shop item, boolean empty) {
        super.updateItem(item, empty);

        setStyle("-fx-background-color: transparent; -fx-padding: 5px;");

        if(empty || item == null) {
            setText(null);
            setGraphic(null);
        }

        else {
            VBox cardContainer = new VBox(10);
            cardContainer.setPadding(new Insets(10));


            cardContainer.getStyleClass().add("shop-card");
            HBox hbox = new HBox(10);

            Label nameLabel = new Label(item.getName());
            nameLabel.setFont(Font.font("System", FontWeight.BOLD, 13));
            nameLabel.setTextFill(Color.BLACK);
            nameLabel.setWrapText(true);

            hbox.getChildren().addAll(nameLabel);
            cardContainer.getChildren().add(hbox);
            setGraphic(cardContainer);


        }
    }
}
