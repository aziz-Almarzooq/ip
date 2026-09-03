package adrian;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Runs the Adrian task manager and processes commands entered by the user.
 */
public class Adrian {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Creates an Adrian application entry point.
     */
    public Adrian() {
    }

    /**
     * Starts the application and processes commands until the user exits.
     *
     * @param args command-line arguments; not used by Adrian.
     */
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

                ui.showDivider();

                if (input.equals("list")) {
                    ui.showTaskList(tasks);
                } else if (input.equals("todo")) {
                    throw new InvalidInputException("The description of a todo cannot be empty.");
                } else if (input.startsWith("todo ")) {
                    String description = input.substring(5).trim();

                    if (description.isEmpty()) {
                        throw new InvalidInputException("The description of a todo cannot be empty.");
                    }

                    Task task = new Todo(description);
                    tasks.add(task);
                    Storage.saveTasks(tasks);

                    ui.showTaskAdded(task, tasks.size());
                } else if (input.equals("deadline")) {
                    throw new InvalidInputException("A deadline needs a description and /by time.");
                } else if (input.startsWith("deadline ")) {
                    String details = input.substring(9);
                    int byIndex = details.indexOf(" /by ");

                    if (byIndex == -1) {
                        throw new InvalidInputException("Please specify the deadline using /by.");
                    }

                    String description = details.substring(0, byIndex).trim();
                    String dueDateTimeText = details.substring(byIndex + 5).trim();

                    if (description.isEmpty()) {
                        throw new InvalidInputException("The description of a deadline cannot be empty.");
                    }

                    if (dueDateTimeText.isEmpty()) {
                        throw new InvalidInputException("The deadline time cannot be empty.");
                    }

                    LocalDateTime dueDateTime;

                    try {
                        dueDateTime = LocalDateTime.parse(dueDateTimeText, INPUT_FORMAT);
                    } catch (DateTimeParseException e) {
                        throw new InvalidInputException("Please use date format yyyy-MM-dd HHmm.");
                    }

                    Task task = new Deadline(description, dueDateTime);
                    tasks.add(task);
                    Storage.saveTasks(tasks);

                    ui.showTaskAdded(task, tasks.size());
                } else if (input.equals("event")) {
                    throw new InvalidInputException("An event needs a description, /from time, and /to time.");
                } else if (input.startsWith("event ")) {
                    String details = input.substring(6);

                    int fromIndex = details.indexOf(" /from ");
                    int toIndex = details.indexOf(" /to ");

                    if (fromIndex == -1 || toIndex == -1) {
                        throw new InvalidInputException("Please specify an event using /from and /to.");
                    }

                    String description = details.substring(0, fromIndex).trim();
                    String startDateTimeText = details.substring(fromIndex + 7, toIndex).trim();
                    String endDateTimeText = details.substring(toIndex + 5).trim();

                    if (description.isEmpty()) {
                        throw new InvalidInputException("The description of an event cannot be empty.");
                    }

                    if (startDateTimeText.isEmpty() || endDateTimeText.isEmpty()) {
                        throw new InvalidInputException("The event start and end times cannot be empty.");
                    }

                    LocalDateTime startDateTime;
                    LocalDateTime endDateTime;

                    try {
                        startDateTime = LocalDateTime.parse(startDateTimeText, INPUT_FORMAT);
                        endDateTime = LocalDateTime.parse(endDateTimeText, INPUT_FORMAT);
                    } catch (DateTimeParseException e) {
                        throw new InvalidInputException("Please use date format yyyy-MM-dd HHmm.");
                    }

                    Task task = new Event(description, startDateTime, endDateTime);
                    tasks.add(task);
                    Storage.saveTasks(tasks);

                    ui.showTaskAdded(task, tasks.size());
                } else if (input.equals("mark")) {
                    throw new InvalidInputException("Please specify which task to mark.");
                } else if (input.startsWith("mark ")) {
                    int taskNumber = getTaskNumber(input, "mark", tasks.size());

                    Task task = tasks.get(taskNumber - 1);
                    task.markAsDone();
                    Storage.saveTasks(tasks);

                    ui.showTaskMarked(task);
                } else if (input.equals("unmark")) {
                    throw new InvalidInputException("Please specify which task to unmark.");
                } else if (input.startsWith("unmark ")) {
                    int taskNumber = getTaskNumber(input, "unmark", tasks.size());

                    Task task = tasks.get(taskNumber - 1);
                    task.markAsNotDone();
                    Storage.saveTasks(tasks);

                    ui.showTaskUnmarked(task);
                } else if (input.equals("delete")) {
                    throw new InvalidInputException("Please specify which task to delete.");
                } else if (input.startsWith("delete ")) {
                    int taskNumber = getTaskNumber(input, "delete", tasks.size());

                    Task removedTask = tasks.remove(taskNumber - 1);
                    Storage.saveTasks(tasks);

                    ui.showTaskDeleted(removedTask, tasks.size());
                } else {
                    throw new InvalidInputException("Sorry, I don't understand that command.");
                }
            } catch (InvalidInputException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showSavingError();
            }

            ui.showDivider();
        }

        ui.close();
    }

    /**
     * Extracts and validates a task number from a command.
     *
     * @param input full command entered by the user.
     * @param command command word that precedes the task number.
     * @param numberOfTasks number of tasks currently available.
     * @return the validated one-based task number.
     * @throws InvalidInputException if the number is invalid or does not identify an existing task.
     */
    private static int getTaskNumber(String input, String command, int numberOfTasks)
            throws InvalidInputException {
        String numberText = input.substring(command.length()).trim();

        try {
            int taskNumber = Integer.parseInt(numberText);

            if (taskNumber < 1 || taskNumber > numberOfTasks) {
                throw new InvalidInputException("That task number does not exist.");
            }

            return taskNumber;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Please enter a valid task number.");
        }
    }
}
