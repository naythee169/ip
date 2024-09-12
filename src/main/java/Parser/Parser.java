package Parser;

import Commands.AddDeadlineCommand;
import Commands.AddEventCommand;
import Commands.AddTodoCommand;
import Commands.BreakCommand;
import Commands.Command;
import Commands.DeleteTaskCommand;
import Commands.FindCommand;
import Commands.ListCommand;
import Commands.MarkTaskCommand;
import Commands.UnmarkTaskCommand;
import Commands.UpdateTaskCommand;
import Exceptions.DelphiException;
import Exceptions.InvalidInputException;

/**
 * Provides the parsing functionality that allows UI.Delphi to process different types of input.
 * It identifies commands and creates corresponding Command objects or throws exceptions for invalid inputs.
 *
 * @author jordanchan
 */
public class Parser {

    DateParser d = new DateParser();

    /**
     * Parses the given input string and returns an appropriate Command object based on the content of the input.
     * Recognizes specific commands and creates instances of their corresponding Command subclasses.
     *
     * @param input The input string to be parsed.
     * @return A Command object representing the parsed input.
     * @throws DelphiException If the input does not match any known command.
     */
    public Command parseInput(String input) throws DelphiException {
        if (input.equals("bye")) {
            return new BreakCommand();
        } else if (checkStringPrefix(input, 4, "mark")) {
            return new MarkTaskCommand(input);
        } else if (checkStringPrefix(input, 6, "unmark")) {
            return new UnmarkTaskCommand(input);
        } else if (checkStringPrefix(input, 6, "delete")) {
            return new DeleteTaskCommand(input);
        } else if (input.equals("list")) {
            return new ListCommand(input);
        } else if (checkStringPrefix(input, 4, "todo")) {
            return new AddTodoCommand(input);
        } else if (checkStringPrefix(input, 8, "deadline")) {
            return new AddDeadlineCommand(input);
        } else if (checkStringPrefix(input, 5, "event")) {
            return new AddEventCommand(input);
        } else if (checkStringPrefix(input, 4, "find")) {
            return new FindCommand(input);
        } else if (checkStringPrefix(input, 6, "update")) {
            return new UpdateTaskCommand(input);
        } else {
            throw new InvalidInputException();
        }
    }

    /**
     * Checks if the first part of the input matches a certain string up to a given number of characters.
     *
     * @param original The string to be compared against.
     * @param index The number of characters to compare from the start of the string.
     * @param comparison The string to compare the prefix against.
     * @return True if the prefix of the original string matches the comparison string, false otherwise.
     */
    public boolean checkStringPrefix(String original, int index, String comparison) {
        // Ensure the index is within the bounds of the original string
        if (index > original.length()) {
            index = original.length();
        }

        // Get the substring from the original string up to the specified index
        String prefix = original.substring(0, index);

        // Compare the prefix with the comparison string
        return prefix.equals(comparison);
    }

    /**
     * Formats a deadline string by extracting and reformatting the components.
     *
     * @param input The input string containing the deadline information.
     * @return The formatted string where the deadline information is represented as "/by date".
     */
    public static String formatStringDeadline(String input) {
        // Extract the parts using regex
        String regex = "(.*) \\(by: (.*)\\)";
        return input.replaceAll(regex, "$1 /by $2");
    }

    /**
     * Formats an event string by extracting and reformatting the components.
     *
     * @param input The input string containing the event information.
     * @return The formatted string where the event information is represented as "/from  start /to end".
     */
    public static String formatStringEvent(String input) {
        // Extract the parts using regex
        String regex = "(.*) \\(from: (.*) to: (.*)\\)";
        return input.replaceAll(regex, "$1 /from $2 /to $3");
    }

    /**
     * Parses a deadline string and extracts the task description and the formatted deadline.
     * The input string is expected to contain the task description followed by "/by" and then the deadline.
     *
     * @param s The input string containing the task description and deadline, separated by "/by".
     * @return A String array where:
     *         - The first element is the task description (trimmed).
     *         - The second element is the formatted deadline wrapped in "(by: ...)".
     * @throws InvalidInputException if the input string does not contain "/by".
     */
    public String[] parseDeadline(String s) throws InvalidInputException{
        String[] res = new String[2];
        int slashIndex = s.indexOf("/by");
        if (slashIndex == -1) {
            throw new InvalidInputException();
        }
        //remove whitespace to ensure correct formatting
        res[0] = s.substring(0, slashIndex).trim();

        // Extract the substring after the slash and trim it
        String deadline = s.substring(slashIndex + 3).trim();
        String date = d.parseAndFormatDateTime(deadline);
        if (date != null) {
            deadline = "(by: " + date + ")";
        } else {
            deadline = "(by: " + s.substring(3).trim() + ")";
        }
        res[1] = deadline;
        return res;
    }

    public String[] parseEvent(String s) throws InvalidInputException {
        String[] res = new String[3];

        int fromIndex = s.indexOf("/from");
        int toIndex = s.indexOf("/to");

        // make sure there is a "from" and "to" part
        if (fromIndex == -1 || toIndex == -1 || toIndex <= fromIndex) {
            throw new InvalidInputException();
        }

        // Extract the event description
        res[0] = s.substring(0, fromIndex).trim();

        // Extract the start time
        String fromPart = s.substring(fromIndex + "/from".length(), toIndex).trim();

        // Extract the end time
        String toPart = s.substring(toIndex + "/to".length()).trim();

        // Reformat the output
        String formattedFromPart = d.parseAndFormatDateTime(fromPart);
        if (formattedFromPart != null) {
            fromPart = "from: " + formattedFromPart;
        } else {
            fromPart = "from: " + fromPart;
        }
        res[1] = fromPart;

        String formattedToPart = d.parseAndFormatDateTime(toPart);
        if (formattedFromPart != null) {
            toPart = "to: " + formattedToPart;
        } else {
            toPart = "to: " + toPart;
        }
        res[2] = toPart;
        return res;
    }
}
