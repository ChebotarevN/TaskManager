package app.model;

public class Tag {
    private String tag;
    private int bound1, bound2;

    public Tag(String tag, int bound1, int bound2) {

    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
