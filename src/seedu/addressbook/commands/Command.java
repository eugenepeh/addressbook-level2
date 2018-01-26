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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static seedu.addressbook.commands.CommandMessages.*;
import static seedu.addressbook.ui.TextUi.DISPLAYED_INDEX_OFFSET;

/**
 * Represents an executable command.
 */
public class Command {
    protected AddressBook addressBook;
    protected List<? extends ReadOnlyPerson> relevantPersons;
    private int targetIndex = -1;
    private final String commandWord;
    private final Person person;
    private final Set<String> keywords;

    public final String feedbackToUser;

    /**
     * Overload constructor used by clear, exit, help, and list commands.
     */
    public Command(String commandWord) {
        this.commandWord = commandWord;
        this.feedbackToUser = null;
        this.keywords = null;
        this.person = null;
    }

    /**
     * Overload constructor used by delete, view, and viewall commands.
     * @param targetIndex last visible listing index of the target person.
     */
    public Command(String commandWord, int targetIndex) {
        this.setTargetIndex(targetIndex);
        this.commandWord = commandWord;
        this.feedbackToUser = null;
        this.keywords = null;
        this.person = null;
    }

    /**
     * Overload constructor for add command.
     */
    public Command(String commandWord, Person person) {
        this.commandWord = commandWord;
        this.feedbackToUser = null;
        this.keywords = null;
        this.person = person;
    }

    /**
     * Overload constructor for incorrect command.
     */
    public Command(String commandWord, String feedbackToUser) {
        this.commandWord = commandWord;
        this.feedbackToUser = feedbackToUser;
        this.keywords = null;
        this.person = null;
    }

    /**
     * Overload constructor for find command.
     */
    public Command(String commandWord, Set<String> keywords) {
        this.commandWord = commandWord;
        this.feedbackToUser = null;
        this.keywords = keywords;
        this.person = null;
    }

