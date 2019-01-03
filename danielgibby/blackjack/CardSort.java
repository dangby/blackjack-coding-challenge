package danielgibby.blackjack;

import java.util.Comparator;

public class CardSort implements Comparator<Card> {

	@Override
	public int compare(Card card1, Card card2) {
		int value1 = card1.getSortValue();
		int value2 = card2.getSortValue();
		return value1 - value2;
	}

}
