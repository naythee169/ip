package Commands;

import java.util.List;

import Storage.Storage;
import Tasks.Task;
import TaskList.TaskList;
import UI.Ui;

public class FindCommand extends Command {
    public FindCommand(String s) {
        super(s);
    }

    /**
     * creates a list of tasks that contain the given keyword
     *
     * @param t  The task list to operate on.
     * @param s  The storage to use for saving/loading data.
     * @param ui The user interface to interact with the user.
     * @return the string representation of the tasks
     */
    public String execute(TaskList t, Storage s, Ui ui) {
        String res = "";
        List<Task> found = t.findTask(getInput().substring(5));
        res += ui.findingTask();
        for (Task tsk : found) {
            res += tsk.toString();
            res += "\n";
        }
        return res;
    }
}
