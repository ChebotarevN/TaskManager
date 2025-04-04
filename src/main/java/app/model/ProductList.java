package app.model;

import java.util.ArrayList;
import java.util.List;

public class ProductList {
    private List<Product> products = new ArrayList<>();

    public ProductList() {
    }

    public ProductList(List<Product> list) {
        products.addAll(list);
        setAllId();
    }

    // Добавить продукт
    public void addProduct(Product product) {
        products.add(product);
        setAllId();
    }

    // Удалить продукт по ID
    public void removeProduct(int id) {
        products.removeIf(p -> p.getId() == id);
        setAllId();
    }

    // Обновить продукт
    public void updateProduct(Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == updatedProduct.getId()) {
                products.set(i, updatedProduct);
                break;
            }
        }
        setAllId();
    }

    // Получить все продукты
    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    // Найти продукт по ID
    public Product getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private void setAllId() {
        int i = 1;
        for (Product p : products) {
            p.setId(i);
            i++;
        }
    }
}