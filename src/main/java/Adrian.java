import java.util.Scanner;

public class Adrian {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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
            System.out.println(" " + input);
            System.out.println(line);
            input = scanner.nextLine();

        }


        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(line);
        scanner.close();
    }
}