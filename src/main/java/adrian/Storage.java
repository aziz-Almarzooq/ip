package adrian;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Storage {
    private static final Path FILE_PATH = Path.of("data", "adrian.txt");

    public static void saveTasks(List<Task> tasks) throws IOException {
        Files.createDirectories(FILE_PATH.getParent());

        List<String> lines = new ArrayList<>();

        for (Task task : tasks) {
            lines.add(task.toDataString());
        }

        Files.write(FILE_PATH, lines);
    }

    public static ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();

        if (!Files.exists(FILE_PATH)) {
            return tasks;
        }

        List<String> lines = Files.readAllLines(FILE_PATH);

        for (String line : lines) {
            tasks.add(parseTask(line));
        }

        return tasks;
    }

    private static Task parseTask(String line) {
        String[] parts = line.split(" \\| ");

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        Task task;

        if (type.equals("T")) {
            task = new Todo(parts[2]);

        } else if (type.equals("D")) {
            LocalDateTime by = LocalDateTime.parse(parts[3]);
            task = new Deadline(parts[2], by);

        } else {
            LocalDateTime from = LocalDateTime.parse(parts[3]);
            LocalDateTime to = LocalDateTime.parse(parts[4]);
            task = new Event(parts[2], from, to);
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }
}
