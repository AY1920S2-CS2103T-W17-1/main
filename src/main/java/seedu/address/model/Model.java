package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.Name;
import seedu.address.model.deck.card.BackFace;
import seedu.address.model.deck.card.Card;
import seedu.address.model.util.Mode;
import seedu.address.model.util.View;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Deck> PREDICATE_SHOW_ALL_DECKS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' library file path.
     */
    Path getLibraryFilePath();

    /**
     * Sets the user prefs' library file path.
     */
    void setLibraryFilePath(Path libraryFilePath);

    /**
     * Replaces library data with the data in {@code library}.
     */
    void setLibrary(ReadOnlyLibrary library);

    /** Returns the library */
    ReadOnlyLibrary getLibrary();

    /**
     * Returns true if a deck with the same identity as {@code deck} exists in the library.
     */
    boolean hasDeck(Deck deck);

    /**
     * Deletes the given deck.
     * The deck must exist in the library.
     */
    void deleteDeck(Deck target);

    /**
     * Adds the given deck.
     * {@code deck} must not already exist in the library.
     */
    void createDeck(Deck deck);

    /**
     * Selects a deck.
     */
    void selectDeck(Index targetIdx);

    /**
     * Returns the current deck;
     */
    Deck getCurrentDeck();

    /**
     * Returns the deck at index of library;
     */
    Deck getDeck(Index index);

    /** Renames the deck at index in library.
     * @return true if there is no deck with the same name, false otherwise
     */
    boolean renameDeck(Index targetIndex, Name name);

    /**
     * Returns the readonly property.
     */
    ReadOnlyProperty<Deck> selectedDeckProperty();

    /**
     * Returns the readonly property.
     */
    ReadOnlyProperty<Mode> currentModeProperty();

    /**
     * Returns the readonly property.
     */
    ReadOnlyProperty<Card> playingCardProperty();

    /**
     * Returns the readonly property.
     */
    ReadOnlyProperty<Boolean> flippedProperty();

    /**
     * Returns the readonly property.
     */
    ReadOnlyProperty<Integer> cardAttemptedProperty();

    /**
     * Returns the readonly property.
     */
    ReadOnlyProperty<Integer> cardRemainingProperty();

    /**
     * Brings the user from deck view to library view.
     */
    void returnToLibrary();

    /**
     * Checks if a card with the same identity as {@code card} exists in the deck.
     * @return true if {@code card} exists in the deck
     */
    boolean hasCard(Card card);

    /**
     * Deletes the given card.
     * {@code target} must exist in the deck.
     */
    void deleteCard(Card target);

    /**
     * Adds the given card to the deck.
     * {@code card} must not already exist in the deck.
     */
    void addCard(Card card);

    /**
     * Replaces the given old card with the new card.
     * {@code target} must exist in the deck.
     * {@code card} must not already exist in the deck.
     */
    void replaceCard(Card target, Card card);

    /**
     * Gets the current view of the model.
     * @return The current view of the model.
     */
    View getView();

    /**
     * Replaces the given deck {@code target} with {@code editedDeck}.
     * {@code target} must exist in the library.
     * The deck identity of {@code editedDeck} must not be the same as another existing deck in the library.
     */
    void setDeck(Deck target, Deck editedDeck);

    /** Returns an unmodifiable view of the filtered deck list. */
    ObservableList<Deck> getFilteredDeckList();

    /** Returns an unmodifiable view of the filtered card list. */
    ObservableList<Card> getFilteredCardList();

    /**
     * Updates the filter of the filtered deck list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDeckList(Predicate<Deck> predicate);

    /**
     * Sets the selected deck in the filtered deck list.
     */
    void setSelectedDeck(Deck deck);

    /**
     * Sets the value of flipped in model manager.
     */
    void setFlipped(Boolean value);

    /**
     * Sets the number of cards attempted.
     */
    void setCardAttempted(int value);

    /**
     * Sets the number of remaining cards in a deck.
     */
    void setCardRemaining(int value);

    /**
     * Sets the mode of the model manager.
     */
    void setCurrentMode(Mode mode);

    /**
     * Sets the playing card.
     */
    void setPlayingCard(Card card);

    /**
     * Returns the card with the given index.
     */
    Card getCard(Index index);

    /**
     * Changes the mode to play mode.
     */
    Card play(Index index);

    /**
     * Flips the card to the back face.
     * @return true if the card has not been flipped, false otherwise
     */
    BackFace flip();

    /**
     * Returns the next card after user answers Yes.
     * @return the next card or null if card list is empty
     */
    Card answerYes();

    /**
     * Returns the statistics report when user stop the game session.
     * @return the statistics object.
     */
    Statistics stop();

    /**
     * Returns the game manager object.
     */
    GameManager getGame();

    /**
     * Returns the next card after user answers No.
     * @return the next card or null if card list is empty
     */
    Card answerNo();

    /**
     * Returns the current mode of the model
     */
    Mode getMode();
}
