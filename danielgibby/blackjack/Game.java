package danielgibby.blackjack;

public class Game {

	// cross round variables:
	private Participant[] participants;
	private int dealerParticipantIndex;
	private int roundsPlayed;
	private boolean playAgain;

	// per round variables:
	private Deck deck;
	private boolean roundOver;
	int maxNoBustPlayerTotal = 0;

	/**
	 * ran once per Game of multiple rounds
	 */
	protected void initializeGame() {
		this.dealerParticipantIndex = 0;
		this.participants[dealerParticipantIndex] = new ParticipantDealer();
		this.participants[1] = new ParticipantPlayer();
		this.setRoundsPlayed(1);
		this.setPlayAgain(true); // make them ask to stop playing, stop if any Participant asks to stop
	}

	/**
	 * clear out any variables or cache that needs to be cleaned between rounds or
	 * end of Game
	 */
	protected void cleanupRound() {
		roundOver = true;
		this.deck = null;
		roundsPlayed++;
	}

	/**
	 * setup game play for another round
	 */
	protected void inializeRound() {
		this.deck = new Deck();
		roundOver = false;
	}

	/**
	 * first deal of 2 cards each to all participants (Dealer and Players)
	 */
	protected void dealFirstTwoCards() {
		this.dealCards(false, true);
		this.dealCards(true, true);
	}

	/**
	 * deals a card to each Hand if in HandPlayState.Hit
	 * 
	 * @param firstDeal - if Dealer and firstDeal, will
	 */
	protected void dealCards(boolean dealerCardFaceUp, boolean playerCardFaceUp) {
		for (Participant participant : participants) {
			for (Hand hand : participant.getHands()) {

				HandPlayState handPlayState = hand.getHandPlayState();

				// can't deal another card if already bust
				if (HandPlayState.Bust.equals(handPlayState)) {
					continue;
				} else if (HandPlayState.Stay.equals(handPlayState)) {
					continue;
				}

				// we have a Hit
				Card card = deck.getAndRemoveNextCard();

				// give cards face up to Players
				if (playerCardFaceUp && Participant.PARTICIPANT_TYPE_PLAYER.equals(participant.getParticipantType())) {
					card.setFaceUp(true);
				}
				// give 2nd card face up to Dealer
				else if (dealerCardFaceUp
						&& Participant.PARTICIPANT_TYPE_DEALER.equals(participant.getParticipantType())) {
					card.setFaceUp(true);
				}

				hand.addCard(card);
			}
		}
	}

	/**
	 * calculate values of all Hands - set Hand to BlackJack if 21;
	 * set maxPlayerTotal for Dealer to hit if below;
	 * 
	 */
	protected void calculateHandValues() {
		maxNoBustPlayerTotal = 0;
		for (Participant participant : participants) {
			for (Hand hand : participant.getHands()) {

				// calculate hand total
				int handTotal = hand.getTotal();

				// setHandPlayState to BlackJack or Stay if 21
				if (handTotal == 21) {
					if (hand.getCards().length == 2) {
						hand.setHandPlayState(HandPlayState.BlackJack);
					} else {
						hand.setHandPlayState(HandPlayState.Stay);
					}
				}
				// setHandPlayState to Bust if over
				else if (handTotal > 21) {
					hand.setHandPlayState(HandPlayState.Bust);
				}
				if (Participant.PARTICIPANT_TYPE_PLAYER.equals(participant.getParticipantType())) {
					if (handTotal > maxNoBustPlayerTotal) {
						maxNoBustPlayerTotal = handTotal;
					}
				}
			}
		}
	}

	/**
	 * turn all dealt Cards face up or face down NOTE: as the only face down card
	 * should be the Dealer's 2nd, we could keep track of that and not have to do
	 * this loop every time. But this method could be handy somewhere else.
	 * 
	 * @param faceUp boolean true for faceUp or false for faceDown
	 */
	protected void turnCards(boolean faceUp) {
		for (Participant participant : participants) {
			for (Hand hand : participant.getHands()) {
				for (Card card : hand.getCards()) {
					card.setFaceUp(faceUp);
				}
			}
		}
	}

	/**
	 * sets roundOver to false if some Hand still HandPlayState.Hit, true otherwise
	 * (Stay, Bust or BlackJack)
	 */
	protected boolean isRoundOver() {
		boolean calcRoundOver = true;
		for (Participant participant : participants) {
			for (Hand hand : participant.getHands()) {
				HandPlayState handPlayState = hand.getHandPlayState();
				if (HandPlayState.Hit.equals(handPlayState)) {
					calcRoundOver = false;
				}
			}
		}
		return calcRoundOver;
	}

	/**
	 * force Hit/Stay according to rules for Dealer
	 */
	protected void forceDealerHand() {

		ParticipantDealer dealer = getDealer();
		int dealerTotal = dealer.getCardTotal();
		Hand dealerHand = dealer.getActiveHand();
		if (HandPlayState.BlackJack.equals(dealerHand.getHandPlayState())
				|| HandPlayState.Bust.equals(dealerHand.getHandPlayState())
				|| HandPlayState.Stay.equals(dealerHand.getHandPlayState())) {
			// do nothing
		} else {
			if (dealerTotal < 17) {
				dealerHand.hit();
			} else if (dealerHand.isHasHighAce() && dealerTotal < 18) {
				dealerHand.hit();
			} else if (maxNoBustPlayerTotal > dealerTotal) {
				dealerHand.hit();
			} else {
				dealerHand.stay();
			}
		}
	}

