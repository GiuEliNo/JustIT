package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.controller.app.ShopController;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UpdateOnDBException;
import it.dosti.justit.utils.FilesToBlob;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.utils.AddressDialog;
import it.dosti.justit.view.gui.DialogEditUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.util.Optional;

public class PageShopTechGController extends BaseGController{
    @FXML
    public Label nameLabel;

    @FXML
    public Label addressLabel;

    @FXML
    public TextArea descriptionArea;

    @FXML
    public Label emailLabel;

    @FXML
    public Label phoneLabel;

    @FXML
    public Label openingHoursLabel;

    @FXML
    public Label homeAssistanceLabel;

    @FXML
    public ImageView previewImage;

    private ShopController appController;

    private static final String EDIT_SHOP_NAME = "Edit Shop Name";
    private static final String EDIT_SHOP_ADDRESS = "Edit Shop Address";
    private static final String EDIT_SHOP_DESCRIPTION = "Edit Shop Description";
    private static final String EDIT_SHOP_EMAIL = "Edit Shop Email";
    private static final String EDIT_SHOP_PHONE = "Edit Shop Phone";
    private static final String EDIT_SHOP_OPENING_HOURS = "Edit Shop Opening Hours";
    private static final String EDIT_SHOP_HOME_ASSISTANCE = "Select to enable/disable home assistance";
    private static final String EDIT_SHOP_IMAGE = "Edit Shop Image";


    public void initialize() {
        this.updatePageInfo();
    }

