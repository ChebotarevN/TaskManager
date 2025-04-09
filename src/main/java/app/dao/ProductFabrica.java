package app.dao;

import app.dao.impl.ListProductDAO;
import app.model.ProductList;

public class ProductFabrica {
    public static String BD = "база данных";
    public static String FILE = "файл";
    public static String RAM = "временно";

//    public static ProductDAO createDAO(String type) {
//        if (type.equalsIgnoreCase(BD)) {
//            return new DbDAO();
//        } else if (type.equalsIgnoreCase(FILE)) {
//            return new FileTaskDAO("tasks.txt");//имя файла
//        } else if (type.equalsIgnoreCase(RAM)) {
//            return new ProductList(10);//генератор на 10 задач
//        } else {
//            throw new IllegalArgumentException("Invalid datasource type!");
//        }
//    }
}
