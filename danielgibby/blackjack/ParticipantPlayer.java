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
	
}
