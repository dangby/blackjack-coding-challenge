package danielgibby.blackjack;

public class ParticipantDealer extends Participant {

	public ParticipantDealer() {
		super();
		super.makeDealer();
	}
	
	public ParticipantDealer(int id) {
		this();
		this.setId(id);
	}
	
}
