package danielgibby.blackjack;

/**
 * Used by Hand so each one can have its own state;
 * also the flag value for when a Player requests Hit or Stay from the dealer.
 * Not persisted, so an enum, not a class works fine here.
 * @author dgibby
 *
 */
public enum HandPlayState {
	Undecided, Hit, Stay, Bust, BlackJack
}
