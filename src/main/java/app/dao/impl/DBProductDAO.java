package app.dao.impl;

import app.dao.ProductDAO;
import app.model.Product;
import app.model.TagList;
import io.github.exortions.dotenv.DotEnv;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBProductDAO implements ProductDAO {
    private final List<Product> products;
    private DotEnv dotEnv;

    public DBProductDAO(TagList tagList) {
        products = new ArrayList<>();
        try {
            dotEnv = new DotEnv(new File(".env"));
            dotEnv.loadParams();
            Connection connection = DriverManager.getConnection(dotEnv.getParameter("JDBC_URL"), dotEnv.getParameter("USERNAME"), dotEnv.getParameter("PASSWORD"));
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM products ORDER BY ID");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getInt(1), rs.getString(2), rs.getInt(3), tagList.getTag(rs.getString(4)));
                products.add(p);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    @Override
    public void addProduct(Product product) {
        product.setId(products.getLast().getId() + 1);
        try {
            Connection connection = DriverManager.getConnection(dotEnv.getParameter("JDBC_URL"), dotEnv.getParameter("USERNAME"), dotEnv.getParameter("PASSWORD"));
            PreparedStatement pst = connection.prepareStatement("INSERT INTO public.products(\n" +
                    "\tid, name, quantity, tag, status)\n" +
                    "\tVALUES (?, ?, ?, ?, ?)");
            pst.setInt(1, product.getId());
            pst.setString(2, product.getName());
            pst.setInt(3, product.getQuantity());
            pst.setString(4, product.getTag().getName());
            pst.setString(5, product.getStatus().toString());
            pst.executeUpdate();
            products.add(product);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProduct(Product product) {
        try {
            Connection connection = DriverManager.getConnection(dotEnv.getParameter("JDBC_URL"),
                    dotEnv.getParameter("USERNAME"), dotEnv.getParameter("PASSWORD"));
            String query = String.format("UPDATE public.products SET NAME = '%s', quantity = %d, tag = '%s', " +
                            "status = '%s' WHERE ID = %d", product.getName(), product.getQuantity(),
                    product.getTag().getName(), product.getStatus().toString(), product.getId());
            PreparedStatement pst = connection.prepareStatement(query);
            pst.execute();
            connection.close();
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId() == product.getId()) {
                    products.set(i, product);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProduct(int id) {
        try {
            Connection connection = DriverManager.getConnection(dotEnv.getParameter("JDBC_URL"),
                    dotEnv.getParameter("USERNAME"), dotEnv.getParameter("PASSWORD"));
            String query = String.format("DELETE FROM PRODUCTS WHERE ID = %d", id);
            PreparedStatement pst = connection.prepareStatement(query);
            pst.execute();
            connection.close();
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId() == id) {
                    products.remove(i);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}