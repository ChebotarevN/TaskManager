package app.dao;

import app.dao.impl.DBTaskDAO;
import app.dao.impl.FileTaskDAO;
import app.dao.impl.TaskListDAO;
import app.tasklist.MainController;

import java.io.FileNotFoundException;

public class TaskFabrica {
    public static String BD = "База данных";
    public static String FILE = "Файл";
    public static String RAM = "Временно";

    public static TaskDAO createDAO(String type) throws Exception {
        if (type.equalsIgnoreCase(BD)) {
            return new DBTaskDAO();
        } else if (type.equalsIgnoreCase(FILE)) {
            return new FileTaskDAO(MainController.selectFile());//имя файла
        } else if (type.equalsIgnoreCase(RAM)) {
            return new TaskListDAO(10);//генератор на 10 задач
        } else {
            throw new IllegalArgumentException("Invalid datasource type!");
        }
    }
}
