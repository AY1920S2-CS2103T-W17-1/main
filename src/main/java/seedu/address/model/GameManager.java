package seedu.address.model;

import java.util.Random;

import javafx.collections.ObservableList;

import seedu.address.model.deck.Deck;
import seedu.address.model.deck.card.BackFace;
import seedu.address.model.deck.card.Card;



/**
 * Represents the model of the game session.
 */

public class GameManager {
    private Random randGen;
    private boolean flipped;
    private ObservableList<Card> cards;
    private Statistics statistics;
    private int deckSize;
    private int currCardIdx;
    private int cardAttempted;
    private int cardRemaining;


    /**
     * Initializes a GameManager with the given deck.
     */
    public GameManager(Deck deck) {
        randGen = new Random();
        this.flipped = false;
        this.cards = deck.asObservableList();
        this.statistics = new Statistics(cards);
        this.deckSize = this.cards.size();
        this.currCardIdx = randGen.nextInt(this.deckSize);
        this.cardAttempted = 0;
    }

    /**
     * Returns the value of whether a card has been flipped.
     * @return true if a card has been flipped, false otherwise.
     */
    public boolean isFlipped() {
        return this.flipped;
    }

    /**
     * Flips the card to the back face.
     *
     * @return true if the card has not been flipped, false otherwise
     */
    public BackFace flip() {
        if (flipped) {
            return null;
        }
        flipped = true;
        return cards.get(currCardIdx).getBackFace();
    }

    /**
     * Returns a random next card after user answers Yes.
     * Removes the correct card from the deck.
     * @return the next card or null if card list is empty
     */
    public Card answerYes() {
        statistics.incrementCorrectAttempt(cards.get(currCardIdx));
        cards.remove(currCardIdx);
        this.deckSize -= 1;
        flipped = false;
        if (this.deckSize == 0) {
            return null;
        }
        this.currCardIdx = randGen.nextInt(this.deckSize);
        cardAttempted++;
        return cards.get(currCardIdx);
    }

    /**
     * Returns the next card after user answers No.
     * Adds a duplicate wrong card to the deck.
     * @return the next card or null if card list is empty
     */
    public Card answerNo() {
        boolean hasTwoCards = statistics.incrementWrongAttempt(cards.get(currCardIdx));
        if (!hasTwoCards) {
            Card currCard = cards.get(currCardIdx);
            cards.add(currCard);
            this.deckSize += 1;
        }
        flipped = false;
        this.currCardIdx = randGen.nextInt(this.deckSize);
        cardAttempted++;
        return cards.get(currCardIdx);
    }

    /**
     * Returns the statistics report when user stops the sessions.
     */
    public Statistics stop() {
        return this.statistics;
    }

    /**
     * Returns the current index of the card.
     */
    public int getCurrCardIdx() {
        return this.currCardIdx;
    }

    /**
     * Returns the number of cards have been attempted.
     */
    public int getCardAttempted() {
        return cardAttempted;
    }

    /**
     * Returns number of the remaining cards inside the deck.
     */
    public int getDeckSize() {
        return deckSize;
    }

    /**
     * Returns the card list.
     */
    public ObservableList<Card> getCards() {
        return this.cards;
    }
}
