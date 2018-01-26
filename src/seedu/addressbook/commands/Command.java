package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Address;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Name;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Phone;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.tag.UniqueTagList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static seedu.addressbook.commands.CommandMessages.ADD_MESSAGE_DUPLICATE_PERSON;
import static seedu.addressbook.commands.CommandMessages.ADD_MESSAGE_SUCCESS;
import static seedu.addressbook.commands.CommandMessages.CLEAR_MESSAGE_SUCCESS;
import static seedu.addressbook.ui.TextUi.DISPLAYED_INDEX_OFFSET;

/**
 * Represents an executable command.
 */
public class Command {
    protected AddressBook addressBook;
    protected List<? extends ReadOnlyPerson> relevantPersons;
    private int targetIndex = -1;
    private String commandWord;
    private Person person;

    /**
     * @param targetIndex last visible listing index of the target person
     */
    public Command(int targetIndex) {
        this.setTargetIndex(targetIndex);
    }

    protected Command() {
    }

    public Command(String commandWord) {
        this.commandWord = commandWord;
    }

    public Command(String commandWord, Person person) {
        this.commandWord = commandWord;
        this.person = person;
    }

    /**
     * Convenience Add constructor using raw values.
     * Adds a person to the address book.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public Command(String commandWord,
                      String name,
                      String phone, boolean isPhonePrivate,
                      String email, boolean isEmailPrivate,
                      String address, boolean isAddressPrivate,
                      Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.commandWord = commandWord;
        this.person = new Person(
                new Name(name),
                new Phone(phone, isPhonePrivate),
                new Email(email, isEmailPrivate),
                new Address(address, isAddressPrivate),
                new UniqueTagList(tagSet)
        );
    }

    public String getCommandWord() {
        return commandWord;
    }
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param personsDisplayed used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForPersonListShownSummary(List<? extends ReadOnlyPerson> personsDisplayed) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, personsDisplayed.size());
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }

    /**
     * Executes the command and returns the result.
     */
    public CommandResult execute(){
        switch (commandWord) {
            case CommandMessages.ADD_COMMAND_WORD:
                return executeAddCommand(person);
            case CommandMessages.CLEAR_COMMAND_WORD:
            default:
                return executeClearCommand();
        }
    };

    /**
     * Supplies the data the command will operate on.
     */
    public void setData(AddressBook addressBook, List<? extends ReadOnlyPerson> relevantPersons) {
        this.addressBook = addressBook;
        this.relevantPersons = relevantPersons;
    }

    /**
     * Extracts the the target person in the last shown list from the given arguments.
     *
     * @throws IndexOutOfBoundsException if the target index is out of bounds of the last viewed listing
     */
    protected ReadOnlyPerson getTargetPerson() throws IndexOutOfBoundsException {
        return relevantPersons.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public void setTargetIndex(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    private CommandResult executeAddCommand(Person toAdd) {
        try {
            addressBook.addPerson(toAdd);
            return new CommandResult(String.format(ADD_MESSAGE_SUCCESS, toAdd));
        } catch (UniquePersonList.DuplicatePersonException dpe) {
            return new CommandResult(ADD_MESSAGE_DUPLICATE_PERSON);
        }
    }

    private CommandResult executeClearCommand() {
        addressBook.clear();
        return new CommandResult(CLEAR_MESSAGE_SUCCESS);
    }
}
