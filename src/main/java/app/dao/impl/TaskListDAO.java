package app.dao.impl;

import app.dao.TaskDAO;
import app.model.StatusEnum;
import app.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaskListDAO implements TaskDAO {

    private final List<Task> tasks;

    public TaskListDAO(int size) throws Exception {
        tasks = new ArrayList<>();
        Random random = new Random();
        List<StatusEnum> randomStatus = List.of(StatusEnum.TO_DO, StatusEnum.PROGRESS, StatusEnum.DONE, StatusEnum.CLOSED);
        int id = 1;
        if (!tasks.isEmpty()) id = tasks.getLast().getId() + 1;
        for (int i = id; i < size + id; i++) {
            Task task = new Task(
                    i,
                    "Task " + i,
                    "Context for task " + i,
                    randomTime(random),
                    randomStatus.get(random.nextInt(4))
            );
            tasks.add(task);
        }

    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task getTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public void addTask(Task task) {
        if (tasks.isEmpty()) {
            task.setId(1);
        } else {
            task.setId(tasks.getLast().getId() + 1);
        }
        tasks.add(task);
    }

    public void updateTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                tasks.set(i, task);
                return;
            }
        }
    }

    public void deleteTask(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.remove(i);
                return;
            }
        }
    }

    private String randomTime(Random random) {
        int hours = random.nextInt(0, 24);
        int minutes = random.nextInt(0, 59);
        if (minutes < 10) {
            return hours + ":0" + minutes;
        } else {
            return hours + ":" + minutes;
        }
    }

}