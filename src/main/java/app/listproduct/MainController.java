package app.listproduct;

import app.dao.ProductDAO;
import app.dao.ProductFabrica;
import app.model.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

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
    @FXML
    private TableColumn<Product, StatusEnum> colStatus;
    @FXML
    private ComboBox<String> workMode;

    private ProductDAO productList;
    private TagList tagList;

    private ObservableList<Product> productsData;

    @FXML
    public void initialize() {
        loadData();
        colId.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        colQuantity.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        colTag.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTag().getName()));
        colStatus.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getStatus()));
        workMode.setItems(FXCollections.observableArrayList(ProductFabrica.BD, ProductFabrica.FILE, ProductFabrica.RAM));
        workMode.getSelectionModel().selectedIndexProperty().addListener(
                (_, _, t) -> {
                    try {
                        changeWorkMode();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public void changeWorkMode() throws FileNotFoundException {
        productList = ProductFabrica.createDAO(workMode.getValue(), tagList);
        assert productList != null;
        productsData = FXCollections.observableArrayList(productList.getAllProducts());
        productsTable.setItems(productsData);
    }

    private void loadData() {
        tagList = new TagList();
        List<String> items = List.of("Молоко", "Сыр", "Творог", "Йогурт", "Масло");
        tagList.addTag(new Tag("Молочное изделие", 20, 40, items));
        items = List.of("Свинина", "Говядина", "Курица", "Рыба", "Колбаса");
        tagList.addTag(new Tag("Мясо и мясные продукты", 10, 30, items));
        items = List.of("Хлеб", "Булочки", "Багеты", "Тосты", "Печенье");
        tagList.addTag(new Tag("Хлебобулочное изделие", 30, 60, items));
    }

    @FXML
    private void handleAddProduct() {
        // Создание диалогового окна
        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Добавить продукт");

        // Кнопки
        ButtonType addButton = new ButtonType("Добавить продукт", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Отменить", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        // Поля ввода
        TextField nameField = new TextField();
        nameField.setPromptText("Введите название товара");
        TextField quantityField = new TextField();
        quantityField.setPromptText("Введите количество товара");
        ComboBox<Tag> tagComboBox = new ComboBox<>(FXCollections.observableArrayList(tagList.getAllTags()));
        tagComboBox.setPromptText("Выберите категорию товара");

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(10);
        grid.add(new Label("Название:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Количество:"), 0, 1);
        grid.add(quantityField, 1, 1);
        grid.add(new Label("Категория:"), 0, 2);
        grid.add(tagComboBox, 1, 2);
        dialog.getDialogPane().setContent(grid);

        // Обработка результата
        dialog.setResultConverter(buttonType -> {
            try {
                if (buttonType == addButton) {
                    if (Objects.equals(nameField.getText(), "")) throw new Exception("Пустая строка в названии товара");
                    if (Objects.equals(quantityField.getText(), ""))
                        throw new Exception("Пустая строка в количестве товара");
                    return new Product(
                            0,
                            nameField.getText(),
                            Integer.parseInt(quantityField.getText()),
                            tagComboBox.getValue()
                    );
                }
            } catch (NumberFormatException e) {
                showAlert("Ошибка ввода", "Введите числовое значение в поле количества");
            } catch (NullPointerException e) {
                showAlert("Ошибка ввода", "Выберите значение в поле категории");
            } catch (Exception e) {
                showAlert("Ошибка ввода", e.getMessage());
            }
            return null;
        });
        // Добавление продукта
        dialog.showAndWait().ifPresent(product -> {
            if ((product.getName() != null && !product.getName().isEmpty()) && product.getTag() != null) {
                productList.addProduct(product);
                productsData.setAll(productList.getAllProducts());
            }
        });
    }

    @FXML
    private void handleChangeProduct() {
        Product selected = productsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Ошибка изменения", "Выберите продукт для изменения!");
            return;
        }
        // Создание диалогового окна
        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Изменить продукт");

        // Кнопки
        ButtonType addButton = new ButtonType("Изменить", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Отменить", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        // Поля ввода
        TextField nameField = new TextField();
        nameField.setText(selected.getName());
        TextField quantityField = new TextField();
        quantityField.setText(String.valueOf(selected.getQuantity()));
        ComboBox<Tag> tagComboBox = new ComboBox<>(FXCollections.observableArrayList(tagList.getAllTags()));
        tagComboBox.setValue(selected.getTag());

        GridPane grid = new GridPane();
        grid.add(new Label("Название:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Количество:"), 0, 1);
        grid.add(quantityField, 1, 1);
        grid.add(new Label("Категория:"), 0, 2);
        grid.add(tagComboBox, 1, 2);
        dialog.getDialogPane().setContent(grid);

        // Обработка результата
        dialog.setResultConverter(buttonType -> {
            try {
                if (buttonType == addButton) {
                    if (Objects.equals(nameField.getText(), ""))
                        throw new Exception("Пустая строка в названии товара");
                    if (Objects.equals(quantityField.getText(), ""))
                        throw new Exception("Пустая строка в количестве товара");
                    Product change = new Product(selected.getId(), nameField.getText(),
                            Integer.parseInt(quantityField.getText()),
                            tagComboBox.getValue());
                    productList.updateProduct(change);
                    productsData.setAll(productList.getAllProducts());
                }
            } catch (NumberFormatException e) {
                showAlert("Ошибка ввода", "Введите числовое значение в поле количества");
            } catch (NullPointerException e) {
                showAlert("Ошибка ввода", "Выберите значение в поле категории");
            } catch (Exception e) {
                showAlert("Ошибка ввода", e.getMessage());
            }
            return null;
        });
        dialog.showAndWait();
        productsTable.refresh();
    }

    @FXML
    private void handleDeleteProduct() {
        Product selected = productsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            productList.deleteProduct(selected.getId());
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

    public static File selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Текстовый файл", ".txt"));
        return fileChooser.showOpenDialog(null);
    }
}