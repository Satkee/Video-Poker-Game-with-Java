import java.util.ArrayList; //importing ArrayList class, which allows for resizable arrays
import java.util.Collections; //importing Collections class, which provides the shuffle method

public class Deck {
	
	//creating an empty array
	private java.util.ArrayList<Card> cards = new ArrayList<Card>();
	
	//creating the full (13*4) deck of cards
	public Deck() {
		for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 4; j++) {
				Card newCard = new Card(i, j);
				cards.add(newCard);
			}
		}
		//calling shuffle method to shuffle the cards after the deck's creation
		shuffle(); 
	}
	
	//defining the shuffle method to randomize the card order using the Collections class
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	//defining the drawing one card from the deck
	public Card drawCard() {		 
		Card card = cards.get(0);    //the drawn card is the first one in the deck
		cards.remove(0);             //the drawn card is removed from the deck
		return card;
	}
}
