package app.model;

public enum StatusEnum {
    LOW("Недостаток"),
    NORMAL("Достаточно"),
    HIGH("Избыток");

    private String text;
    StatusEnum(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
