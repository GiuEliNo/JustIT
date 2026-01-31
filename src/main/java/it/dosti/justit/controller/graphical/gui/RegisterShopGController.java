package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.controller.app.RegisterController;
import it.dosti.justit.ui.navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class RegisterShopGController extends BaseGController {

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField imageField;

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
    private TextField addressCountryField;
    @FXML
    private TextField addressCityField;

    @FXML
    private TextField addressStreetField;




    @FXML
    void signInPressed() {
        ShopBean shopBean = new ShopBean();
        RegisterController registerController = new RegisterController();

        shopBean.setName(nameField.getText());
        shopBean.setAddress(addressStreetField.getText());
        shopBean.setEmail(emailField.getText());
        shopBean.setPhone(phoneField.getText());
        shopBean.setImage(imageField.getText());
        shopBean.setOpeningHours(openingField1.getText()+" : " + openingField2.getText());
        shopBean.setDescription(descriptionField.getText());
        shopBean.setAddress(addressStreetField.getText() + ","  + addressCityField.getText() + "," + addressCountryField.getText());
        shopBean.setHomeAssistance(homeAssistanceCheck.isSelected());

        if(registerController.registerNewShop(shopBean))
            {
            System.out.println("Register shop successful");
            new MainGController(navigation);
            navigation.navigate(Screen.REGISTERTEC_VIEW);
            }
        else{
            System.out.println("Register shop failed");
        }

    }

}
