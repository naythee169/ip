import java.util.Scanner;

public class Delphi {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! I'm Delphi, the greatest oracle in all of the classical world.");
        System.out.println("To quote a very famous person who will arrive a bit later, ask and ye shall find");

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) break;
            else System.out.println("    " + input);
        }

        System.out.println("Bye. Hope to see you again soon!");
    }
}
