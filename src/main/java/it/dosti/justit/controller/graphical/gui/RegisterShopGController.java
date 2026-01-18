package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.controller.app.RegisterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegisterShopGController extends BaseGController {

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField imageField;

    @FXML
    private TextField OpeningField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button signInButton;

    @FXML
    void signInPressed() {
        ShopBean shopBean = new ShopBean();
        RegisterController registerController = new RegisterController();

        shopBean.setName(nameField.getText());
        shopBean.setAddress(addressField.getText());
        shopBean.setEmail(emailField.getText());
        shopBean.setPhone(phoneField.getText());
        shopBean.setImage(imageField.getText());
        shopBean.setOpeningHours(OpeningField.getText());
        shopBean.setDescription(descriptionField.getText());
        shopBean.setAddress(addressField.getText());
        shopBean.setHomeAssistance(true);

        if(registerController.registerNewShop(shopBean))
            {
            System.out.println("Register shop successful");
            }
        else{
            System.out.println("Register shop failed");
        }

    }

}
