package app.model;

public class Product {
    private int id;
    private String name;
    private int quantity;
    private Tag tag;
    private StatusEnum status;

    public Product(int id, String name, int quantity, Tag tag) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        setQuantity(quantity);
        setStatus();
    }

    public Product(String name, int quantity, Tag tag) {
        this.name = name;
        this.tag = tag;
        setQuantity(quantity);
        setStatus();
    }

    private void setStatus() {
        if (quantity < tag.getBound1()) status = StatusEnum.LOW;
        else if (quantity < tag.getBound2()) status = StatusEnum.NORMAL;
        else status = StatusEnum.HIGH;
    }

    public StatusEnum getStatus() {
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