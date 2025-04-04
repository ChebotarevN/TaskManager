package app.model;

public class Product {
    private int id;
    private String name;
    private int quantity;
    private Tag tag;
    private Status status;

    public Product(int id, String name, int quantity, Tag tag) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        setQuantity(quantity);
        setStatus();
    }

    private void setStatus() {
        if (quantity < tag.getBound1()) status = Status.LOW;
        else if (quantity < tag.getBound2()) status = Status.NORMAL;
        else status = Status.HIGH;
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
        if (quantity < 0) {
            this.quantity = 0;
        } else {
            this.quantity = quantity;
        }
        setStatus();
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}