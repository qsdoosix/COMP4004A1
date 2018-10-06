import java.io.*;

public class Poker {
	//Because the player won't change more than 4 cards at a time, so 18 cards should be enough in the worse case. 
	Card[] card_buffer;
	//The hand for both player
	Card[] player_hand= new Card[5];
	Card[] enemy_hand = new Card[5];
	
	public Poker(){
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
		String[] input = line.split(",");
		for(int i =0; i < input.length;i++) {
			System.out.println(input[i]);
		}
	}

}
