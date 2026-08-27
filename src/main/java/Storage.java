import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
            task = new Deadline(parts[2], parts[3]);
        } else {
            task = new Event(parts[2], parts[3], parts[4]);
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }
}
