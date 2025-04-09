module app.listproduct {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires dotenv;


    opens app.listproduct to javafx.fxml;
    exports app.listproduct;
}