package danielgibby.blackjack;

import java.util.Arrays;
import java.util.Random;

public class Deck {
	private Card[] cards; // 0 based Array (like all java arrays)
	private int lastIndexInDeck; // 0 based index to match - number of cards in deck is + 1
	private boolean shuffled;
	private static Random random;
	
	/**
	 * constructor
	 */
	public Deck() {
		this(true);
	}
	/**
	 * constructor allows a shuffled or unshuffled Deck
	 * @param shuffle
	 */
	public Deck(boolean shuffle) {
		lastIndexInDeck = -1; // 0 based Array of cards, so this signifies we don't have any yet
		initializeDeck();
		if (shuffle) {
			shuffleDeck();
		}
	}
	
	
	
	// normal methods
	
	/**
	 * create a normal deck of 52 unshuffled cards
	 */
	public void initializeDeck() {
		// initialize array and index
		this.setCards(new Card[52]);
		this.setShuffled(false);
		lastIndexInDeck = 0;
		
		// assign face values and suit
		for (Card.Suit suit : Card.Suit.values()) {

			// create 2 - 10 Face Cards in Suit
			for (int cardFace = 2; cardFace <= 10; cardFace++) {
				createNewCard(Integer.toString(cardFace), suit);
			}

			// create A,J,Q,K Face Cards in Suit
			createNewCard(Card.FACE_ACE, suit);
			createNewCard(Card.FACE_JACK, suit);
			createNewCard(Card.FACE_QUEEN, suit);
			createNewCard(Card.FACE_KING, suit);
		}
	}
	
	/**
	 * create a new Card with given face and suit and add to Deck cards array
	 * @param face
	 * @param suit
	 */
	private void createNewCard(String face, Card.Suit suit) {
		this.cards[lastIndexInDeck++] = new Card(face, suit);
	}
	
	/**
	 * shuffle the this.cards array by reference
	 */
	public void shuffleDeck() {
		this.setCards(sortCardsForCounting(this.cards));
		this.setShuffled(true);
	}
	
	/**
	 * @return sorted cards in order of 2,3,4,5,6,7,8,9,10,J,Q,K,A
	 */
	public static Card[] sortCardsForCounting(Card[] cards) {
		Card[] sortedCards = cards.clone();
		Arrays.sort(sortedCards, new CardSort());
		return sortedCards;
	}

	/**
	 * reorder the Card array by reference
	 * @param cards
	 */
	public static void shuffleCards(Card[] cards) {
		random = new Random();
		int numCards = cards.length;
		for (int count = numCards; count > 1; count--) {
			swapCard(cards, count - 1, random.nextInt(count));
		}
	}
	
	/**
	 * Swap two cards in the array by reference
	 * @param Array of Card[]s
	 * @param index1 - to swap with index2
	 * @param index2 - to swap with index1
	 */
	private static void swapCard(Card[] array, int index1, int index2) {
		Card temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}
	
	/**
	 * not only returns Card, but removes it from the Deck
	 * @return last Card in the Deck or null if empty Deck
	 */
	public Card getAndRemoveNextCard() {
		if (lastIndexInDeck < 0) {
			return null;
		}
		Card card = this.cards[lastIndexInDeck];
		this.cards[lastIndexInDeck] = null;
		lastIndexInDeck--;
		return card;
	}
	
	/**
	 * 
	 * @return size of deck
	 */
	public int getDeckSize() {
		return this.lastIndexInDeck + 1;
	}

	
	// getters and setters

	public Card[] getCards() {
		return cards;
	}
	public void setCards(Card[] cards) {
		this.cards = cards;
		this.lastIndexInDeck = cards.length;
	}

	public boolean isShuffled() {
		return shuffled;
	}
	public void setShuffled(boolean shuffled) {
		this.shuffled = shuffled;
	}



}
