import java.util.ArrayList;
import java.util.Scanner; 


public class VideoPoker {
	
	//creating the player's cards array
	private ArrayList<Card> playerCards = new ArrayList<Card>();
	
	//accessing player's inputs
	private Scanner input = new Scanner(System.in);

	public VideoPoker () {
		
	}
	
	//method for adding a card to player's cards
	public void addCard(Card card) {
		playerCards.add(card);
	}
		
	//method for defining every card (face values) in the player's cards
	public String toString() {
		String facevalues = "";
		for(Card card : playerCards) {
			facevalues += card.toString() + " ";
		}
		return facevalues;
	}
		
	//method for discarding unwanted cards (player's choice)
	public void discard() {
		String choice;         //stores player's choice whether to remove any of the cards
		String discarded = ""; //stores player's input regarding which cards to remove
			
		System.out.println("Do you want to discard any of the given cards? Press y for yes, n for no");
		choice = input.next();
			
		//in case player mistypes, system asks again
		while(!choice.equals("y") && !choice.equals("n")) {
			System.out.println("Sorry, did not get you. Press y if you want to discard cards or n to check for prize");
			choice = input.next();
		}
			
		if(choice.equals("y")) {
			System.out.println("Please, indicate which cards would you like to discard (i.e. 25 discards second and fifth card).");
			discarded = input.next();
		}
		
		//calls the method to replace discarded cards with the new ones
		replace(discarded);
	}
		
	//method for replacing discarded cards with the new ones from the current deck
	private void replace(String discarded) {
		if(!discarded.equals("")) {
			for(int i = 0; i < discarded.length(); i++) {
				int discardedCard = Integer.parseInt(discarded.substring(i, i+1));
				Card card = Start.deck.drawCard();     //refers to the current deck
				playerCards.set(discardedCard-1, card);  //substitutes discarded card with the new one from the deck
			}
			
			System.out.println("Your final cards are:");
			System.out.println(toString());
		}
	}
		
