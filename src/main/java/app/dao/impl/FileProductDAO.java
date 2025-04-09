package app.dao.impl;

import app.dao.ProductDAO;
import app.model.Product;
import app.model.Tag;
import app.model.TagList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileProductDAO implements ProductDAO {
    private final List<Product> products;
    private File file;

    public FileProductDAO(File file, TagList tagList) throws FileNotFoundException {
        this.file = file;
        products = new ArrayList<>();
        Scanner in = new Scanner(file);
        while (in.hasNext()) {
            String[] line = in.nextLine().split(",");
            int id = Integer.parseInt(line[0]);
            String name = line[1];
            int q = Integer.parseInt(line[2]);
            Tag tag = tagList.getTag(line[3]);
            Product p = new Product(id, name, q, tag);
            products.add(p);
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public Product getProductById(int id) {
        return products.get(id);
    }

    @Override
    public void addProduct(Product product) {
        product.setId(products.getLast().getId() + 1);
        products.add(product);
        writeFile();
    }

    @Override
    public void updateProduct(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                products.set(i, product);
                break;
            }
        }
        writeFile();
    }

    @Override
    public void deleteProduct(int id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.remove(i);
                break;
            }
        }
        writeFile();
    }

    private void writeFile() {
        try {
            FileWriter fileWriter = new FileWriter(file);
            for (Product p : products) {
                fileWriter.append(p.getId() + "," + p.getName() + "," + p.getQuantity() + "," + p.getTag() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
