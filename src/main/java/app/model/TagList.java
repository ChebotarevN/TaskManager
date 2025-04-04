package app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TagList {
    private List<Tag> tags = new ArrayList<>();

    public void addTag(Tag tag){
        if (tags.stream().noneMatch(t -> Objects.equals(t.getName(), tag.getName()))){
            tags.add(tag);
        }
    }

    public List<Tag> getAllTags() {
        return tags;
    }

    public Tag getTag(String name) {
        return tags.stream()
                .filter(t -> Objects.equals(t.getName(), name))
                .findFirst()
                .orElse(null);
    }
}
