module com.dostisrl.justit {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.dostisrl.justit to javafx.fxml;
    exports com.dostisrl.justit;
}