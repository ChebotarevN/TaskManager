package app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TagList {
    private List<Tag> tags = new ArrayList<>();

    public List<Tag> getAllTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        if (tags.stream().noneMatch(t -> Objects.equals(t, tag))) {
            tags.add(tag);
        }
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    public Tag getTag(String name) {
        return tags.stream()
                .filter(t -> Objects.equals(t.getName(), name))
                .findFirst()
                .orElse(null);
    }

    public Tag getTag(int id) {
        return tags.get(id);
    }

    public int getSize() {
        return tags.size();
    }

    public List<String> getItems(int id) {
        return tags.get(id).getItems();
    }
}
