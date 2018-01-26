package seedu.addressbook.commands;

/**
 * Container for display command messages.
 */
public class CommandMessages {

    public static final String ADD_COMMAND_WORD = "add";
    public static final String ADD_MESSAGE_USAGE = ADD_COMMAND_WORD + ": Adds a person to the address book. "
            + "Contact details can be marked private by prepending 'p' to the prefix.\n"
            + "Parameters: NAME [p]p/PHONE [p]e/EMAIL [p]a/ADDRESS  [t/TAG]...\n"
            + "Example: " + ADD_COMMAND_WORD
            + " John Doe p/98765432 e/johnd@gmail.com a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney";
    public static final String ADD_MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String ADD_MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    public static final String CLEAR_COMMAND_WORD = "clear";
    public static final String CLEAR_MESSAGE_USAGE = "Clears address book permanently.\n"
            + "Example: " + CLEAR_COMMAND_WORD;
    public static final String CLEAR_MESSAGE_SUCCESS = "Address book has been cleared!";

    public static final String DELETE_COMMAND_WORD = "delete";
    public static final String DELETE_MESSAGE_USAGE = DELETE_COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX\n"
            + "Example: " + DELETE_COMMAND_WORD + " 1";
    public static final String DELETE_MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    public static final String EXIT_COMMAND_WORD = "exit";
    public static final String EXIT_MESSAGE_USAGE = EXIT_COMMAND_WORD + ": Exits the program.\n"
            + "Example: " + EXIT_COMMAND_WORD;
    public static final String EXIT_MESSAGE_EXIT_ACKNOWEDGEMENT = "Exiting Address Book as requested ...";

    public static final String FIND_COMMAND_WORD = "find";
    public static final String FIND_MESSAGE_USAGE = FIND_COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + FIND_COMMAND_WORD + " alice bob charlie";

    public static final String HELP_COMMAND_WORD = "help";
    public static final String HELP_MESSAGE_USAGE = HELP_COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + HELP_COMMAND_WORD;

    public static final String LIST_COMMAND_WORD = "list";
    public static final String LIST_MESSAGE_USAGE = LIST_COMMAND_WORD
            + ": Displays all persons in the address book as a list with index numbers.\n"
            + "Example: " + LIST_COMMAND_WORD;

    public static final String VIEWALL_COMMAND_WORD = "viewall";
    public static final String VIEWALL_MESSAGE_USAGE = VIEWALL_COMMAND_WORD + ": Views the non-private details of the person "
            + "identified by the index number in the last shown person listing.\n"
            + "Parameters: INDEX\n"
            + "Example: " + VIEWALL_COMMAND_WORD + " 1";
    public static final String VIEWALL_MESSAGE_VIEW_PERSON_DETAILS = "Viewing person: %1$s";

    public static final String VIEW_COMMAND_WORD = "view";
    public static final String VIEW_MESSAGE_USAGE = VIEW_COMMAND_WORD + ": Views the non-private details of the person "
            + "identified by the index number in the last shown person listing.\n"
            + "Parameters: INDEX\n"
            + "Example: " + VIEW_COMMAND_WORD + " 1";
    public static final String VIEW_MESSAGE_VIEW_PERSON_DETAILS = "Viewing person: %1$s";

}
