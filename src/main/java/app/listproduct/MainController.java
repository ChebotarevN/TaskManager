package app.listproduct;

import app.dao.impl.ListProductDAO;
import app.model.ProductList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import app.model.Product;
import javafx.scene.layout.GridPane;

public class MainController {
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> colId;
    @FXML
    private TableColumn<Product, String> colName;
    @FXML
    private TableColumn<Product, Integer> colQuantity;
    @FXML
    private TableColumn<Product, String> colTag;

    private ProductList productList;

    private ObservableList<Product> productsData;

    @FXML
    public void initialize() {
        productList = new ProductList(new ListProductDAO(10).getAllProducts());

        colId.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        colQuantity.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        colTag.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTag()));

        loadData();

        // Инициализация данных таблицы
        productsData = FXCollections.observableArrayList(productList.getProducts());
        productsTable.setItems(productsData);
    }

    private void loadData() {
        productList.addProduct(new Product(0, "Молоко", 2, "Молочное изделие"));
        productList.addProduct(new Product(0, "Мыло", 5, "Бчтовая химия"));
    }

    @FXML
    private void handleAddProduct() {
        // Создание диалогового окна
        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Добавить продукт");

        // Кнопки
        ButtonType addButton = new ButtonType("Добавить", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Отменить", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        // Поля ввода
        TextField nameField = new TextField();
        Spinner<Integer> quantitySpinner = new Spinner<>(1, 100, 1);
        TextField tagField = new TextField();

        GridPane grid = new GridPane();
        grid.add(new Label("Название:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Количество:"), 0, 1);
        grid.add(quantitySpinner, 1, 1);
        grid.add(new Label("Тег:"), 0, 2);
        grid.add(tagField, 1, 2);
        dialog.getDialogPane().setContent(grid);

        // Обработка результата
        dialog.setResultConverter(buttonType -> {
            if (buttonType == addButton) {
                return new Product(
                        0,
                        nameField.getText(),
                        quantitySpinner.getValue(),
                        tagField.getText()
                );
            }
            return null;
        });

        // Добавление продукта
        dialog.showAndWait().ifPresent(product -> {
            if (product.getName() != null && product.getTag() != null) {
                productList.addProduct(product);
                productsData.setAll(productList.getProducts());
            }
        });
    }

    @FXML
    private void handleDeleteProduct() {
        Product selected = productsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            productList.removeProduct(selected.getId());
            productsData.remove(selected);
        } else {
            showAlert("Ошибка удаления", "Выберите продукт для удаления!");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}