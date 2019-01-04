/**
 * 
 */
package danielgibby.blackjack;

/**
 * @author dgibby
 * 
 *         receives cards face down, each Hand has state for hit or hold,
 *         subclasses of Actor determine whether they will request hit/hold from
 *         user or determine it programmatically.
 */
public abstract class Participant {

	public final static String PARTICIPANT_TYPE_DEALER = "D";
	public final static String PARTICIPANT_TYPE_PLAYER = "P";
	
	private int id;
	private int gamePoints; // total of all rounds played during game
	private Hand[] hands;
	private static final int maxHandsPerPlayer = 4;
	private int activeHandIndex;
	private int totalParticipantHands = 0;
	private String participantType; // "D" or "P" as PARTICIPANT_TYPE_XXX above
	private boolean stopPlaying;


	// constructors
	
	public Participant() {
		initializeNewHand();
		this.setActiveHandIndex(0);
	}
	public Participant(int id) {
		this();
		this.id = id;
	}

	
	
	// normal methods
	
	/**
	 * determined by subclasses how to handle hit or stay
	 */
	abstract public void hitOrStay(); 

	/**
	 * create a new Hand and add it to the hands Array, then increment totalParticipantHands
	 */
	public void initializeNewHand() {
		if (hands == null) {
			hands = new Hand[maxHandsPerPlayer];
			
		}
		hands[totalParticipantHands++] = new Hand();
	}
	
	/**
	 * lets each Hand determine the value of an Ace set gameScore
	 * 
	 * @return int gameScore
	 */
	public int getCardTotal() {
		int total = 0;
		for (Hand hand : getHands()) {
			total += hand.getTotal();
		}
		return total;
	}

	/**
	 * @return total number of hands this Actor has this round of the game (will be
	 *         1 until we allow Hand splitting)
	 */
	public int getNumHands() {
		return getHands().length;
	}

	/**
	 * add a Card to the active Hand
	 * @param card
	 */
	public void giveCard(Card card) {
		if (activeHandIndex > -1 && activeHandIndex < getHands().length) {
			getHands()[activeHandIndex].addCard(card);
		} else {
			throw new IllegalStateException("Bad active hand value: " + activeHandIndex);
		}
	}

	
	
	// getters and setters

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getGamePoints() {
		return gamePoints;
	}
	
	public void addGamePoints(int gamePoints) {
		this.gamePoints += gamePoints;
	}
	
	public void setGamePoints(int gamePoints) {
		this.gamePoints = gamePoints;
	}

	public Hand getActiveHand() {
		return this.hands[this.activeHandIndex];
	}
	
	public Hand[] getHands() {
		return hands;
	}
	
	public void setHands(Hand[] hands) {
		this.hands = hands;
	}
	
	public int getActiveHandIndex() {
		return activeHandIndex;
	}
	
	public void setActiveHandIndex(int activeHand) {
		this.activeHandIndex = activeHand;
	}
	
	public String getParticipantType() {
		return participantType;
	}
	
	public void setParticipantType(String type) {
		if (PARTICIPANT_TYPE_DEALER.equals(type) || PARTICIPANT_TYPE_PLAYER.equals(type)) {
			this.participantType = type;
		} else {
			throw new IllegalStateException("Unsupported player type: " + type);
		}
	}

	protected void makeDealer() {
		this.participantType = PARTICIPANT_TYPE_DEALER;
	}
	
	protected void makePlayer() {
		this.participantType = PARTICIPANT_TYPE_PLAYER;
	}
	
	public boolean isStopPlaying() {
		return stopPlaying;
	}
	
	public void setStopPlaying(boolean stopPlaying) {
		this.stopPlaying = stopPlaying;
	}
	


}
