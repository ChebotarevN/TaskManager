package app.tasklist;

import app.dao.TaskDAO;
import app.dao.TaskFabrica;
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
import java.util.Objects;

public class MainController {
    @FXML
    private TableView<Task> taskTable;
    @FXML
    private TableColumn<Task, Integer> colId;
    @FXML
    private TableColumn<Task, String> colName;
    @FXML
    private TableColumn<Task, String> colDescription;
    @FXML
    private TableColumn<Task, String> colTime;
    @FXML
    private TableColumn<Task, StatusEnum> colStatus;
    @FXML
    private ComboBox<String> workMode;

    private TaskDAO taskList;

    private ObservableList<Task> taskData;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        colDescription.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescription()));
        colTime.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTime()));
        colStatus.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getStatus()));
        workMode.setItems(FXCollections.observableArrayList(TaskFabrica.BD, TaskFabrica.FILE, TaskFabrica.RAM));
        workMode.getSelectionModel().selectedIndexProperty().addListener(
                (_, _, _) -> {
                    try {
                        changeWorkMode();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public void changeWorkMode() throws Exception {
        taskList = TaskFabrica.createDAO(workMode.getValue());
        taskData = FXCollections.observableArrayList(taskList.getAllTasks());
        taskTable.setItems(taskData);
    }

    @FXML
    private void handleAddProduct() {
        // Создание диалогового окна
        Dialog<Task> dialog = new Dialog<>();
        dialog.setTitle("Добавить задачу");

        // Кнопки
        ButtonType addButton = new ButtonType("Добавить задачу", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Отменить", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        // Поля ввода
        TextField nameField = new TextField();
        nameField.setPromptText("Введите название задачи");
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Введите описание задачи");
        TextField timeField = new TextField();
        timeField.setPromptText("Введите время");
        ComboBox<StatusEnum> statusComboBox = new ComboBox<>(FXCollections.observableArrayList(StatusEnum.TO_DO, StatusEnum.PROGRESS, StatusEnum.DONE, StatusEnum.CLOSED));
        statusComboBox.setPromptText("Выберите статус задачи");

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(10);
        grid.add(new Label("Название:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Описание:"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("Время:"), 0, 2);
        grid.add(timeField, 1, 2);
        grid.add(new Label("Статус:"), 0, 3);
        grid.add(statusComboBox, 1, 3);
        dialog.getDialogPane().setContent(grid);

        // Обработка результата
        dialog.setResultConverter(buttonType -> {
            try {
                if (buttonType == addButton) {
                    if (Objects.equals(nameField.getText(), ""))
                        throw new Exception("Пустая строка в названии задачи");
                    if (Objects.equals(descriptionField.getText(), ""))
                        throw new Exception("Пустая строка в описании задачи");
                    if (Objects.equals(timeField.getText(), ""))
                        throw new Exception("Пустая строка в времени задачи");
                    return new Task(
                            0,
                            nameField.getText(),
                            descriptionField.getText(),
                            timeField.getText(),
                            statusComboBox.getValue()
                    );
                }
            } catch (NullPointerException e) {
                showAlert("Ошибка ввода", "Выберите значение в поле статус");
            } catch (Exception e) {
                showAlert("Ошибка ввода", e.getMessage());
            }
            return null;
        });
        dialog.showAndWait().ifPresent(task -> {
            if (task != null) {
                taskList.addTask(task);
                taskData.setAll(taskList.getAllTasks());
            }
        });
    }

    @FXML
    private void handleChangeProduct() {
        Task selected = taskTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Ошибка изменения", "Выберите продукт для изменения!");
            return;
        }
        // Создание диалогового окна
        Dialog<Task> dialog = new Dialog<>();
        dialog.setTitle("Изменить продукт");

        // Кнопки
        ButtonType addButton = new ButtonType("Изменить", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Отменить", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        // Поля ввода
        TextField nameField = new TextField();
        nameField.setText(selected.getName());
        TextField descriptionField = new TextField();
        descriptionField.setText(selected.getDescription());
        TextField timeField = new TextField();
        timeField.setText(selected.getTime());
        ComboBox<StatusEnum> statusComboBox = new ComboBox<>(FXCollections.observableArrayList(StatusEnum.TO_DO, StatusEnum.PROGRESS, StatusEnum.DONE, StatusEnum.CLOSED));
        statusComboBox.setValue(selected.getStatus());

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(10);
        grid.add(new Label("Название:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Описание:"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("Время:"), 0, 2);
        grid.add(timeField, 1, 2);
        grid.add(new Label("Статус:"), 0, 3);
        grid.add(statusComboBox, 1, 3);
        dialog.getDialogPane().setContent(grid);

        // Обработка результата
        dialog.setResultConverter(buttonType -> {
            try {
                if (buttonType == addButton) {
                    if (Objects.equals(nameField.getText(), ""))
                        throw new Exception("Пустая строка в названии задачи");
                    if (Objects.equals(descriptionField.getText(), ""))
                        throw new Exception("Пустая строка в описании задачи");
                    if (Objects.equals(timeField.getText(), ""))
                        throw new Exception("Пустая строка в времени задачи");
                    Task change = new Task(selected.getId(),
                            nameField.getText(),
                            descriptionField.getText(),
                            timeField.getText(),
                            statusComboBox.getValue());
                    taskList.updateTask(change);
                    taskData.setAll(taskList.getAllTasks());
                    return new Task(
                            0,
                            nameField.getText(),
                            descriptionField.getText(),
                            timeField.getText(),
                            statusComboBox.getValue()
                    );
                }
            } catch (NullPointerException e) {
                showAlert("Ошибка ввода", "Выберите значение в поле статус");
            } catch (Exception e) {
                showAlert("Ошибка ввода", e.getMessage());
            }
            return null;
        });
        dialog.showAndWait();
        taskTable.refresh();
    }

    @FXML
    private void handleDeleteProduct() {
        Task selected = taskTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            taskList.deleteTask(selected.getId());
            taskData.remove(selected);
        } else {
            showAlert("Ошибка удаления", "Выберите задачу для удаления!");
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