	/**
	 * for each Hand: when not already Bust or BlackJack, get Player input or force
	 * Dealer Hit or Stay
	 */
	protected void getPlayerInput() {

		// get input from each Player for each Hand
		Participant[] players = getNormalPlayers();
		for (Participant participant : players) {
			for (Hand hand : participant.getHands()) {

				// ask if they want Hit or Stay for this Hand
				if (HandPlayState.BlackJack.equals(hand.getHandPlayState())
						|| HandPlayState.Bust.equals(hand.getHandPlayState())
						|| HandPlayState.Stay.equals(hand.getHandPlayState())) {
				} else {
					String input = "Hit";
					// TODO: wait for input of Hit/Stay here
					if ("Hit".equals(input)) {
						hand.hit();
					} else if ("Stay".equals(input)) {
						hand.stay();
					}
				}
			}
			// check state of whether player wants to keep playing
			if (participant.isStopPlaying()) {
				playAgain = false;
			}
		}

	}

	protected void awardPoints() {
		// get Dealer total to compare to Player total(s) so points can be properly
		// awarded
		ParticipantDealer dealer = getDealer();
		int dealerTotal = dealer.getCardTotal();
		Hand dealerHand = dealer.getActiveHand();

		// calculate score for each Hand and award points to each Player
		Participant[] players = getNormalPlayers();
		for (Participant participant : players) {
			for (Hand hand : participant.getHands()) {
				int handTotal = hand.getTotal();
				// compare each Hand individually to Dealer's Hand and award points
				
				// A player win over the dealer is worth 1 point
				if (handTotal <= 21 && handTotal > dealerTotal) {
					participant.addGamePoints(1);
				}
				
				// A dealer win over a player is worth 1 point
				else if (dealerTotal <= 21 && handTotal > dealerTotal) {
					dealer.addGamePoints(1);
				}

				// A player and dealer each with two cards that total 21 are both awarded 1 point
				else if (dealerTotal == 21 && dealerHand.isHasHighAce() && handTotal == 21 && hand.isHasHighAce()) {
					dealer.addGamePoints(1);
					participant.addGamePoints(1);
				}

				// A player or dealer win with two cards that total 21 is worth 2 points
				else if (dealerTotal == 21 && dealerHand.isHasHighAce()) {
					dealer.addGamePoints(2);
				}
				else if (handTotal == 21 && hand.isHasHighAce()) {
					participant.addGamePoints(2);
				}

			}
		}
	}

	/**
	 * do final stuff, maybe show final points or something
	 */
	protected void wrapUpGame() {

	}

	/**
	 * convenience method for getting only the ParticipantDealer
	 * 
	 * @return ParticipantDealer
	 */
	public ParticipantDealer getDealer() {
		return (ParticipantDealer) participants[dealerParticipantIndex];
	}

	/**
	 * convenience method for getting only the ParticipantPlayer(s)
	 * 
	 * @return Participant[] of all participants but not the Dealer
	 */
	public Participant[] getNormalPlayers() {
		return removeParticipant(participants, dealerParticipantIndex);
	}

	/**
	 * remove a Participant from the given participants array. Use if a player drops
	 * out before game begins or maybe between rounds.
	 * 
	 * @param participants
	 * @param index
	 * @return Participant[] without Participant at the given index
	 */
	public static Participant[] removeParticipant(Participant[] participants, int index) {
		// If empty array or the index is not in array range return the original
		// participants
		if (participants == null || index < 0 || index >= participants.length) {
			return participants;
		}

		// new array
		Participant[] players = new Participant[participants.length - 1];

		// Copy participants from start until index
		System.arraycopy(participants, 0, players, 0, index);

		// Copy participants from index + 1 until end
		System.arraycopy(participants, index + 1, players, index, participants.length - index - 1);

		return players;
	}

	/**
	 * main while loop, logic could be copied and reused elsewhere
	 */
	public void main() {
		playAgain = true;
		while (playAgain) {
			initializeGame();
			dealFirstTwoCards();
			calculateHandValues();
			isRoundOver();

			//TODO: make asynchronous with state flags rather than a while loop
			while (!roundOver) {
				getPlayerInput();
				forceDealerHand();
				dealCards(true, true);
				calculateHandValues();
				turnCards(true);
				isRoundOver();
			}
			
			awardPoints();
			cleanupRound();
		}
		wrapUpGame();
	}

	
	
	// getters and setters

	public int getRoundsPlayed() {
		return roundsPlayed;
	}

	public void setRoundsPlayed(int roundsPlayed) {
		this.roundsPlayed = roundsPlayed;
	}

	public boolean isPlayAgain() {
		return playAgain;
	}

	public void setPlayAgain(boolean playAgain) {
		this.playAgain = playAgain;
	}
}
