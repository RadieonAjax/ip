package shrimp.utility;

import shrimp.task.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Storage {
    private static final String FILE_PATH = "./data/shrimp.txt";

    public static void saveTasks(TaskList tasks) {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs(); // Create the directory if it doesn't exist

        try (FileWriter writer = new FileWriter(file)) {
            for (int i = 0; i < tasks.getCount(); i++) {
                Task task = tasks.getTask(i);
                writer.write(formatTaskForSaving(task) + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TaskList loadTasks() throws IOException {
        TaskList tasks = new TaskList();
        File file = new File(FILE_PATH);

        if (file.exists()) {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                Task task = parseTask(line);
                tasks.addTask(task);
            }
        }
        return tasks;
    }

    private static String formatTaskForSaving(Task task) {
        String type = task.getType();
        switch (type) {
            case "[T]" -> {
                return String.format("T | %d | %s", task.isDone() ? 1 : 0, task.getDescription());
            }
            case "[D]" -> {
                Deadline deadline = (Deadline) task;
                return String.format("D | %d | %s | %s", task.isDone() ? 1 : 0, task.getDescription(), deadline.getBy());
            }
            case "[E]" -> {
                Event event = (Event) task;
                return String.format("E | %d | %s | %s | %s", task.isDone() ? 1 : 0, task.getDescription(), event.getFrom(), event.getTo());
            }
        }
        return "";
    }

    private static Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        String taskType = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (taskType) {
            case "T":
                return new Todo(description, isDone);
            case "D":
                String by = parts[3];
                return new Deadline(description, by, isDone);
            case "E":
                String from = parts[3];
                String to = parts[4];
                return new Event(description, from, to, isDone);
            default:
                throw new IllegalStateException("Unexpected value: " + taskType);
        }
    }
}
