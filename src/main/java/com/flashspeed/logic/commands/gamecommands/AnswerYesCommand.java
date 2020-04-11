package com.flashspeed.logic.commands.gamecommands;

import static java.util.Objects.requireNonNull;

import com.flashspeed.logic.commands.Command;
import com.flashspeed.logic.commands.CommandResult;
import com.flashspeed.logic.commands.exceptions.CommandException;
import com.flashspeed.model.Model;
import com.flashspeed.model.Statistics;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.model.util.Mode;

/**
 * Answers yes the a card.
 */
public class AnswerYesCommand extends Command {
    public static final String COMMAND_WORD = "yes";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Answer yes if you have the correct answer.\n";
    public static final String MESSAGE_SUCCESS = "Nice, you got it right!";
    public static final String MESSAGE_NOT_PLAY_MODE = "Cannot answer card in non-play view";
    public static final String MESSAGE_NOT_FLIPPED = "Card has not flipped yet!";
    public static final String MESSAGE_END_GAME = "Session completed!";

    private Statistics statistics;

    /**
     * Creates an AnswerYesCommand.
     */
    public AnswerYesCommand() { }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getMode() == Mode.VIEW) {
            throw new CommandException(MESSAGE_NOT_PLAY_MODE);
        }
        if (!model.getGame().isFlipped()) {
            throw new CommandException(MESSAGE_NOT_FLIPPED);
        }
        Card nextCard = model.answerYes();
        if (nextCard == null) {
            statistics = model.stop();
            return new CommandResult(String.format(MESSAGE_END_GAME), false, false, true, statistics);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AnswerYesCommand); // instanceof handles nulls

    }
}
