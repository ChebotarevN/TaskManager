package app.model;

public class Task {

    private int id;
    private String name;
    private String description;
    private String time;
    private int hours, minutes;
    private StatusEnum status;

    public Task(int id, String name, String description, String time, StatusEnum status) throws Exception {
        this.id = id;
        this.name = name;
        this.description = description;
        setTime(time);
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) throws Exception {
        if (!time.contains(":"))
            throw new Exception("Неправильный формат минут.");
        int hours = Integer.parseInt(time.split(":")[0]);
        int minutes = Integer.parseInt(time.split(":")[1]);
        if ((hours < 0 || hours >= 24) || minutes < 0 || minutes >= 60)
            throw new Exception("Неправильный формат минут.");
        this.hours = Integer.parseInt(time.split(":")[0]);
        this.minutes = Integer.parseInt(time.split(":")[1]);
        if (minutes < 10) {
            this.time = hours + ":0" + minutes;
        } else {
            this.time = hours + ":" + minutes;
        }
    }

    public String getTime() {
        return time;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}