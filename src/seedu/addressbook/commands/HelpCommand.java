package seedu.addressbook.commands;


/**
 * Shows help instructions.
 */
public class HelpCommand extends Command {
    public HelpCommand() {}

    @Override
    public CommandResult execute() {
        return new CommandResult(
                CommandMessages.ADD_MESSAGE_USAGE
                + "\n" + CommandMessages.DELETE_MESSAGE_USAGE
                + "\n" + CommandMessages.CLEAR_MESSAGE_USAGE
                + "\n" + CommandMessages.FIND_MESSAGE_USAGE
                + "\n" + CommandMessages.LIST_MESSAGE_USAGE
                + "\n" + CommandMessages.VIEW_MESSAGE_USAGE
                + "\n" + CommandMessages.VIEWALL_MESSAGE_USAGE
                + "\n" + CommandMessages.HELP_MESSAGE_USAGE
                + "\n" + CommandMessages.EXIT_MESSAGE_USAGE
        );
    }
}
