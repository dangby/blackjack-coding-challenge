package danielgibby.blackjack;


/**
 * ActorPlayer can choose whether to hit/stay
 * 
 * @author dgibby
 *
 */
public class ParticipantPlayer extends Participant {
	
	public ParticipantPlayer() {
		super();
		super.makePlayer();
	}
	
	public ParticipantPlayer(int id) {
		this();
		this.setId(id);
	}
	
	/**
	 * ParticipantPlayer asks for player input -
	 *  implements abstract Participant method
	 */
	public void hitOrStay() {
		getPlayerInput();
	}
	
	
	/**
	 * for each Hand: when not already Bust or BlackJack, get Player input or force
	 * Dealer Hit or Stay
	 */
	protected void getPlayerInput() {

		// get input from each Player for each Hand
		for (Hand hand : this.getHands()) {

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
	}
	
	
}
