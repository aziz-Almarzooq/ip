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

        String input = scanner.nextLine();

        while(!input.equals("bye")) {
            if(input.equals("list")){
                System.out.println(line);
                for(int i = 0; i<number;i++) {
                    System.out.println(i+1 + ". " + tasks[i]);
                }
                System.out.println(line);
            }

            else if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(5));
                Task task = tasks[taskNumber - 1];

                task.markAsDone();

                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + task);
            }
            else if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(7));
                Task task = tasks[taskNumber - 1];

                task.markAsNotDone();

                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + task);
            }
            else if (input.startsWith("todo ")) {
                String description = input.substring(5);

                tasks[number] = new Todo(description);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[number]);

                number++;

                System.out.println("Now you have " + number
                        + " tasks in the list.");

            } else if (input.startsWith("deadline ")) {
                String details = input.substring(9);

                int byIndex = details.indexOf(" /by ");

                String description = details.substring(0, byIndex);
                String by = details.substring(byIndex + 5);

                tasks[number] = new Deadline(description, by);

                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[number]);

                number++;

                System.out.println("Now you have " + number
                        + " tasks in the list.");

            } else if (input.startsWith("event ")) {
                String details = input.substring(6);

                int fromIndex = details.indexOf(" /from ");
                int toIndex = details.indexOf(" /to ");

                String description = details.substring(0, fromIndex);
                String from = details.substring(fromIndex + 7, toIndex);
                String to = details.substring(toIndex + 5);

                tasks[number] = new Event(description, from, to);

                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[number]);

                number++;

                System.out.println("Now you have " + number
                        + " tasks in the list.");
            }


            System.out.println(line);
            input = scanner.nextLine();

        }

        System.out.println(line);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(line);
        scanner.close();
    }
}