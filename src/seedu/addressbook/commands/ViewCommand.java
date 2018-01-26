package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyPerson;

import static seedu.addressbook.commands.CommandMessages.VIEW_MESSAGE_VIEW_PERSON_DETAILS;


/**
 * Shows details of the person identified using the last displayed index.
 * Private contact details are not shown.
 */
public class ViewCommand extends Command {



    public ViewCommand(int targetVisibleIndex) {
        super(CommandMessages.VIEW_COMMAND_WORD, targetVisibleIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPerson target = getTargetPerson();
            if (!addressBook.containsPerson(target)) {
                return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
            }
            return new CommandResult(String.format(VIEW_MESSAGE_VIEW_PERSON_DETAILS, target.getAsTextHidePrivate()));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

}
