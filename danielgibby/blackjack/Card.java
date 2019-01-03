package danielgibby.blackjack;

public class Card {
	public enum Suit {
		Hearts, Clubs, Diamonds, Spades
	}

	public final static int SUIT_HEARTS = 1; // reserve 0 for no value
	public final static int SUIT_CLUBS = 2;
	public final static int SUIT_DIAMONDS = 3;
	public final static int SUIT_SPADES = 4;
	public final static String FACE_ACE = "A";
	public final static String FACE_2 = "2";
	public final static String FACE_3 = "3";
	public final static String FACE_4 = "4";
	public final static String FACE_5 = "5";
	public final static String FACE_6 = "6";
	public final static String FACE_7 = "7";
	public final static String FACE_8 = "8";
	public final static String FACE_9 = "9";
	public final static String FACE_10 = "10";
	public final static String FACE_JACK = "J";
	public final static String FACE_QUEEN = "Q";
	public final static String FACE_KING = "K";
	
	private int suit; // persisted as an int, not the enums above
	private String face; // persisted as a String, not enums above
	private boolean isFaceUp;

	// constructors
	
	public Card() {
	}

	/**
	 * @param String face
	 * @param Card.Suit suit
	 */
	public Card(String face, Suit suit) {
		this.setFace(face);
		this.setSuit(suit);
		this.setFaceUp(false);
	}

	/**
	 * create Card for persisted variable values
	 * 
	 * @param String face
	 * @param int suit
	 */
	public Card(String face, int suit) {
		this.setFace(face);
		if (suit == SUIT_HEARTS || suit == SUIT_CLUBS || suit == SUIT_DIAMONDS || suit == SUIT_SPADES) {
			this.setSuit(suit);
		} else {
			throw new IllegalStateException("Bad value somehow for suit: " + suit);
		}
		this.setFaceUp(false);
	}
	
	
	
	// normal methods
	
	/**
	 * 
	 * @return full name of Card ie. 2 of Hearts 
	 */
	public String getFullName() {
		StringBuilder fullName = new StringBuilder();
		switch (this.face) {
		case FACE_ACE:
			fullName.append("Ace");
			break;
		case FACE_JACK:
			fullName.append("Jack");
			break;
		case FACE_QUEEN:
			fullName.append("Queen");
			break;
		case FACE_KING:
			fullName.append("King");
			break;
		case FACE_2:
		case FACE_3:
		case FACE_4:
		case FACE_5:
		case FACE_6:
		case FACE_7:
		case FACE_8:
		case FACE_9:
		case FACE_10:
			fullName.append(this.face);
			break;
		default:
			throw new IllegalStateException("Unsupported card face: "+this.face);
		}
		
		fullName.append(" of ");
		if (this.suit == SUIT_HEARTS) {
			fullName.append("Hearts");			
		}
		if (this.suit == SUIT_DIAMONDS) {
			fullName.append("Diamonds");			
		}
		if (this.suit == SUIT_CLUBS) {
			fullName.append("Clubs");			
		}
		if (this.suit == SUIT_SPADES) {
			fullName.append("Spades");			
		}

		return fullName.toString();
	}

	/**
	 * called BaseValue not FaceValue since Ace isn't for sure worth 1 or 11, 
	 * also this allows for sorting all in order, and not jumbling J,10,K,Q all as value of 10
	 * @return face value of card; return 11 for Ace even though it could be changed
	 */
	public int getSortValue() {
		int value = 0;
		switch (this.face) {
		case FACE_ACE:
			value = 1; break; // this could be changed later, but we'll know it's an Ace this way.
		case FACE_2:
			value = 2; break;
		case FACE_3:
			value = 3; break;
		case FACE_4:
			value = 4; break;
		case FACE_5:
			value = 5; break;
		case FACE_6:
			value = 6; break;
		case FACE_7:
			value = 7; break;
		case FACE_8:
			value = 8; break;
		case FACE_9:
			value = 9; break;
		case FACE_10:
			value = 10; break;
		case FACE_JACK:
			value = 100; break; // high values just for sorting, to make more obvious if method used wrong
		case FACE_QUEEN:
			value = 200; break;
		case FACE_KING:
			value = 300; break;
		default:
			throw new IllegalStateException("Unsupported card face: "+this.face);
		}
		return value;
	}
	

	
	// getters and setters

	public int getSuit() {
		return suit;
	}
	
	protected void setSuit(Suit suit) {
		if (suit == Suit.Hearts) {
			this.setSuit(SUIT_HEARTS);
		} else if (suit == Suit.Clubs) {
			this.setSuit(SUIT_CLUBS);
		} else if (suit == Suit.Diamonds) {
			this.setSuit(SUIT_DIAMONDS);
		} else if (suit == Suit.Spades) {
			this.setSuit(SUIT_SPADES);
		} else {
			throw new IllegalStateException("Bad value somehow for suit: " + suit);
		}
	}
	
	public void setSuit(int suit) {
		if (SUIT_HEARTS == suit || SUIT_CLUBS == suit || SUIT_DIAMONDS == suit || SUIT_SPADES == suit) {
			this.suit = suit;
		} else {
			throw new IllegalStateException("Bad value somehow for suit: " + suit);
		}
	}

	public String getFace() {
		return face;
	}
	
	public void setFace(String face) {
		this.face = face;
	}

	public boolean isFaceUp() {
		return isFaceUp;
	}
	
	public void setFaceUp(boolean isFaceUp) {
		this.isFaceUp = isFaceUp;
	}
	
}
