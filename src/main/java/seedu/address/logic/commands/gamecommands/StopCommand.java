package seedu.address.logic.commands.gamecommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Statistics;
import seedu.address.model.util.Mode;

/**
 * Stops the game session.
 */
public class StopCommand extends Command {
    public static final String COMMAND_WORD = "stop";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Stop the current game session.\n";
    public static final String MESSAGE_SUCCESS = "Session stopped!";
    public static final String MESSAGE_NOT_PLAY_MODE = "You have not started a new game session";

    private Statistics statistics;

    /**
     * Creates an StopCommand
     */
    public StopCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getMode() == Mode.VIEW) {
            throw new CommandException(MESSAGE_NOT_PLAY_MODE);
        }
        statistics = model.stop();
        //return new CommandResult(String.format(MESSAGE_SUCCESS, statistics));
        return new CommandResult(String.format(MESSAGE_SUCCESS), false, false, true, statistics);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StopCommand // instanceof handles nulls
                && statistics.equals(((StopCommand) other).statistics));
    }
}
