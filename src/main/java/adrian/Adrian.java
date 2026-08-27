package adrian;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Adrian {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public static void main(String[] args) {
        Ui ui = new Ui();
        ArrayList<Task> tasks;

        try {
            tasks = Storage.loadTasks();
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new ArrayList<>();
        }

        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();

            try {
                if (input.equals("bye")) {
                    ui.showGoodbye();
                    break;
                }

                ui.showLine();

                if (input.equals("list")) {
                    ui.showTaskList(tasks);
                } else if (input.equals("todo")) {
                    throw new AdrianException("The description of a todo cannot be empty.");
                } else if (input.startsWith("todo ")) {
                    String description = input.substring(5).trim();

                    if (description.isEmpty()) {
                        throw new AdrianException("The description of a todo cannot be empty.");
                    }

                    Task task = new Todo(description);
                    tasks.add(task);
                    Storage.saveTasks(tasks);

                    ui.showTaskAdded(task, tasks.size());
                } else if (input.equals("deadline")) {
                    throw new AdrianException("A deadline needs a description and /by time.");
                } else if (input.startsWith("deadline ")) {
                    String details = input.substring(9);
                    int byIndex = details.indexOf(" /by ");

                    if (byIndex == -1) {
                        throw new AdrianException("Please specify the deadline using /by.");
                    }

                    String description = details.substring(0, byIndex).trim();
                    String by = details.substring(byIndex + 5).trim();

                    if (description.isEmpty()) {
                        throw new AdrianException("The description of a deadline cannot be empty.");
                    }

                    if (by.isEmpty()) {
                        throw new AdrianException("The deadline time cannot be empty.");
                    }

                    LocalDateTime dateTime;

                    try {
                        dateTime = LocalDateTime.parse(by, INPUT_FORMAT);
                    } catch (DateTimeParseException e) {
                        throw new AdrianException("Please use date format yyyy-MM-dd HHmm.");
                    }

                    Task task = new Deadline(description, dateTime);
                    tasks.add(task);
                    Storage.saveTasks(tasks);

                    ui.showTaskAdded(task, tasks.size());
                } else if (input.equals("event")) {
                    throw new AdrianException("An event needs a description, /from time, and /to time.");
                } else if (input.startsWith("event ")) {
                    String details = input.substring(6);

                    int fromIndex = details.indexOf(" /from ");
                    int toIndex = details.indexOf(" /to ");

                    if (fromIndex == -1 || toIndex == -1) {
                        throw new AdrianException("Please specify an event using /from and /to.");
                    }

                    String description = details.substring(0, fromIndex).trim();
                    String from = details.substring(fromIndex + 7, toIndex).trim();
                    String to = details.substring(toIndex + 5).trim();

                    if (description.isEmpty()) {
                        throw new AdrianException("The description of an event cannot be empty.");
                    }

                    if (from.isEmpty() || to.isEmpty()) {
                        throw new AdrianException("The event start and end times cannot be empty.");
                    }

                    LocalDateTime fromDateTime;
                    LocalDateTime toDateTime;

                    try {
                        fromDateTime = LocalDateTime.parse(from, INPUT_FORMAT);
                        toDateTime = LocalDateTime.parse(to, INPUT_FORMAT);
                    } catch (DateTimeParseException e) {
                        throw new AdrianException("Please use date format yyyy-MM-dd HHmm.");
                    }

                    Task task = new Event(description, fromDateTime, toDateTime);
                    tasks.add(task);
                    Storage.saveTasks(tasks);

                    ui.showTaskAdded(task, tasks.size());
                } else if (input.equals("mark")) {
                    throw new AdrianException("Please specify which task to mark.");
                } else if (input.startsWith("mark ")) {
                    int taskNumber = getTaskNumber(input, "mark", tasks.size());

                    Task task = tasks.get(taskNumber - 1);
                    task.markAsDone();
                    Storage.saveTasks(tasks);

                    ui.showTaskMarked(task);
                } else if (input.equals("unmark")) {
                    throw new AdrianException("Please specify which task to unmark.");
                } else if (input.startsWith("unmark ")) {
                    int taskNumber = getTaskNumber(input, "unmark", tasks.size());

                    Task task = tasks.get(taskNumber - 1);
                    task.markAsNotDone();
                    Storage.saveTasks(tasks);

                    ui.showTaskUnmarked(task);
                } else if (input.equals("delete")) {
                    throw new AdrianException("Please specify which task to delete.");
                } else if (input.startsWith("delete ")) {
                    int taskNumber = getTaskNumber(input, "delete", tasks.size());

                    Task removedTask = tasks.remove(taskNumber - 1);
                    Storage.saveTasks(tasks);

                    ui.showTaskDeleted(removedTask, tasks.size());
                } else {
                    throw new AdrianException("Sorry, I don't understand that command.");
                }
            } catch (AdrianException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showSavingError();
            }

            ui.showLine();
        }

        ui.close();
    }

    private static int getTaskNumber(String input, String command, int numberOfTasks)
            throws AdrianException {
        String numberText = input.substring(command.length()).trim();

        try {
            int taskNumber = Integer.parseInt(numberText);

            if (taskNumber < 1 || taskNumber > numberOfTasks) {
                throw new AdrianException("That task number does not exist.");
            }

            return taskNumber;
        } catch (NumberFormatException e) {
            throw new AdrianException("Please enter a valid task number.");
        }
    }
}
