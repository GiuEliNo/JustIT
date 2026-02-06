package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.controller.app.RegisterController;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.FilesToBlob;
import it.dosti.justit.utils.JustItLogger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class RegisterShopGController extends BaseGController {

    @FXML
    private TextField descriptionField;

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

        try {
            if (registerController.registerNewShop(shopBean)) {
                JustItLogger.getInstance().info("Register shop successful");
                navigation.navigate(Screen.MAIN);
                navigation.navigate(Screen.REGISTERTEC_VIEW);
            } else {
                throw new RegisterOnDbException("Register shop failed");
            }

        }catch(RegisterOnDbException e){
            JustItLogger.getInstance().error(e.getMessage());
        }

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
    void backButtonPressed() {
        navigation.navigate(Screen.MAIN);
        navigation.navigate(Screen.REGISTERTEC_VIEW);
    }

}
