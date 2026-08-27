import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;

public class Adrian {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks;

        try {
            tasks = Storage.loadTasks();
        } catch (IOException e) {
            System.out.println("OOPS!!! Could not load saved tasks.");
            tasks = new ArrayList<>();
        }

        String banner = "    _       _      _             \n"
                + "   / \\   __| |_ __(_) __ _ _ __  \n"
                + "  / _ \\ / _` | '__| |/ _` | '_ \\ \n"
                + " / ___ \\ (_| | |  | | (_| | | | |\n"
                + "/_/   \\_\\__,_|_|  |_|\\__,_|_| |_|\n";

        String line = "____________________________________________________________";

        System.out.println(line);
        System.out.println(banner);
        System.out.println("Hello! I'm Adrian.");
        System.out.println("What can I do for you?");
        System.out.println(line);



        while (true) {
            String input = scanner.nextLine();

            try {
                if (input.equals("bye")) {
                    System.out.println(line);
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println(line);
                    break;
                }

                System.out.println(line);

                if (input.equals("list")) {
                    System.out.println("Here are the tasks in your list:");

                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }

                } else if (input.equals("todo")) {
                    throw new AdrianException(
                            "The description of a todo cannot be empty.");

                } else if (input.startsWith("todo ")) {
                    String description = input.substring(5).trim();

                    if (description.isEmpty()) {
                        throw new AdrianException(
                                "The description of a todo cannot be empty.");
                    }

                    Task task = new Todo(description);
                    tasks.add(task);
                    Storage.saveTasks(tasks);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + tasks.size()
                            + " tasks in the list.");

                } else if (input.equals("deadline")) {
                    throw new AdrianException(
                            "A deadline needs a description and /by time.");

                } else if (input.startsWith("deadline ")) {
                    String details = input.substring(9);
                    int byIndex = details.indexOf(" /by ");

                    if (byIndex == -1) {
                        throw new AdrianException(
                                "Please specify the deadline using /by.");
                    }

                    String description =
                            details.substring(0, byIndex).trim();
                    String by =
                            details.substring(byIndex + 5).trim();

                    if (description.isEmpty()) {
                        throw new AdrianException(
                                "The description of a deadline cannot be empty.");
                    }

                    if (by.isEmpty()) {
                        throw new AdrianException(
                                "The deadline time cannot be empty.");
                    }

                    Task task = new Deadline(description, by);
                    tasks.add(task);
                    Storage.saveTasks(tasks);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + tasks.size()
                            + " tasks in the list.");

                } else if (input.equals("event")) {
                    throw new AdrianException(
                            "An event needs a description, /from time, and /to time.");

                } else if (input.startsWith("event ")) {
                    String details = input.substring(6);

                    int fromIndex = details.indexOf(" /from ");
                    int toIndex = details.indexOf(" /to ");

                    if (fromIndex == -1 || toIndex == -1) {
                        throw new AdrianException(
                                "Please specify an event using /from and /to.");
                    }

                    String description =
                            details.substring(0, fromIndex).trim();
                    String from =
                            details.substring(fromIndex + 7, toIndex).trim();
                    String to =
                            details.substring(toIndex + 5).trim();

                    if (description.isEmpty()) {
                        throw new AdrianException(
                                "The description of an event cannot be empty.");
                    }

                    if (from.isEmpty() || to.isEmpty()) {
                        throw new AdrianException(
                                "The event start and end times cannot be empty.");
                    }

                    Task task = new Event(description, from, to);
                    tasks.add(task);
                    Storage.saveTasks(tasks);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + tasks.size()
                            + " tasks in the list.");

                } else if (input.equals("mark")) {
                    throw new AdrianException(
                            "Please specify which task to mark.");

                } else if (input.startsWith("mark ")) {
                    int taskNumber =
                            getTaskNumber(input, "mark", tasks.size());

                    Task task = tasks.get(taskNumber - 1);
                    task.markAsDone();
                    Storage.saveTasks(tasks);

                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + task);

                } else if (input.equals("unmark")) {
                    throw new AdrianException(
                            "Please specify which task to unmark.");

                } else if (input.startsWith("unmark ")) {
                    int taskNumber =
                            getTaskNumber(input, "unmark", tasks.size());

                    Task task = tasks.get(taskNumber - 1);
                    task.markAsNotDone();
                    Storage.saveTasks(tasks);

                    System.out.println(
                            "OK, I've marked this task as not done yet:");
                    System.out.println("  " + task);

                } else if (input.equals("delete")) {
                    throw new AdrianException(
                            "Please specify which task to delete.");

                } else if (input.startsWith("delete ")) {
                    int taskNumber =
                            getTaskNumber(input, "delete", tasks.size());

                    Task removedTask = tasks.remove(taskNumber - 1);
                    Storage.saveTasks(tasks);

                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removedTask);
                    System.out.println("Now you have " + tasks.size()
                            + " tasks in the list.");

                } else {
                    throw new AdrianException(
                            "Sorry, I don't understand that command.");
                }

            } catch (AdrianException e) {
                System.out.println("OOPS!!! " + e.getMessage());
            } catch (IOException e) {
                System.out.println("OOPS!!! Could not save tasks.");
            }

            System.out.println(line);
        }

        scanner.close();
    }
    private static int getTaskNumber(
            String input, String command, int numberOfTasks)
            throws AdrianException {

        String numberText = input.substring(command.length()).trim();

        try {
            int taskNumber = Integer.parseInt(numberText);

            if (taskNumber < 1 || taskNumber > numberOfTasks) {
                throw new AdrianException(
                        "That task number does not exist.");
            }

            return taskNumber;

        } catch (NumberFormatException e) {
            throw new AdrianException(
                    "Please enter a valid task number.");
        }
    }
}
