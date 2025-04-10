//package app.model;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TaskList {
//    private List<Task> tasks = new ArrayList<>();
//
//    public TaskList() {
//    }
//
//    public TaskList(List<Task> list) {
//        tasks.addAll(list);
//        setAllId();
//    }
//
//    // Добавить продукт
//    public void addTask(Task task) {
//        tasks.add(task);
//        setAllId();
//    }
//
//    // Удалить продукт по ID
//    public void removeTask(int id) {
//        tasks.removeIf(p -> p.getId() == id);
//        setAllId();
//    }
//
//    // Обновить продукт
//    public void updateTask(Task updatedTask) {
//        for (int i = 0; i < tasks.size(); i++) {
//            if (tasks.get(i).getId() == updatedTask.getId()) {
//                tasks.set(i, updatedTask);
//                break;
//            }
//        }
//        setAllId();
//    }
//
//    // Получить все продукты
//    public List<Task> getTasks() {
//        return new ArrayList<>(tasks);
//    }
//
//    // Найти продукт по ID
//    public Task getTaskById(int id) {
//        return tasks.stream()
//                .filter(p -> p.getId() == id)
//                .findFirst()
//                .orElse(null);
//    }
//
//    private void setAllId() {
//        int i = 1;
//        for (Task p : tasks) {
//            p.setId(i);
//            i++;
//        }
//    }
//}