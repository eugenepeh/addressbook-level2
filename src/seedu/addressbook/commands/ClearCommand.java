package seedu.addressbook.commands;

import static seedu.addressbook.commands.CommandMessages.CLEAR_MESSAGE_SUCCESS;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        addressBook.clear();
        return new CommandResult(CLEAR_MESSAGE_SUCCESS);
    }
}
