package app.dao.impl;

import app.dao.TaskDAO;
import app.model.StatusEnum;
import app.model.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileTaskDAO implements TaskDAO {
    private final List<Task> tasks;
    private File file;

    public FileTaskDAO(File file) throws Exception {
        this.file = file;
        tasks = new ArrayList<>();
        Scanner in = new Scanner(file);
        while (in.hasNext()) {
            String[] line = in.nextLine().split(",");
            int id = Integer.parseInt(line[0]);
            String name = line[1];
            String description = line[2];
            String time = line[3];
            StatusEnum status = StatusEnum.getEnum(line[4]);
            Task p = new Task(id, name, description, time, status);
            tasks.add(p);
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return tasks;
    }

    @Override
    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    @Override
    public void addTask(Task task) {
        if (tasks.isEmpty()) {
            task.setId(1);
        } else {
            task.setId(tasks.getLast().getId() + 1);
        }
        tasks.add(task);
        writeFile();
    }

    @Override
    public void updateTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                tasks.set(i, task);
                break;
            }
        }
        writeFile();
    }

    @Override
    public void deleteTask(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.remove(i);
                break;
            }
        }
        writeFile();
    }

    private void writeFile() {
        try {
            FileWriter fileWriter = new FileWriter(file);
            for (Task t : tasks) {
                fileWriter.append(t.getId() + "," + t.getName() + "," + t.getDescription() + ","
                        + t.getTime() + "," + t.getStatus() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
