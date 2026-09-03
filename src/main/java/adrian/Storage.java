package adrian;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves tasks to disk and reconstructs them when the application starts.
 */
public class Storage {
    private static final Path FILE_PATH = Path.of("data", "adrian.txt");

    /**
     * Creates a storage service for Adrian's task data.
     */
    public Storage() {
    }

    /**
     * Writes all tasks to Adrian's data file, replacing its existing contents.
     *
     * @param tasks tasks to save.
     * @throws IOException if the data directory or file cannot be written.
     */
    public static void saveTasks(List<Task> tasks) throws IOException {
        Files.createDirectories(FILE_PATH.getParent());

        List<String> lines = new ArrayList<>();

        for (Task task : tasks) {
            lines.add(task.toDataString());
        }

        Files.write(FILE_PATH, lines);
    }

    /**
     * Loads all tasks from Adrian's data file.
     *
     * @return tasks reconstructed from storage, or an empty list if no data file exists.
     * @throws IOException if the data file cannot be read.
     */
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

    /**
     * Reconstructs a task from one line of stored task data.
     *
     * @param line serialized task data.
     * @return task represented by the stored data.
     */
    private static Task parseTask(String line) {
        String[] parts = line.split(" \\| ");

        String taskTypeSymbol = parts[0];
        boolean isDone = parts[1].equals("1");
        Task task;

        if (taskTypeSymbol.equals("T")) {
            task = new Todo(parts[2]);
        } else if (taskTypeSymbol.equals("D")) {
            LocalDateTime dueDateTime = LocalDateTime.parse(parts[3]);
            task = new Deadline(parts[2], dueDateTime);
        } else {
            LocalDateTime startDateTime = LocalDateTime.parse(parts[3]);
            LocalDateTime endDateTime = LocalDateTime.parse(parts[4]);
            task = new Event(parts[2], startDateTime, endDateTime);
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }
}