    /**
     * Overload constructor for add command using person's raw values.
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
        this.feedbackToUser = null;
        this.keywords = null;
        this.person = new Person(
                new Name(name),
                new Phone(phone, isPhonePrivate),
                new Email(email, isEmailPrivate),
                new Address(address, isAddressPrivate),
                new UniqueTagList(tagSet)
        );
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param personsDisplayed used to generate summary.
     * @return summary message for persons displayed.
     */
    public static String getMessageForPersonListShownSummary(List<? extends ReadOnlyPerson> personsDisplayed) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, personsDisplayed.size());
    }

    public static boolean isExit(Command command) {
        return command.getCommandWord() == EXIT_COMMAND_WORD; // instanceof returns false if it is null
    }

    /**
     * Executes the command and returns the result.
     */
    public CommandResult execute(){
        switch (commandWord) {
            case ADD_COMMAND_WORD:
                return executeAddCommand(person);
            case CLEAR_COMMAND_WORD:
                return executeClearCommand();
            case DELETE_COMMAND_WORD:
                return executeDeleteCommand();
            case EXIT_COMMAND_WORD:
                return executeExitCommand();
            case FIND_COMMAND_WORD:
                return executeFindCommand();
            case INCORRECT_COMMAND_WORD:
                return executeIncorrectCommand();
            case LIST_COMMAND_WORD:
                return executeListCommand();
            case VIEWALL_COMMAND_WORD:
                return executeViewAllCommand();
            case VIEW_COMMAND_WORD:
                return executeViewCommand();
            case HELP_COMMAND_WORD:
            default:
                return executeHelpCommand();
        }
    };

    public String getCommandWord() {
        return commandWord;
    }

    /**
     * Returns a copy of keywords in this command.
     */
    public Set<String> getKeywords() {
        return new HashSet<>(keywords);
    }

    /**
     * Retrieves all persons in the address book whose names contain some of the specified keywords.
     *
     * @param keywords for searching.
     * @return list of persons found.
     */
    private List<ReadOnlyPerson> getPersonsWithNameContainingAnyKeyword(Set<String> keywords) {
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final Set<String> wordsInName = new HashSet<>(person.getName().getWordsInName());
            if (!Collections.disjoint(wordsInName, keywords)) {
                matchedPersons.add(person);
            }
        }
        return matchedPersons;
    }

    /**
     * Supplies the data the command will operate on.
     */
    public void setData(AddressBook addressBook, List<? extends ReadOnlyPerson> relevantPersons) {
        this.addressBook = addressBook;
        this.relevantPersons = relevantPersons;
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }

    /**
     * Extracts the the target person in the last shown list from the given arguments.
     *
     * @throws IndexOutOfBoundsException if the target index is out of bounds of the last viewed listing.
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

    /**
     * Adds a person to the address book.
     */
    private CommandResult executeAddCommand(Person toAdd) {
        try {
            addressBook.addPerson(toAdd);
            return new CommandResult(String.format(ADD_MESSAGE_SUCCESS, toAdd));
        } catch (UniquePersonList.DuplicatePersonException dpe) {
            return new CommandResult(ADD_MESSAGE_DUPLICATE_PERSON);
        }
    }

    /**
     * Clears the address book.
     */
    private CommandResult executeClearCommand() {
        addressBook.clear();
        return new CommandResult(CLEAR_MESSAGE_SUCCESS);
    }

    /**
     * Deletes a person identified using it's last displayed index from the address book.
     */
    private CommandResult executeDeleteCommand() {
        try {
            final ReadOnlyPerson target = getTargetPerson();
            addressBook.removePerson(target);
            return new CommandResult(String.format(DELETE_MESSAGE_DELETE_PERSON_SUCCESS, target));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (UniquePersonList.PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
    }

    /**
     * Terminates the program.
     */
    private CommandResult executeExitCommand() {
        return new CommandResult(EXIT_MESSAGE_EXIT_ACKNOWEDGEMENT);
    }

    /**
     * Finds and lists all persons in address book whose name contains any of the argument keywords.
     * Keyword matching is case sensitive.
     */
    private CommandResult executeFindCommand() {
        final List<ReadOnlyPerson> personsFound = getPersonsWithNameContainingAnyKeyword(keywords);
        return new CommandResult(getMessageForPersonListShownSummary(personsFound), personsFound);
    }

    /**
     * Lists all persons in the address book to the user.
     */
    private CommandResult executeListCommand() {
        List<ReadOnlyPerson> allPersons = addressBook.getAllPersons().immutableListView();
        return new CommandResult(getMessageForPersonListShownSummary(allPersons), allPersons);
    }

    /**
     * Shows help instructions.
     */
    private CommandResult executeHelpCommand() {
        return new CommandResult(
                ADD_MESSAGE_USAGE
                        + "\n" + DELETE_MESSAGE_USAGE
                        + "\n" + CLEAR_MESSAGE_USAGE
                        + "\n" + FIND_MESSAGE_USAGE
                        + "\n" + LIST_MESSAGE_USAGE
                        + "\n" + VIEW_MESSAGE_USAGE
                        + "\n" + VIEWALL_MESSAGE_USAGE
                        + "\n" + HELP_MESSAGE_USAGE
                        + "\n" + EXIT_MESSAGE_USAGE
        );
    }

    /**
     * Represents an incorrect command. Produces some feedback to the user.
     */
    private CommandResult executeIncorrectCommand() {
        return new CommandResult(feedbackToUser);
    }

    /**
     * Shows all details of the person identified using the last displayed index.
     * Private contact details are shown.
     */
    private CommandResult executeViewAllCommand() {
        try {
            final ReadOnlyPerson target = getTargetPerson();
            if (!addressBook.containsPerson(target)) {
                return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
            }
            return new CommandResult(String.format(VIEWALL_MESSAGE_VIEW_PERSON_DETAILS, target.getAsTextShowAll()));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    /**
     * Shows details of the person identified using the last displayed index.
     * Private contact details are not shown.
     */
    private CommandResult executeViewCommand() {
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
