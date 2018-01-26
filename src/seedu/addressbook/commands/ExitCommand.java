package seedu.addressbook.commands;

import static seedu.addressbook.commands.CommandMessages.EXIT_MESSAGE_EXIT_ACKNOWEDGEMENT;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    @Override
    public CommandResult execute() {
        return new CommandResult(EXIT_MESSAGE_EXIT_ACKNOWEDGEMENT);
    }

    public static boolean isExit(Command command) {
        return command instanceof ExitCommand; // instanceof returns false if it is null
    }
}
