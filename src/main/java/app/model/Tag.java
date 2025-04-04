package app.model;

public class Tag {
    private String name;
    private int bound1, bound2;

    public Tag(String name, int bound1, int bound2) {
        this.name = name;
        this.bound1 = bound1;
        this.bound2 = bound2;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String tag) {
        this.name = name;
    }

    public int getBound1() {
        return bound1;
    }

    public void setBound1(int bound1) {
        this.bound1 = bound1;
    }

    public int getBound2() {
        return bound2;
    }

    public void setBound2(int bound2) {
        this.bound2 = bound2;
    }
}