    public void editShopNameClicked() {

        DialogEditUser dialog = new DialogEditUser(EDIT_SHOP_NAME, nameLabel.getText());

        dialog.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                ShopBean shopBean = new ShopBean();

                shopBean.setName(dialog.getNewValue());
                try {
                    if(!appController.editShopName(shopBean)){
                        Notifications.create()
                                .title(EDIT_SHOP_NAME)
                                .text("Error address not changed!")
                                .showError();
                    } else {
                        Notifications.create()
                                .title(EDIT_SHOP_NAME)
                                .text("Success!")
                                .showConfirm();
                    }
                } catch ( UpdateOnDBException e) {
                    JustItLogger.getInstance().error(e.getMessage(), e);
                }
            }
        });
        this.updatePageInfo();

    }

    public void editAddressButtonClicked() {
        Dialog<AddressDialog> dialog = new Dialog<>();
        dialog.setTitle(EDIT_SHOP_ADDRESS);
        dialog.setHeaderText("Update Shop Address");

        ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField streetField = new TextField();
        streetField.setPromptText("Es. Via Roma 1");

        TextField cityField = new TextField();
        cityField.setPromptText("Es. Milano");

        TextField countryField = new TextField();
        countryField.setPromptText("Es. Italia");

        Label labelStreet = new Label("Street:");
        Label labelCity = new Label("City:");
        Label labelCountry = new Label("Country:");

        grid.add(labelStreet, 0, 0);
        grid.add(streetField, 1, 0);
        grid.add(labelCity, 0, 1);
        grid.add(cityField, 1, 1);
        grid.add(labelCountry, 0, 2);
        grid.add(countryField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new AddressDialog(streetField.getText(), cityField.getText(), countryField.getText());
            }
            return null;
        });
        Optional<AddressDialog> result = dialog.showAndWait();

            if(result.isPresent()){
                ShopBean shopBean = new ShopBean();

                shopBean.setAddress(result.toString());
                try {
                    if(!appController.editShopAddress(shopBean)){
                        Notifications.create()
                                .title(EDIT_SHOP_ADDRESS)
                                .text("Error address not changed!")
                                .showError();
                    } else {
                        Notifications.create()
                                .title(EDIT_SHOP_ADDRESS)
                                .text("Success!")
                                .showConfirm();
                    }
                } catch ( UpdateOnDBException e) {
                    JustItLogger.getInstance().error(e.getMessage(), e);
                }
            }
        this.updatePageInfo();
    }

    public void editDescriptionButton() {
        DialogEditUser dialog = new DialogEditUser(EDIT_SHOP_DESCRIPTION, descriptionArea.getText());

        dialog.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                ShopBean shopBean = new ShopBean();

                shopBean.setDescription(dialog.getNewValue());
                try {
                    if(!appController.editShopDescription(shopBean)){
                        Notifications.create()
                                .title(EDIT_SHOP_DESCRIPTION)
                                .text("Error description not changed!")
                                .showError();
                    } else {
                        Notifications.create()
                                .title(EDIT_SHOP_DESCRIPTION)
                                .text("Success!")
                                .showConfirm();
                    }
                } catch ( UpdateOnDBException e) {
                    JustItLogger.getInstance().error(e.getMessage(), e);
                }
            }
        });
        this.updatePageInfo();
    }

    public void onEmailButtonClicked() {
        DialogEditUser dialog = new DialogEditUser(EDIT_SHOP_EMAIL, emailLabel.getText());

        dialog.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                ShopBean shopBean = new ShopBean();

                shopBean.setEmail(dialog.getNewValue());
                try {
                    if(!appController.editShopEmail(shopBean)){
                        Notifications.create()
                                .title(EDIT_SHOP_EMAIL)
                                .text("Error email not changed!")
                                .showError();
                    } else {
                        Notifications.create()
                                .title(EDIT_SHOP_EMAIL)
                                .text("Success!")
                                .showConfirm();
                    }
                } catch ( UpdateOnDBException e) {
                    JustItLogger.getInstance().error(e.getMessage(), e);
                }
            }
        });
        this.updatePageInfo();
    }

    public void onPhoneButtonClicked() {
        DialogEditUser dialog = new DialogEditUser(EDIT_SHOP_PHONE, phoneLabel.getText());

        dialog.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                ShopBean shopBean = new ShopBean();

                shopBean.setPhone(dialog.getNewValue());
                try {
                    if(!appController.editShopPhone(shopBean)){
                        Notifications.create()
                                .title(EDIT_SHOP_PHONE)
                                .text("Error phone not changed!")
                                .showError();
                    } else {
                        Notifications.create()
                                .title(EDIT_SHOP_PHONE)
                                .text("Success!")
                                .showConfirm();
                    }
                } catch ( UpdateOnDBException e) {
                    JustItLogger.getInstance().error(e.getMessage(), e);
                }
            }
        });
        this.updatePageInfo();
    }

    public void onOpeningHoursButtonClicked() {
        DialogEditUser dialog = new DialogEditUser(EDIT_SHOP_OPENING_HOURS, openingHoursLabel.getText());

        dialog.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                ShopBean shopBean = new ShopBean();

                shopBean.setOpeningHours(dialog.getNewValue());
                try {
                    if(!appController.editShopOpeningHours(shopBean)){
                        Notifications.create()
                                .title(EDIT_SHOP_OPENING_HOURS)
                                .text("Error Opening hours not changed!")
                                .showError();
                    } else {
                        Notifications.create()
                                .title(EDIT_SHOP_OPENING_HOURS)
                                .text("Success!")
                                .showConfirm();
                    }
                } catch ( UpdateOnDBException e) {
                    JustItLogger.getInstance().error(e.getMessage(), e);
                }
            }
        });
        this.updatePageInfo();
    }

    public void onHomeAssistanceButtonClicked() {

        Dialog<Boolean> dialog = new Dialog<Boolean>();
        dialog.setTitle(EDIT_SHOP_HOME_ASSISTANCE);

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        CheckBox checkBox = new CheckBox("Home Assistance?");
        if(appController.isHomeAssistance()){
            checkBox.setSelected(true);
        }
        VBox content = new VBox(checkBox);
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setSpacing(10);
        dialog.getDialogPane().setContent(content);
        dialog.setResultConverter(dialogButton ->{
            if(dialogButton == saveButtonType){
                return checkBox.isSelected();
            }
            return null;
        });

        Optional<Boolean> result = dialog.showAndWait();

        result.ifPresent(response -> {
                ShopBean shopBean = new ShopBean();
                shopBean.setHomeAssistance(response);
                try {
                    if(!appController.editShopHomeAssistance(shopBean)){
                        Notifications.create()
                                .title(EDIT_SHOP_NAME)
                                .text("Error changing home assistance preference!")
                                .showError();
                    } else {
                        Notifications.create()
                                .title(EDIT_SHOP_NAME)
                                .text("Success!")
                                .showConfirm();
                    }
                } catch ( UpdateOnDBException e) {
                    JustItLogger.getInstance().error(e.getMessage(), e);
                }
        });
        this.updatePageInfo();
    }

    public void onImageButtonClicked(ActionEvent event) {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select image file");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg"));

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if(selectedFile != null) {
                Image imageCache = new Image(selectedFile.toURI().toString(),200,0,true,true);
                previewImage.setImage(imageCache);

                byte[] blobDb = new FilesToBlob().convertFileToBlob(selectedFile);
                try{
                    ShopBean shopBean = new ShopBean();
                    shopBean.setImage(blobDb);
                    if(!appController.editShopImage(shopBean)) {
                        Notifications.create()
                                .title(EDIT_SHOP_IMAGE)
                                .text("Error image not changed!")
                                .showError();
                    }
                    else{
                        Notifications.create()
                                .title(EDIT_SHOP_IMAGE)
                                .text("Success!")
                                .showConfirm();
                    }
                } catch (UpdateOnDBException e) {
                    JustItLogger.getInstance().error(e.getMessage(), e);
                }
            }
            this.updatePageInfo();
    }



    private void updatePageInfo() {
        appController = new ShopController();
        ShopBean shopBean = appController.getShopBean();

        if (shopBean != null) {
            nameLabel.setText(shopBean.getName());
            addressLabel.setText(shopBean.getAddress());
            descriptionArea.setText(shopBean.getDescription());
            phoneLabel.setText(shopBean.getPhone());
            emailLabel.setText(shopBean.getEmail());
            openingHoursLabel.setText(shopBean.getOpeningHours());
            homeAssistanceLabel.setText(shopBean.getHomeAssistanceMessage());
        }
        try {
            previewImage.setImage(appController.getShopImage());
        } catch (ShopNotFoundException e) {
            JustItLogger.getInstance().warn("Shop image not found");
        }
    }
}
