package ui;

import command.Command;
import exceptions.DelphiException;
import exceptions.HardDriveNotFoundException;
import parser.Parser;
import storage.Storage;
import tasklist.TaskList;

/**
 * The main class for running the ui.Delphi application.
 * It manages the core components such as the task list, storage, parser, and user interface.
 */
public class Delphi {
    private final TaskList taskList;
    private final Storage storage;
    private final Parser parser;
    private final Ui ui;

    /**
     * Constructs a Delphi instance with the specified file path for storage.
     *
     * @param filePath The path to the file where task data is stored.
     */
    public Delphi(String filePath) {
        storage = new Storage(filePath);
        taskList = new TaskList();
        parser = new Parser();
        ui = new Ui();
    }

    /**
     * returns the Delphi bot welcome message upon startup
     */
    public String welcomeMessage() {
        return ui.welcomeMessage();
    }

    /**
     * @return the tasks stored in the hard drive at the time of starting the bot
     */
    public String currentTasks() {
        try {
            taskList.loadStorageToTasks(this.storage);
            return "current tasks: \n" + taskList.printTasks();
        } catch (HardDriveNotFoundException e) {
            return e.getMessage();
        }
    }

    /**
     * @param input the user input
     * @return the message return by the Command object once executed or the error message if there is one
     */
    public String getResponse(String input) {
        try {
            Command c = parser.parseInput(input);
            return c.execute(taskList, storage, ui, parser);
        } catch (DelphiException e) {
            return e.getMessage();
        }
    }
}

