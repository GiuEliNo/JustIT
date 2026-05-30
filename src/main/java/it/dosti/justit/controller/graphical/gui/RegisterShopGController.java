package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.controller.app.RegisterController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.exceptions.RegisterOnBackEndException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.FilesToBlob;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.view.gui.LoadingOverlayUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.util.concurrent.CompletableFuture;

public class RegisterShopGController extends BaseGController {


    @FXML
    private StackPane rootPane;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Button imagePickerButton;

    @FXML
    private TextField openingField1;

    @FXML
    private TextField openingField2;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private CheckBox homeAssistanceCheck;

    @FXML
    private Button signInButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField addressCountryField;
    @FXML
    private TextField addressCityField;

    @FXML
    private TextField addressStreetField;

    @FXML
    private ImageView previewImage;

    private byte[] blobDb;


    @FXML
    void signInPressed() {

        VBox loadingOverlay = LoadingOverlayUtils.buildLoadingOverlay("Registering shop in progress\nPlease wait...");

        rootPane.getChildren().add(loadingOverlay);

        ShopBean shopBean = new ShopBean();
        RegisterController registerController = new RegisterController();

        shopBean.setName(nameField.getText());
        shopBean.setAddress(addressStreetField.getText());
        shopBean.setEmail(emailField.getText());
        shopBean.setPhone(phoneField.getText());
        shopBean.setOpeningHours(openingField1.getText()+" : " + openingField2.getText());
        shopBean.setDescription(descriptionField.getText());
        shopBean.setAddress(addressStreetField.getText() + ","  + addressCityField.getText() + "," + addressCountryField.getText());
        shopBean.setHomeAssistance(homeAssistanceCheck.isSelected());
        shopBean.setImage(blobDb);

        CompletableFuture.supplyAsync(() -> {

            try {

                return registerController.registerNewShop(shopBean);

            }catch(RegisterOnBackEndException e){
                JustItLogger.getInstance().error(e.getMessage());
                return false;
            }
        }).thenAccept(result -> Platform.runLater(() -> {
            if(Boolean.TRUE.equals(result)) {
                JustItLogger.getInstance().info("Register shop successful");
                LoadingOverlayUtils.animateTransition(rootPane, loadingOverlay, navigation, Screen.REGISTER_TECH);
            }
            else{
                JustItLogger.getInstance().info("Register shop failed.\nTry again.");
                rootPane.getChildren().remove(loadingOverlay);
            }
        }));

    }

    @FXML
    void onPickerClick(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg"));

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null) {
            Image imageCache = new Image(selectedFile.toURI().toString(),200,0,true,true);
            previewImage.setImage(imageCache);

            blobDb = new FilesToBlob().convertFileToBlob(selectedFile);
        }


    }

    @FXML
    void backButtonPressed() throws NavigationException {
        navigation.navigate(Screen.REGISTER_TECH);
    }

}
