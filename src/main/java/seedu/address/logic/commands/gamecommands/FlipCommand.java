package seedu.address.logic.commands.gamecommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.card.BackFace;
import seedu.address.model.util.Mode;

/**
 * Flips a card.
 */
public class FlipCommand extends Command {
    public static final String COMMAND_WORD = "flip";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Flips the card to see the back face.\n";
    public static final String MESSAGE_SUCCESS = "Did you get your answer right?";
    public static final String MESSAGE_NOT_PLAY_MODE = "Cannot flip card in non-play view";
    public static final String MESSAGE_ALREADY_FLIPPED = "Card already flipped!";

    private BackFace backFace;
    /**
     * Creates an FlipCommand.
     */
    public FlipCommand() {
        this.backFace = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getMode() == Mode.VIEW) {
            throw new CommandException(MESSAGE_NOT_PLAY_MODE);
        }
        BackFace backFace = model.flip();
        if (backFace == null) {
            throw new CommandException(MESSAGE_ALREADY_FLIPPED);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FlipCommand // instanceof handles nulls
                && backFace.equals(((FlipCommand) other).backFace));
    }
}
