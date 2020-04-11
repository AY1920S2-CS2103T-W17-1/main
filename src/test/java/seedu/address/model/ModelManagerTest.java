package seedu.address.model;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.DeckUtils.JAPANESE_DECK;
import static seedu.address.testutil.DeckUtils.MALAY_DECK;
import static seedu.address.testutil.CardUtils.JAP_CARD_1;
import static seedu.address.testutil.CardUtils.JAP_CARD_2;
import static seedu.address.testutil.CardUtils.JAP_CARD_3;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javafx.beans.property.SimpleObjectProperty;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.deckcommands.SelectDeckCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.card.BackFace;
import seedu.address.model.deck.card.Card;
import seedu.address.model.util.Mode;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.LibraryBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Library(), new Library(modelManager.getLibrary()));
        assertEquals(Optional.empty(), modelManager.getDeckIndex());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setLibraryFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setLibraryFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setLibraryFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setLibraryFilePath(null));
    }

    @Test
    public void setLibraryFilePath_validPath_setsLibraryPath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setLibraryFilePath(path);
        assertEquals(path, modelManager.getLibraryFilePath());
    }

    @Test
    public void getFilteredDeckList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredDeckList().remove(0));
    }

    @Test
    public void hasDeck_nullDeck_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasDeck(null));
    }

    @Test
    public void hasDeck_deckNotInLibrary_returnsFalse() {
        assertFalse(modelManager.hasDeck(JAPANESE_DECK));
    }

    @Test
    public void hasDeck_deckInLibrary_returnsTrue() {
        modelManager.createDeck(JAPANESE_DECK);
        assertTrue(modelManager.hasDeck(JAPANESE_DECK));
    }

    @Test
    public void hasDeck_deckNotInLibrary_afterDeleteDeck_returnsFalse() {
        modelManager.createDeck(JAPANESE_DECK);
        modelManager.deleteDeck(JAPANESE_DECK);
        assertFalse(modelManager.hasDeck(JAPANESE_DECK));
    }

    @Test
    public void getCurrentDeck_returnsNull() {
        assertNull(modelManager.getCurrentDeck());
    }

    @Test
    public void getCurrentDeck_returnsCurrentDeck() throws ParseException{
        modelManager.createDeck(JAPANESE_DECK);
        try {
            Index index = ParserUtil.parseIndex("1");
            modelManager.selectDeck(index);
            assertEquals(modelManager.getCurrentDeck(), JAPANESE_DECK);
        }  catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeckCommand.MESSAGE_USAGE), pe);
        }
    }

    @Test
    public void setSelectedDeck_returnsSelectedDeckValue() {
        modelManager.createDeck(JAPANESE_DECK);
        modelManager.setSelectedDeck(JAPANESE_DECK);
        assertEquals(modelManager.selectedDeckProperty().getValue(), JAPANESE_DECK);
    }


    @Test
    public void hasCard_cardInDeck_returnsTrue() throws ParseException{
        modelManager.createDeck(JAPANESE_DECK);
        try {
            Index index = ParserUtil.parseIndex("1");
            modelManager.selectDeck(index);
            assertTrue(modelManager.hasCard(JAP_CARD_1));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeckCommand.MESSAGE_USAGE), pe);
        }
    }

    @Test
    public void getCard_returnsTrue() throws  ParseException {
        modelManager.createDeck(JAPANESE_DECK);
        try {
            Index deckIndex = ParserUtil.parseIndex("1");
            modelManager.selectDeck(deckIndex);
            Index cardIndex = ParserUtil.parseIndex("1");
            assertEquals(modelManager.getCard(cardIndex), JAP_CARD_1);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeckCommand.MESSAGE_USAGE), pe);
        }

    }

    @Test
    public void hasCard_cardNotINDeck_afterDeleteCard_returnsFalse() throws ParseException {
        modelManager.createDeck(JAPANESE_DECK);
        try {
            Index index = ParserUtil.parseIndex("1");
            modelManager.selectDeck(index);
            modelManager.deleteCard(JAP_CARD_1);
            assertFalse(modelManager.hasCard(JAP_CARD_1));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeckCommand.MESSAGE_USAGE), pe);
        }
    }

    @Test
    public void hasCard_cardNrReplaceCad_returnsFalse() throws ParseException {
        modelManager.createDeck(JAPANESE_DECK);
        Card newCard = new CardBuilder().withFrontFace("newFront1").withBackFace("newBack1").build();
        try {
            Index index = ParserUtil.parseIndex("1");
            modelManager.selectDeck(index);
            modelManager.replaceCard(JAP_CARD_2, newCard);
            assertFalse(modelManager.hasCard(JAP_CARD_2));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeckCommand.MESSAGE_USAGE), pe);
        }
    }

    @Test
    public void hasCard_cardInDeck_afterReplaceCard_returnsTrue() throws ParseException {
        modelManager.createDeck(JAPANESE_DECK);
        Card newCard = new CardBuilder().withFrontFace("newFront2").withBackFace("newBack2").build();
        try {
            Index index = ParserUtil.parseIndex("1");
            modelManager.selectDeck(index);
            modelManager.replaceCard(JAP_CARD_3, newCard);
            assertTrue(modelManager.hasCard(newCard));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeckCommand.MESSAGE_USAGE), pe);
        }
    }

    @Test
    public void cardReturned_afterPlay_returnsNull() throws ParseException{
        modelManager.createDeck(JAPANESE_DECK);
        try {
            Index index = ParserUtil.parseIndex("10");
            Card card = modelManager.play(index);
            assertEquals(card, null);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeckCommand.MESSAGE_USAGE), pe);
        }
    }


    @Test
    public void gameCreated_afterPlay_returnsTrue() throws ParseException{
        modelManager.createDeck(JAPANESE_DECK);
        try {
            Index index = ParserUtil.parseIndex("1");
            modelManager.selectDeck(index);
            Card card = modelManager.play(index);
            assertFalse(modelManager.getGame() == null);
            assertEquals(modelManager.getMode(), Mode.PLAY);
            assertEquals(modelManager.playingCardProperty().getValue(), card);
            assertEquals(modelManager.flippedProperty().getValue(), false);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeckCommand.MESSAGE_USAGE), pe);
        }
    }

    @Test
    public void getBackFace_afterFlip_returnsTrue() throws ParseException{
        modelManager.createDeck(JAPANESE_DECK);
        try {
            Index index = ParserUtil.parseIndex("1");
            modelManager.selectDeck(index);
            Card card = modelManager.play(index);
            BackFace backFace = modelManager.flip();
            assertEquals(modelManager.flippedProperty().getValue(), true);
            assertEquals(backFace, card.getBackFace());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeckCommand.MESSAGE_USAGE), pe);
        }
    }

    @Test
    public void getNextCard_afterAnswerYes_returnsTrue() throws ParseException {
        modelManager.createDeck(JAPANESE_DECK);
        try {
            Index index = ParserUtil.parseIndex("1");
            modelManager.selectDeck(index);
            modelManager.play(index);
            modelManager.flip();
            Card card = modelManager.answerYes();
            assertEquals(modelManager.flippedProperty().getValue(), false);
            assertEquals(modelManager.playingCardProperty().getValue(), card);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeckCommand.MESSAGE_USAGE), pe);
        }
    }

    @Test
    public void getNextCard_afterAnswerNo_returnsTrue() throws ParseException {
        modelManager.createDeck(JAPANESE_DECK);
        try {
            Index index = ParserUtil.parseIndex("1");
            modelManager.selectDeck(index);
            modelManager.play(index);
            modelManager.flip();
            Card card = modelManager.answerNo();
            assertEquals(modelManager.flippedProperty().getValue(), false);
            assertEquals(modelManager.playingCardProperty().getValue(), card);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeckCommand.MESSAGE_USAGE), pe);
        }
    }

    @Test
    public void equals() {
        Library library = new LibraryBuilder().withDeck(JAPANESE_DECK).withDeck(MALAY_DECK).build();
        Library differentLibrary = new Library();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(library, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(library, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentLibrary, userPrefs)));

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setLibraryFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(library, differentUserPrefs)));
    }
}
