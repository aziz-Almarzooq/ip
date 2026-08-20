import java.util.Scanner;

public class Adrian {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];;
        int number = 0;
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

                    for (int i = 0; i < number; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
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

                    tasks[number] = new Todo(description);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[number]);

                    number++;

                    System.out.println(
                            "Now you have " + number + " tasks in the list.");

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

                    tasks[number] = new Deadline(description, by);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[number]);

                    number++;

                    System.out.println(
                            "Now you have " + number + " tasks in the list.");

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

                    tasks[number] = new Event(description, from, to);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[number]);

                    number++;

                    System.out.println(
                            "Now you have " + number + " tasks in the list.");

                } else if (input.startsWith("mark ")) {
                    int taskNumber = getTaskNumber(input, "mark", number);

                    tasks[taskNumber - 1].markAsDone();

                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks[taskNumber - 1]);

                } else if (input.startsWith("unmark ")) {
                    int taskNumber = getTaskNumber(input, "unmark", number);

                    tasks[taskNumber - 1].markAsNotDone();

                    System.out.println(
                            "OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks[taskNumber - 1]);

                } else {
                    throw new AdrianException(
                            "Sorry, I don't understand that command.");
                }

            } catch (AdrianException e) {
                System.out.println(e.getMessage());
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
