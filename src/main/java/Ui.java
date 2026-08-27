import java.util.List;
import java.util.Scanner;

public class Ui {
    private static final String BANNER = "    _       _      _             \n"
            + "   / \\   __| |_ __(_) __ _ _ __  \n"
            + "  / _ \\ / _` | '__| |/ _` | '_ \\ \n"
            + " / ___ \\ (_| | |  | | (_| | | | |\n"
            + "/_/   \\_\\__,_|_|  |_|\\__,_|_| |_|\n";
    private static final String LINE = "____________________________________________________________";

    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        showLine();
        System.out.println(BANNER);
        System.out.println("Hello! I'm Adrian.");
        System.out.println("What can I do for you?");
        showLine();
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showGoodbye() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showTaskList(List<Task> tasks) {
        System.out.println("Here are the tasks in your list:");

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showError(String message) {
        System.out.println("OOPS!!! " + message);
    }

    public void showLoadingError() {
        System.out.println("OOPS!!! Could not load saved tasks.");
    }

    public void showSavingError() {
        System.out.println("OOPS!!! Could not save tasks.");
    }

    public void close() {
        scanner.close();
    }
}
