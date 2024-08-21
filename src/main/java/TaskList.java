import java.util.*;
public class TaskList {
    List<String> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }
    public void addTask(String task) {
        if (!task.equals("list")) {
            tasks.add(task);
            System.out.println("    added: " + task);
        }
        else printTasks();
    }
    public void printTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + ". " + tasks.get(i));
        }
    }
}
