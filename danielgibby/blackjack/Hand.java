package danielgibby.blackjack;

public class Hand {

	public static final int maxCards = 11; // Way to receive 11 cards in a round would be 1x4 + 2x4 + 3x3 = 21 - You should go to Vegas if that happens

	private Card[] cards; // 0 based array, like all java Arrays
	private int nextCardIndex; // 0 based index for cards
	private HandPlayState handPlayState; // what to do for this Hand
	private boolean hasHighAce; // flag for if an Ace is being counted as an 11, reset each time more cards are dealt

	public Hand() {
		// Array that holds maximum of maxCards (don’t allocate extra memory).
		this.setCards(new Card[11]);
		this.nextCardIndex = 0;
		this.setHandPlayState(HandPlayState.Undecided);
	}

	
	
	// normal methods
	
	public void addCard(Card card) {
		if (nextCardIndex < 0 || nextCardIndex > maxCards) {
			throw new IllegalStateException("Hand nextCardIndex is invalid: " + nextCardIndex);
		}
		setHasHighAce(false);
		cards[nextCardIndex++] = card;
	}

	/**
	 * Aces are given a value of 1 if total would be over 21, 11 otherwise
	 * 
	 * automatically setHandPlayState to Stay if 21, Bust if over 21
	 * 
	 * @return total score of hand
	 */
	public int getTotal() {
		int total = 0;

		// because Aces need to be determined last, we need to order the cards and count
		// them last
		Card[] sortedCards = Deck.sortCardsForCounting(this.cards);

		for (Card card : sortedCards) {
			if (card.isFaceUp()) {
				int sortValue = card.getSortValue();
				if (sortValue < 11) {
					total += sortValue;
				} else if (card.getFace().equals(Card.FACE_JACK) || card.getFace().equals(Card.FACE_QUEEN)
						|| card.getFace().equals(Card.FACE_KING)) {
					total += 10;
				} else if (card.getFace().equals(Card.FACE_ACE)) {
					if (total + 11 > 21) {
						total += 1;
					} else {
						total += 11;
						setHasHighAce(true);
					}
				} else {
					throw new IllegalStateException("Illegal card value: " + card.getFace());
				}
			}
		}

		// automatically setHandPlayState to Stay if 21, Bust if over 21
		if (total == 21) {
			this.setHandPlayState(HandPlayState.Stay);
		} else if (total > 21) {
			this.setHandPlayState(HandPlayState.Bust);
		}
		return total;
	}

	/**
	 * determine if has a face down card or an undecided value for an Ace - could
	 * show a different color or something for total;
	 * 
	 * @return false if there is a face down card or an undecided Ace value,
	 *         otherwise true
	 */
	public boolean isTotalComplete() {
		boolean isComplete = true;
		for (Card card : this.cards) {
			if (card.isFaceUp() == false) {
				isComplete = false;
			}
			if (Card.FACE_ACE.equals(card.getFace())) {
				isComplete = false;
			}
		}
		return isComplete;
	}

	/**
	 * for Player interaction, rather than an automated setHandPlayState
	 */
	public void hit() {
		this.setHandPlayState(HandPlayState.Hit);
	}

	/**
	 * for Player interaction, rather than an automated setHandPlayState
	 */
	public void stay() {
		this.setHandPlayState(HandPlayState.Stay);
	}

	
	
	// getters and setters

	public Card[] getCards() {
		return cards;
	}
	
	public void setCards(Card[] cards) {
		this.cards = cards;
	}

	public HandPlayState getHandPlayState() {
		return handPlayState;
	}

	/**
	 * meant for automated calls, use hit() or stay() for player interaction
	 * 
	 * @param playState - HandPlayState
	 */
	protected void setHandPlayState(HandPlayState playState) {
		// don't override if already Bust, only a new Hand can change that
		if (this.handPlayState != HandPlayState.Bust) {
			this.handPlayState = playState;
		}
	}

	public boolean isHasHighAce() {
		return hasHighAce;
	}
	
	public void setHasHighAce(boolean hasHighAce) {
		this.hasHighAce = hasHighAce;
	}

}
