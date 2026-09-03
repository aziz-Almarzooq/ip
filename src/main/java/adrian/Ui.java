package adrian;

import java.util.List;
import java.util.Scanner;

/**
 * Handles console input and output for the Adrian application.
 */
public class Ui {
    private static final String BANNER = "    _       _      _             \n"
            + "   / \\   __| |_ __(_) __ _ _ __  \n"
            + "  / _ \\ / _` | '__| |/ _` | '_ \\ \n"
            + " / ___ \\ (_| | |  | | (_| | | | |\n"
            + "/_/   \\_\\__,_|_|  |_|\\__,_|_| |_|\n";
    private static final String DIVIDER = "____________________________________________________________";

    private final Scanner scanner;

    /**
     * Creates a user interface that reads commands from standard input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the application banner and welcome message.
     */
    public void showWelcome() {
        showDivider();
        System.out.println(BANNER);
        System.out.println("Hello! I'm Adrian.");
        System.out.println("What can I do for you?");
        showDivider();
    }

    /**
     * Reads the next command entered by the user.
     *
     * @return command entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the farewell message.
     */
    public void showGoodbye() {
        showDivider();
        System.out.println("Bye. Hope to see you again soon!");
        showDivider();
    }

    /**
     * Displays a horizontal separator line.
     */
    public void showDivider() {
        System.out.println(DIVIDER);
    }

    /**
     * Displays all tasks with their one-based list numbers.
     *
     * @param tasks tasks to display.
     */
    public void showTaskList(List<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        showNumberedTasks(tasks);
    }

    /**
     * Displays tasks whose descriptions match a search keyword.
     *
     * @param matchingTasks matching tasks to display.
     */
    public void showMatchingTasks(List<Task> matchingTasks) {
        System.out.println("Here are the matching tasks in your list:");
        showNumberedTasks(matchingTasks);
    }

    /**
     * Displays tasks with one-based list numbers.
     *
     * @param tasks tasks to display.
     */
    private void showNumberedTasks(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Displays confirmation that a task was added.
     *
     * @param task task that was added.
     * @param taskCount total number of tasks after the addition.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays confirmation that a task was marked as completed.
     *
     * @param task task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    /**
     * Displays confirmation that a task was marked as incomplete.
     *
     * @param task task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    /**
     * Displays confirmation that a task was deleted.
     *
     * @param task task that was deleted.
     * @param taskCount total number of tasks after the deletion.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays an error caused by invalid user input.
     *
     * @param message explanation of the error.
     */
    public void showError(String message) {
        System.out.println("OOPS!!! " + message);
    }

    /**
     * Displays an error indicating that saved tasks could not be loaded.
     */
    public void showLoadingError() {
        System.out.println("OOPS!!! Could not load saved tasks.");
    }

    /**
     * Displays an error indicating that tasks could not be saved.
     */
    public void showSavingError() {
        System.out.println("OOPS!!! Could not save tasks.");
    }

    /**
     * Releases the input scanner used by this user interface.
     */
    public void close() {
        scanner.close();
    }
}
