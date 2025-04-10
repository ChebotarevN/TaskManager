package app.dao.impl;

import app.dao.TaskDAO;
import app.model.StatusEnum;
import app.model.Task;
import io.github.exortions.dotenv.DotEnv;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBTaskDAO implements TaskDAO {
    private final List<Task> tasks;
    private DotEnv dotEnv;

    public DBTaskDAO() {
        tasks = new ArrayList<>();
        try {
            dotEnv = new DotEnv(new File(".env"));
            dotEnv.loadParams();
            Connection connection = DriverManager.getConnection(dotEnv.getParameter("JDBC_URL"), dotEnv.getParameter("USERNAME"), dotEnv.getParameter("PASSWORD"));
            Statement stmt = connection.createStatement();
            String query = "";
            query = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                    "id INTEGER PRIMARY KEY," +
                    "name VARCHAR(255)," +
                    "description VARCHAR(255)," +
                    "time VARCHAR(255)," +
                    "status VARCHAR(255))", dotEnv.getParameter("TABLE_NAME"));
            int statusExecute = stmt.executeUpdate(query);
            if (statusExecute == 0) {
                query = String.format("SELECT * FROM %s ORDER BY ID", dotEnv.getParameter("TABLE_NAME"));
                PreparedStatement pst = connection.prepareStatement(query);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    Task t = new Task(rs.getInt(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), StatusEnum.getEnum(rs.getString(5)));
                    tasks.add(t);
                }
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return tasks;
    }

    @Override
    public Task getTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    @Override
    public void addTask(Task task) {
        if (tasks.isEmpty()) {
            task.setId(1);
        } else {
            task.setId(tasks.getLast().getId() + 1);
        }
        try {
            Connection connection = DriverManager.getConnection(dotEnv.getParameter("JDBC_URL"),
                    dotEnv.getParameter("USERNAME"), dotEnv.getParameter("PASSWORD"));
            String query = String.format("INSERT INTO %s(" +
                    "id, name, description, time, status)" +
                    "VALUES (?, ?, ?, ?, ?)", dotEnv.getParameter("TABLE_NAME"));
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, task.getId());
            pst.setString(2, task.getName());
            pst.setString(3, task.getDescription());
            pst.setString(4, task.getTime());
            pst.setString(5, task.getStatus().toString());
            pst.executeUpdate();
            tasks.add(task);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTask(Task task) {
        try {
            Connection connection = DriverManager.getConnection(dotEnv.getParameter("JDBC_URL"),
                    dotEnv.getParameter("USERNAME"), dotEnv.getParameter("PASSWORD"));
            String query = String.format("UPDATE %s SET NAME = '%s', description = '%s', time = '%s', " +
                            "status = '%s' WHERE ID = %d", dotEnv.getParameter("TABLE_NAME"), task.getName(), task.getDescription(),
                    task.getTime(), task.getStatus().toString(), task.getId());
            PreparedStatement pst = connection.prepareStatement(query);
            pst.execute();
            connection.close();
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getId() == task.getId()) {
                    tasks.set(i, task);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTask(int id) {
        try {
            Connection connection = DriverManager.getConnection(dotEnv.getParameter("JDBC_URL"),
                    dotEnv.getParameter("USERNAME"), dotEnv.getParameter("PASSWORD"));
            String query = String.format("DELETE FROM %s WHERE ID = %d", dotEnv.getParameter("TABLE_NAME"), id);
            PreparedStatement pst = connection.prepareStatement(query);
            pst.execute();
            connection.close();
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getId() == id) {
                    tasks.remove(i);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}