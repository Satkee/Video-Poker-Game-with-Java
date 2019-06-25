
public class Start {
	
	private VideoPoker player;	//creating the player variable, which represent the actual poker player
	public static Deck deck;	//creating the deck variable, which represents the deck currently being played
								//static allows this specific (altered) deck to be used across the Starter class
	
	//initializing the Game
	public Start() {
		player = new VideoPoker();
		deck = new Deck();
	}
	
	//creating the game set up - drawing the first five cards and storing it in the player's hand array
	public void startGame() {
		for(int i = 0; i < 5; i++) {
			Card card = deck.drawCard();
			player.addCard(card);
		}
		
		//presenting the first 5 cards to the player
		System.out.println("Welcome to Video Poker! Your cards are the following:\n");
		System.out.println(player.toString());  
		
		//discard method removes unwanted card(s) if player decides to do so
		player.discard();	      	
		
		System.out.println(player.score());			
		
	}
}
