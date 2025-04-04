package app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TagList {
    private List<Tag> tags = new ArrayList<>();

    public void addTag(Tag tag){
        if (tags.stream().noneMatch(t -> Objects.equals(t.getTag(), tag.getTag()))){
            tags.add(tag);
        }
    }
}
