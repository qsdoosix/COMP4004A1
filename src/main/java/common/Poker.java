package common;
import java.io.*;

public class Poker {
	//Because the player won't change more than 4 cards at a time, so 18 cards should be enough in the worse case. 
	Card[] card_buffer=new Card[18];
	//The hand for both player
	Card[] player_hand= new Card[5];
	Card[] enemy_hand = new Card[5];
	int card_index=0;
	int num_card=0;
	
	int[] cardnumbercount = new int[13];
	int[] cardcolorcount = new int[4];
	
	public Poker(){
	}
	
	//Interpret the input line to cards and store into the card_buffer
	public void readCard(String line) {
		//Split the input by comma
		String[] input = line.split(" ");
		for(int i =0; i < input.length;i++) {
			//System.out.println(input[i]);
			int n;
			if(input[i].charAt(1)=='A') {
				n=1;
			}else if(input[i].charAt(1)=='0') {//Use 0 to represent 10 to keep it simple
				n=10;
			}else if(input[i].charAt(1)=='J') {
				n=11;
			}else if(input[i].charAt(1)=='Q') {
				n=12;
			}else if(input[i].charAt(1)=='K') {
				n=13;
			}else {
				//In this code '1' and 'A' is both considered as Ace, as it won't cause any confusion.
				n=Character.getNumericValue(input[i].charAt(1));
			}
			card_buffer[i]=new Card(input[i].charAt(0),n);
			num_card++;
		}
	}
	
	public void initializegame() {
		//Give player 5 cards for AI to beat.
		for(int i = 0; i < 5;i++) {
			player_hand[i]=drawCard();
			}
		//Give AI 5 cards
		for(int i = 0; i < 5;i++) {
			enemy_hand[i]=drawCard();
			}
	}
	
	public Card drawCard() {
		card_index++;
		return card_buffer[card_index-1];
	}
	
	public void countCard(Card[] hand) {
		int[] ccc = {0,0,0,0};//The temporary array of color counter
		int[] cnc = {0,0,0,0,0,0,0,0,0,0,0,0,0};//The temporary array of number counter
		
		for(int i = 0; i < hand.length;i++) {
			switch(hand[i].color) {
				case 'C':{
					ccc[0]++;
					break;
				}
				case 'H':{
					ccc[1]++;
					break;
				}
				case 'S':{
					ccc[2]++;
					break;
				}
				case 'D':{
					ccc[3]++;
					break;
				}
			}
			cnc[hand[i].number-1]++;
		}
		cardnumbercount=cnc;
		cardcolorcount=ccc;
		for(int i = 0; i < cardnumbercount.length;i++) {
			System.out.print(cardnumbercount[i]+ ", ");
		}
		System.out.println();
		for(int i = 0; i < cardcolorcount.length;i++) {
			System.out.print(cardcolorcount[i]+ ", ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File file = new File("src/main/resources/Cards1.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
	    String line= br.readLine();
		if(line==null) {
			//Something went wrong
			System.out.println("Empty file");
			System.exit(-1);
		}
		Poker game = new Poker();
		game.readCard(line);
		game.drawCard();
	}

	public String Analyse(int[] inc, int[] inn) {
		return null;
	}
}
