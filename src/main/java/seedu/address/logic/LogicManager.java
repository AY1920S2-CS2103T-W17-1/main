package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.MasterParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyLibrary;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.card.Card;
import seedu.address.model.util.Mode;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final MasterParser masterParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.masterParser = new MasterParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command;
        command = masterParser.parseCommand(commandText);
        commandResult = command.execute(model);
        try {
            storage.saveLibrary(model.getLibrary());
        } catch (IOException ioe) {
            //throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    public ReadOnlyLibrary getLibrary() {
        return model.getLibrary();
    }

    @Override
    public ObservableList<Deck> getFilteredDeckList() {
        return model.getFilteredDeckList();
    }

    @Override
    public Path getLibraryFilePath() {
        return model.getLibraryFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public Deck getCurrentDeck() {
        return model.getCurrentDeck();
    }

    @Override
    public ReadOnlyProperty<Deck> selectedDeckProperty() {
        return model.selectedDeckProperty();
    }

    @Override
    public ReadOnlyProperty<Card> playingCardProperty() {
        return model.playingCardProperty();
    }

    @Override
    public ReadOnlyProperty<Mode> currentModeProperty() {
        return model.currentModeProperty();
    }

    @Override
    public ReadOnlyProperty<Boolean> flippedProperty() {
        return model.flippedProperty();
    }

    @Override
    public ReadOnlyProperty<Integer> cardAttemptedProperty() {
        return model.cardAttemptedProperty();
    }

    @Override
    public ReadOnlyProperty<Integer> cardRemainingProperty() {
        return model.cardRemainingProperty();
    }

    @Override
    public void setSelectedDeck(Deck deck) {
        model.setSelectedDeck(deck);
    }

    @Override
    public Mode getMode() {
        return model.getMode();
    }
}
