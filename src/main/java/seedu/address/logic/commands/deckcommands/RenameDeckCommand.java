package seedu.address.logic.commands.deckcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.Name;

/**
 * Edits the name of a deck in the library.
 */
public class RenameDeckCommand extends Command {

    public static final String COMMAND_WORD = "rename";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the name of the deck identified "
            + "by the index number used in the displayed deck list.\n"
            + "Existing name will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "NAME \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "Japanese 2";

    public static final String MESSAGE_RENAME_DECK_SUCCESS = "Edited Deck: %1$s";
    public static final String MESSAGE_NOT_EDITED = "New deck name must be provided.";
    public static final String MESSAGE_DUPLICATE_DECK = "This deck name already exists in the library.";

    private final Index index;
    private final Name name;

    /**
     * @param index of the deck in the library list to edit
     * @param name new name to edit the deck with
     */
    public RenameDeckCommand(Index index, Name name) {
        requireAllNonNull(index, name);

        this.index = index;
        this.name = name;
    }

    // need to remake this method
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // to recode
        List<Deck> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Deck deckToEdit = lastShownList.get(index.getZeroBased());
        Deck editedDeck = renameDeck(deckToEdit, name);

        if (!deckToEdit.isSameDeck(editedDeck) && model.hasPerson(editedDeck)) {
            throw new CommandException(MESSAGE_DUPLICATE_DECK);
        }

        model.setPerson(deckToEdit, editedDeck);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_RENAME_DECK_SUCCESS, editedDeck));

    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Deck renameDeck(Deck deckToEdit, Name name) {
        assert deckToEdit != null;

        return deckToEdit;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RenameDeckCommand // instanceof handles nulls
                && index.equals(((RenameDeckCommand) other).index)
                && index.equals(((RenameDeckCommand) other).name));
    }
}