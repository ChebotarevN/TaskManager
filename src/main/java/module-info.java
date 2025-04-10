module app.tasklist {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires dotenv;


    opens app.tasklist to javafx.fxml;
    exports app.tasklist;
}