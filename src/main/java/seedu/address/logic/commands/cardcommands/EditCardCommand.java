package seedu.address.logic.commands.cardcommands;

<<<<<<< HEAD
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.dump.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.dump.Address;
import seedu.address.model.deck.dump.Email;
import seedu.address.model.deck.dump.Name;
import seedu.address.model.deck.dump.Phone;
import seedu.address.model.deck.dump.tag.Tag;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Edits the details of an existing person in the address book.
=======
import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.card.Card;

import java.util.List;

/**
 * Edits a card in the deck.
>>>>>>> b759adc08096c8661fd321a19929122145affe9f
 */
public class EditCardCommand extends Command {

    public static final String COMMAND_WORD = "edit";

<<<<<<< HEAD
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditCommand.EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCardCommand(Index index, EditCommand.EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditCommand.EditPersonDescriptor(editPersonDescriptor);
=======
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits a card in the current deck. "
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + "FRONT:BACK\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + "ありがとう:thanks";

    public static final String MESSAGE_SUCCESS = "Card edited: %1$s";

    private final Index targetIndex;
    private final Card editedCard;

    public EditCardCommand(Index targetIndex, Card editedCard) {
        this.targetIndex = targetIndex;
        this.editedCard = editedCard;
>>>>>>> b759adc08096c8661fd321a19929122145affe9f
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
<<<<<<< HEAD
        List<Deck> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Deck deckToEdit = lastShownList.get(index.getZeroBased());
        Deck editedDeck = createEditedPerson(deckToEdit, editPersonDescriptor);

        if (!deckToEdit.isSameDeck(editedDeck) && model.hasPerson(editedDeck)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(deckToEdit, editedDeck);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedDeck));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Deck createEditedPerson(Deck deckToEdit, EditCommand.EditPersonDescriptor editPersonDescriptor) {
        assert deckToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(deckToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(deckToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(deckToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(deckToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(deckToEdit.getTags());

        return new Deck(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
=======

        List<Card> lastShownList = model.getFilteredCardList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
        }

        Card oldCard = lastShownList.get(targetIndex.getZeroBased());

        model.replaceCard(oldCard, editedCard);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedCard));
>>>>>>> b759adc08096c8661fd321a19929122145affe9f
    }

    @Override
    public boolean equals(Object other) {
<<<<<<< HEAD
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return;
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditCommand.EditPersonDescriptor toCopy) {

        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCommand.EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditCommand.EditPersonDescriptor e = (EditCommand.EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }

=======
        return other == this // short circuit if same object
                || (other instanceof EditCardCommand // instanceof handles nulls
                && targetIndex.equals(((EditCardCommand) other).targetIndex)); // state check
    }
>>>>>>> b759adc08096c8661fd321a19929122145affe9f
}
