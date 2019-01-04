package danielgibby.blackjack;

public class ParticipantDealer extends Participant {

	private int maxNoBustPlayerTotal;

	public ParticipantDealer() {
		super();
		super.makeDealer();
	}
	
	public ParticipantDealer(int id) {
		this();
		this.setId(id);
	}
	
	/**
	 * ParticipantPlayer asks for player input
	 *  implements abstract Participant method
	 */
	@Override
	public void hitOrStay() {
		forceDealerHand();
	}
	
	/**
	 * force Hit/Stay according to rules for Dealer
	 */
	protected void forceDealerHand() {

		int dealerTotal = this.getCardTotal();
		Hand dealerHand = this.getActiveHand();
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

	protected int getMaxNoBustPlayerTotal() {
		return maxNoBustPlayerTotal;
	}

	protected void setMaxNoBustPlayerTotal(int maxNoBustPlayerTotal) {
		this.maxNoBustPlayerTotal = maxNoBustPlayerTotal;
	}


}
