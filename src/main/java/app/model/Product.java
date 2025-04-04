package app.model;

public class Product {
    private int id;
    private String name;
    private int quantity;
    private String tag;
    private Status status;

    public Product(int id, String name, int quantity, String tag) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.tag = tag;
    }

    private void setStatus() {
        
    }

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}