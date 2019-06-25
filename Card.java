
public class Card implements Comparable<Card>{		//implementing the comparable interface to facilitate comparison of the cards later on
	
	private int value; //integers, since will be used as indices to refer to arrays' values
	private int suit;
	
	//defining values array
	private static final String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"}; 
	
	//defining suits array
	private static final String[] suits = {"♠", "♥", "♦", "♣"};
	
	//defining the card with a suit and a value
	public Card(int v, int s) {
		value = v;
		suit = s;
	}
	
	//method for getting a value of a card
	public int getValue() {
		return value;
	}
	
	//method for getting a suit of a card
	public int getSuit() {
		return suit;
	}
	
	//method for defining the card's face value: value + suit
	public String toString() {
		String facevalue = values[value] + suits[suit];
		return facevalue;
	}
	
	//method for comparing cards
	public int compareTo(Card other) {
		if(this.getValue() > other.getValue())
			return 1;
		else if(this.getValue() < other.getValue())
			return -1;
		else
			return 0;
	}
}