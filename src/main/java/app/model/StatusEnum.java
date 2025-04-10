package app.model;

public enum StatusEnum {
    TO_DO("К исполнению"),
    PROGRESS("В процессе"),
    DONE("Выполнена"),
    CLOSED("Закрыта");

    private String text;

    StatusEnum(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static StatusEnum getEnum(String name) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.text.equalsIgnoreCase(name)) {
                return status;
            }
        }
        return null;
    }
}
