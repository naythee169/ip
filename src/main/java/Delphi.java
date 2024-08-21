import java.util.Scanner;

public class Delphi {
    public static void main(String[] args) {
        TaskList t = new TaskList();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! I'm Delphi, the greatest oracle in all of the classical world.");
        System.out.println("To quote a very famous person who will arrive a bit later, ask and ye shall find");

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) break;
            else {
                t.addTask(input);
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
    }
}