	//method for getting the score of the final set of cards
	public String score() {
		for(int i = 0; i < 5; i++) {   //nested loops facilitates the correct sorting. Otherwise sorting might be incomplete	
			
			//loops through the player's cards, uses comparison method to compare cards and set them in ascending order
			for(int j = 0; j < 4; j++) {
				int compareCards = playerCards.get(j).compareTo(playerCards.get(j+1));
				if(compareCards > 0) {
					Card currentCard = playerCards.get(j);
					playerCards.set(j, playerCards.get(j+1));
					playerCards.set(j+1, currentCard);
				}
			}
		}
				
		//now the code should loop through the 5 cards and look for combinations
		//firstly, all combinations should be set to false
		boolean JacksOrBetter = false, TwoPairs = false, 
				ThreeOfAKind = false,
				straight = false, flush = false,
				FullHouse = false,
				FourOfAKind = false,
				StraightFlush = false,
				RoyalFlush = false,
				pair = false; //even though there is no payoff for a pair below jacks, this is usefull for evaluating other combinations
		
		//begin looping through the player's cards
		for(int k = 0; k < 4; k++) {
			
			//checking if there is only one pair
			if (playerCards.get(k).getValue() == playerCards.get(k+1).getValue()                  // looks for a pair
				&& (k-1<0||playerCards.get(k-1).getValue()!=playerCards.get(k).getValue())        // handles boundary conditions
				&& (k+2>4||playerCards.get(k+2).getValue()!=playerCards.get(k).getValue())) { 
				
				//checking whether there is already a pair in a loop.
				//if there is, we have detected another one, which results in two pairs combo.
				//if not, it jumps this if statement and assigns pair to true (first/only pair so far).
				if (pair) {
					TwoPairs = true; 
				}
	
				pair = true;
				
				//checking whether that pair is jacks or higher
				int pairValue = playerCards.get(k).getValue();       
				if(pairValue == 0 || pairValue >= 10) {     //0 represents A (ace), 10, 11 and 12 represent Jacks, Queens and Kings respectively
															//values' array indices
					JacksOrBetter = true;	
				}
			}
						
			//checking whether there is four of a kind combination
			if ((k<2) && playerCards.get(k).getValue() == playerCards.get(k+1).getValue()     //(k<2) checks for overflow
				&& playerCards.get(k+1).getValue() == playerCards.get(k+2).getValue() 
				&& playerCards.get(k+2).getValue() == playerCards.get(k+3).getValue()) {
				
				FourOfAKind = true;
			}	
			
			//similarly checking whether there is three of a kind combination
			if (!FourOfAKind && (k<3)
				&& playerCards.get(k).getValue() == playerCards.get(k+1).getValue() 
				&& playerCards.get(k+1).getValue() == playerCards.get(k+2).getValue()) {
						
				ThreeOfAKind = true;
			}
					
			//checking whether there is a straight
			//Ace can be a part of a top (AKQJ10) or a bottom (A2345) straight (first two lines checks this)
			if ((k<1) && (playerCards.get(k).getValue() == 0 && playerCards.get(k+4).getValue() == 12     
				|| playerCards.get(k).getValue() + 1 == playerCards.get(k+1).getValue())       
				&& playerCards.get(k+1).getValue() + 1 == playerCards.get(k+2).getValue()		//last three lines check 
				&& playerCards.get(k+2).getValue() + 1 == playerCards.get(k+3).getValue()		//for sequence needed to get a straight
				&& playerCards.get(k+3).getValue() + 1 == playerCards.get(k+4).getValue()) {
						
				straight = true;
			}
			
			//checking for flush and consequently for straight flush and royal flush
			if ((k<1) && playerCards.get(k).getSuit() == playerCards.get(k+1).getSuit()
				&& playerCards.get(k+1).getSuit() == playerCards.get(k+2).getSuit()
				&& playerCards.get(k+2).getSuit() == playerCards.get(k+3).getSuit()
				&& playerCards.get(k+3).getSuit() == playerCards.get(k+4).getSuit()) {
					
				flush = true;
						
				//straight flush is true if besides flush we also have a straight
				if (straight) {
							
					StraightFlush = true; 
				}	
				
				//royal flush is true if straight flush starts with ace and ends with king
				if (StraightFlush && (playerCards.get(0).getValue() == 0 && playerCards.get(4).getValue() == 12)){
							
					RoyalFlush = true;
				} 		
			}			
		}
			
		//checking whether there is a full house; which is true only if there is three of the kind and a single pair
		FullHouse = ThreeOfAKind & pair;
		
		//the combinations have different payoffs; only the highest one matters, therefore starting with the best hand.
			if (RoyalFlush) {
				return "Congratulations! You have the Royal Flush! Your prize is 800";
			}
			if (StraightFlush) {
				return "Congratulations! You have the Straight Flush! Your prize is 50";
			}
			if (FourOfAKind) {
				return "Congratulations! You have the Four of a Kind! Your prize is 25";
			}
			if (FullHouse) {
				return "Congratulations! You have the Full House! Your prize is 9";
			}
			if (flush) {
				return "Congratulations! You have the Flush! Your prize is 6 ";
			}
			if (straight) {
				return "Congratulations! You have the Straight! Your prize is 4";
			}
			if (ThreeOfAKind) {
				return "Congratulations! You have the Three of a Kind! Your prize is 3";
			}
			if (TwoPairs) {
				return "Congratulations! You have Two Pairs! Your prize is 2";
			}
			if (JacksOrBetter) {
				return "Congratulations! You have Jacks or better! Your prize is 1";
			}
			else {
				return "Sorry, You did not get any prize";
			}
			
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String cont = "y";                 //variable used to store players decision whether to continue playing
		while(cont.equals("y")) {          //loops until player decides to stop
			Start poker = new Start(); 
			poker.startGame(); 
			
			System.out.println("Would you like to play again? Press y for yes, n for no");
			cont = input.next(); 
			
			//in case player mistypes, systems asks again
			while(!cont.equals("y") && !cont.equals("n")) {
				System.out.println("Sorry, did not get you. Press y if you want to play again or n to stop");
				cont = input.next();
			}
		}
		
		input.close();
		System.out.println("Thank you for playing Video Poker!");
	}
}
