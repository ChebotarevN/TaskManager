package app.dao.impl;

import app.dao.ProductDAO;
import app.model.Product;
import app.model.TagList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListProductDAO implements ProductDAO {

    private final List<Product> products;

    public ListProductDAO(int size, TagList tagList) {
        products = new ArrayList<>();
        Random random = new Random();
        int id = 1;
        if (!products.isEmpty()) id = products.getLast().getId() + 1;
        for (int i = id; i < size + id; i++) {
            int randomTag = random.nextInt(0, tagList.getSize());
            int randomItem = random.nextInt(0, tagList.getItems(randomTag).size());
            Product product = new Product(
                    i,
                    tagList.getTag(randomTag).getItems().get(randomItem),
                    random.nextInt(100),
                    tagList.getTag(randomTag)
            );
            products.add(product);
        }

    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public void addProduct(Product product) {
        product.setId(products.getLast().getId() + 1);
        products.add(product);
    }

    public void updateProduct(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                products.set(i, product);
                return;
            }
        }
    }

    public void deleteProduct(int id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.remove(i);
                return;
            }
        }
    }

}