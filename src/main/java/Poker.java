import java.io.*;

public class Poker {
	//Because the player won't change more than 4 cards at a time, so 18 cards should be enough in the worse case. 
	Card[] card_buffer=new Card[18];
	//The hand for both player
	Card[] player_hand= new Card[5];
	Card[] enemy_hand = new Card[5];
	
	public Poker(){
	}
	
	//Interpret the input line to cards and store into the card_buffer;
	public void readCard(String line) {
		//Split the input by comma
		String[] input = line.split(",");
		for(int i =0; i < input.length;i++) {
			//System.out.println(input[i]);
			int n;			
			if(input[i].charAt(1)=='A') {
				n=1;
			}else if(input[i].charAt(1)=='0') {
				n=10;
			}else if(input[i].charAt(1)=='J') {
				n=11;
			}else if(input[i].charAt(1)=='Q') {
				n=12;
			}else if(input[i].charAt(1)=='K') {
				n=13;
			}else {
				n=Character.getNumericValue(input[i].charAt(1));
			}
			card_buffer[i]=new Card(input[i].charAt(0),n);
		}
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
	}
}
