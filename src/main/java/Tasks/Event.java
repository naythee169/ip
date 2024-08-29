package Tasks;

import Exceptions.DelphiException;
import Exceptions.EmptyInputException;
import Exceptions.InvalidInputException;
import Parser.Parser;
import Parser.DateParser;
import Tasks.Task;

/**
 * Represents a Tasks.Event task with a specific time window.
 */
public class Event extends Task {
    private String window;

    /**
     * Constructs an Tasks.Event task with a given description.
     *
     * @param description The description of the Tasks.Event task.
     * @throws EmptyInputException if the description is empty.
     */
    public Event(String description) throws DelphiException {
        super(description);

        int fromIndex = description.indexOf("/from");
        int toIndex = description.indexOf("/to");

        // Validate the positions
        if (fromIndex == -1 || toIndex == -1 || toIndex <= fromIndex) {
            throw new InvalidInputException();
        }

        // Extract the event description
        this.name = description.substring(0, fromIndex).trim();

        // Extract the start time
        String fromPart = description.substring(fromIndex + "/from".length(), toIndex).trim();

        // Extract the end time
        String toPart = description.substring(toIndex + "/to".length()).trim();

        String formattedFromPart = DateParser.parseAndFormatDateTime(fromPart);
        if (formattedFromPart != null) {
            fromPart = "from: " + formattedFromPart;
        } else {
            fromPart = "from: " + fromPart;
        }

        String formattedToPart = DateParser.parseAndFormatDateTime(toPart);
        if (formattedFromPart != null) {
            toPart = "from: " + formattedToPart;
        } else {
            toPart = "to: " + toPart;
        }
        // Reformat the output
        window = "(" + fromPart + " " + toPart + ")";
    }

    /**
     * Returns a string representation of the Tasks.Event task, including its time window.
     *
     * @return The string representation of the Tasks.Event task.
     */
    @Override
    public String toString() {
        return "[E]" + "[" + this.getStatusIcon() + "] " + this.name + " " + window;
    }
}
