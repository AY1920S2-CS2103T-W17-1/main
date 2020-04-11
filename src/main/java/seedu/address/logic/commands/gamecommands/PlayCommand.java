package seedu.address.logic.commands.gamecommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.card.Card;

/**
 * Starts a game session.
 */
public class PlayCommand extends Command {
    public static final String COMMAND_WORD = "play";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Plays a review session with a deck.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example:" + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Selected deck: %1$s";
    public static final String MESSAGE_DECK_NOT_FOUND = "Deck not found in the library!";
    public static final String MESSAGE_NO_CARD = "Oops, there are no cards in the selected deck.";
    public static final String MESSAGE_ALREADY_PLAY = "You should complete or end this session "
            + "before playing a new one.";

    private final Index targetIdx;

    /**
     * Creates an PlayCommand with a specific {@code Deck}.
     */
    public PlayCommand(Index targetIdx) {
        requireNonNull(targetIdx);
        this.targetIdx = targetIdx;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Deck deck = model.getDeck(targetIdx);
        if (model.getGame() != null) {
            throw new CommandException(MESSAGE_ALREADY_PLAY);
        }
        Card card = model.play(targetIdx);
        if (card == null) {
            throw new CommandException(MESSAGE_DECK_NOT_FOUND);
        }
        if (card.getFrontFace() == null && card.getBackFace() == null) {
            throw new CommandException(String.format(MESSAGE_NO_CARD));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, deck));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PlayCommand // instanceof handles nulls
                && targetIdx.equals(((PlayCommand) other).targetIdx));
    }
}
