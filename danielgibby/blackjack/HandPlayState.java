package danielgibby.blackjack;

/**
 * Used by Hand so each one can have its own state;
 * Not persisted, so an enum, not a class works fine here.
 * @author dgibby
 *
 */
public enum HandPlayState {
	Undecided, Hit, Stay, Bust, BlackJack
}
