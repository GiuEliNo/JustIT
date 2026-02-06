package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.bean.UserBean;
import it.dosti.justit.controller.app.ShopController;
import it.dosti.justit.controller.app.UpdateController;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UpdateOnDBException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.utils.FilesToBlob;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.view.gui.DialogEditUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.File;

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
        DialogEditUser dialog = new DialogEditUser(EDIT_SHOP_ADDRESS, addressLabel.getText());

        dialog.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                ShopBean shopBean = new ShopBean();

                shopBean.setAddress(dialog.getNewValue());
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
        });
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
        //todo
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
