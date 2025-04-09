package app.dao;

import app.dao.impl.FileProductDAO;
import app.dao.impl.ListProductDAO;
import app.listproduct.MainController;
import app.model.ProductList;
import app.model.TagList;

import java.io.FileNotFoundException;

public class ProductFabrica {
    public static String BD = "База данных";
    public static String FILE = "Файл";
    public static String RAM = "Временно";

    public static ProductDAO createDAO(String type, TagList tagList) throws FileNotFoundException {
        if (type.equalsIgnoreCase(BD)) {
//            return new DbDAO();
        } else if (type.equalsIgnoreCase(FILE)) {
            return new FileProductDAO(MainController.selectFile(), tagList);//имя файла
        } else if (type.equalsIgnoreCase(RAM)) {
            return new ListProductDAO(10, tagList);//генератор на 10 задач
        } else {
            throw new IllegalArgumentException("Invalid datasource type!");
        }
        return null;
    }
}
