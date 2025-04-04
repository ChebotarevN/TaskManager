module app.listproduct {
    requires javafx.controls;
    requires javafx.fxml;


    opens app.listproduct to javafx.fxml;
    exports app.listproduct;
}