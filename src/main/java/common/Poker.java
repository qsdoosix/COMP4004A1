package common;
import java.io.*;

public class Poker {
	//Because the player won't change more than 4 cards at a time, so 18 cards should be enough in the worse case. 
	Card[] card_buffer=new Card[52];
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
	
	public void initializegame(String in) throws IOException {
		//Load cards from file
		File file = new File(in);
		BufferedReader br = new BufferedReader(new FileReader(file));
	    String line= br.readLine();
		if(line==null) {
			//Something went wrong
			System.out.println("Empty file");
			System.exit(-1);
		}
		readCard(line);
		br.close();
		nextRound();
	}
	
	public void nextRound() {
		if(card_index<num_card-13) {
			System.out.println("\n\nStarting new round");
			//Give player 5 cards for AI to beat.
			for(int i = 0; i < 5;i++) {
				player_hand[i]=drawCard();
				}
			player_hand=sortArray(player_hand);
			//Give AI 5 cards
			for(int i = 0; i < 5;i++) {
				enemy_hand[i]=drawCard();
				}
			enemy_hand=sortArray(enemy_hand);
			System.out.println("Displaying both player's hand after drawing the card");
			displayHand();
		}else {
			System.out.println("No enough cards in the deck to start new round");
		}
	}
	
	public Card drawCard() {
		card_index++;
		return card_buffer[card_index-1];
	}
	
	public Card[] sortArray(Card[] in) {
		//A helper method to sort the hands using bubble sort
		for(int i = 0; i<in.length-1; i++) {
			int r= in[i].compareTo(in[i+1]);
			if(r>0) {
				Card t = in[i];
				in[i]=in[i+1];
				in[i+1]=t;
				i=-1;
			}
		}
		return in;
	}
	public int[] sortArray(int[] in) {
		//A helper method to sort the hands using bubble sort
		for(int i = 0; i<in.length-1; i++) {
			if(in[i]<in[i+1]) {
				int t = in[i];
				in[i]=in[i+1];
				in[i+1]=t;
				i=-1;
			}
		}
		return in;
	}
	
	public void countCard(Card[] in) {
		for(int i = 0;i<cardcolorcount.length;i++) {
			cardcolorcount[i]=0;
		}
		for(int i = 0;i<cardnumbercount.length;i++) {
			cardnumbercount[i]=0;
		}
		String list = "SHDC";
		for(int i = 0; i < in.length;i++) {
			cardcolorcount[list.indexOf(in[i].color)]++;
			cardnumbercount[in[i].number-1]++;
		}
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Poker game = new Poker();
		game.initializegame("src/main/resources/Cards1.txt");
	}

	public int[] Analyse(Card[] in) {
		//To analyse the input card (Should be 5 sorted cards) and decide what to do (Change or not)
		return null;
	}
	public boolean isRoyalFlush(Card[] in) {
		// TODO Auto-generated method stub
		sortArray(in);
		countCard(in);
		sortArray(cardcolorcount);
		sortArray(cardnumbercount);
		if(cardcolorcount[0]<5) {
			//The 5 cards are not in same color, so it can't be a royal flush;
			return false;
		}		
		//Because input is sorted at the beginning, so it must be 1(A) 10,J,Q,K
		return  in[0].number==1&&
				in[1].number==10&&
				in[2].number==11&&
				in[3].number==12&&
				in[4].number==13;
	}
	public boolean isStraightFlush(Card[] in) {
		return isFlush(in)&&isStraight(in);
	}
	public boolean isStraight(Card[] in) {
		// TODO Auto-generated method stub
		in=sortArray(in);
		//The special case about card 'Ace'
		if( in[0].number==1&&
			in[1].number==10&&
			in[2].number==11&&
			in[3].number==12&&
			in[4].number==13) {
			return true;
		}
		if(in[0].number!=in[1].number&&in[1].number!=in[2].number&&in[2].number!=in[3].number&&in[3].number!=in[4].number) {
			//If 5 sorted card are different and the maximum card minus minimum card is 4, then it must be 5 cards in sequence.
			//Then the smallest card must not be 'A' (because A 2 3 4 5 is not a straight as far as i know)
			return (in[4].number-in[0].number==4&&in[0].number!=1);
		}
		return false;
	}
	public boolean isFlush(Card[] in) {
		// TODO Auto-generated method stub
		countCard(in);
		cardcolorcount=sortArray(cardcolorcount);
		return(cardcolorcount[0]==5);
	}
	public boolean is4oK(Card[] in) {
		countCard(in);
		sortArray(cardnumbercount);
		//If there are 4 cards with same number, then it must be 4 of a kind.
		return cardnumbercount[0]==4;
	}
	public boolean isFHouse(Card[] in) {
		countCard(in);
		sortArray(cardnumbercount);
		return(cardnumbercount[0]==3&&cardnumbercount[1]==2);
	}
	public boolean is3oK(Card[] in) {
		// TODO Auto-generated method stub
		countCard(in);
		sortArray(cardnumbercount);
		//If there are 3 cards with same number and the other two are not same, then it is 3 of a kind
		return cardnumbercount[0]==3&&cardnumbercount[1]<2;
	}

	public boolean is2Pair(Card[] in) {
		// TODO Auto-generated method stub
		countCard(in);
		sortArray(cardnumbercount);
		//If there are exactly 2 of 2 cards then it is two pairs
		return cardnumbercount[0]==2&&cardnumbercount[1]==2;
	}

	public boolean isPair(Card[] in) {
		// TODO Auto-generated method stub
		countCard(in);
		sortArray(cardnumbercount);
		//If there are exactly 2 of 2 cards then it is two pairs
		return cardnumbercount[0]==2&&cardnumbercount[1]<2;
	}

	public int onefromflush(Card[] in) {
		// TODO Auto-generated method stub
		int colorindex = -1;
		char[] colorlist= {'S','H','D','C'};
		countCard(in);
		for(int i=0;i<cardcolorcount.length;i++) {
			if(cardcolorcount[i]==4) {//This is the color have 4 cards in hand
				colorindex=i;
				break;
			}
		}
		if(colorindex<0) {
			return -1;
		}
		for(int i = 0; i < in.length;i++) {
			if(in[i].color!=colorlist[colorindex]) {
				return i;
			}
		}
		return -1;
	}
	public int onefromstraight(Card[] in) {
		int re = -1;
		//A free card to represent the "missing" card can be any card in sequence.
		boolean freeCard = true;
		for(int i = 0; i < in.length-1;i++) {
			if(in[i+1].number==in[i].number) {
				//Two cards with same number, mark the first one as free card.
				re=i;
			}else if(in[i+1].number-in[i].number==2) {
				//Two cards with a gap between them, try to find a free card to fix it.
				if(!freeCard) {
					return -1;
				}
				if(i==3) {
					//If it is already the last card, then it will be changed
					re=4;
				}
				freeCard=false;
			}else if(in[i+1].number-in[i].number>2) {
				//Two cards with a large gap between them (can't be fixed with one free card)
				//So it must be either the first card or the last card
				if(i==0) {
					//The First card will be changed
					re=0;
				}
				if(i==3) {
					//The last card will be changed
					re=4;
				}
			}
		}
		return re;
	}

	public int onefromFS(Card[] in) {
		// TODO Auto-generated method stub
		//Re-use the code one from flush and one from straight
		if(isStraightFlush(in)) {
			return -1;
		}
		int r1=onefromflush(in);
		int r2=onefromstraight(in);
		if(r1==r2) {
			//If changing a card can make it became flush and straight at same time, then it must be one card from straight flush.
			return r1;
		}else {
			//A special case that there may be two cards with same number but only one of them can be used for flush
			if(r1>-1&&r2>-1) {
				if(in[r1].number==in[r2].number) {
					return r1;
				}
			}
		}
		return -1;
	}

	public int[] oneFrom4oK(Card[] in) {
		// TODO Auto-generated method stub
		int[] wrong = {-1,-1};
		int ones=0;
		int three=0;
		int[] re= new int[2];
		countCard(in);
		for(int i = 0; i < cardnumbercount.length;i++) {
			if(cardnumbercount[i]==1) {
				if(ones==re.length) {
					//If there is more than 2 cards exist only once,then it is not one from 4 of a kind
					return wrong;
				}
				for(int a = 0; a < in.length;a++) {
					//Search for the card with same number as its index in counter (Because the index in counter is the number of card)
					if(i==in[a].number) {
						re[ones]=a+1;
					}
				}
				ones++;
			}else if(cardnumbercount[i]==3) {
				three++;
			}
		}
		if(ones==2&&three==1) {
			return re;
		}else {
			return wrong;
		}
	}

	public int onefromFHouse(Card[] in) {
		// TODO Auto-generated method stub
		//To make it one cards away from full house, it must have 2 pairs of cards and one single cards.
		//Because the 3 cards with same number should be interpreted as one card away from 4 of a kind.
		int pair=0;
		countCard(in);
		int re=-1;
		for(int i = 0; i < cardnumbercount.length;i++) {
			if(cardnumbercount[i]==2) {
				pair++;
			}else if(cardnumbercount[i]==1) {
				for(int a = 0; a < in.length;a++) {
					//Search for the card with same number as its index in counter (Because the index in counter is the number of card)
					if(i==in[a].number-1) {
						re=a;
					}
				}
			}
		}
		if(pair==2) {
			return re;
		}else {
			return -1;
		}
	}

	public int oneFromRFlush(Card[] in) {
		// TODO Auto-generated method stub
		countCard(in);
		int cardinRF=0;
		char maincolor='N';
		String list= "SHDC";
		for(int i = 0; i<cardcolorcount.length;i++) {
			if(cardcolorcount[i]>=4) {
				//If there is a color have 4 or more cards, then it is the color for the possible royal flush
				maincolor=list.charAt(i);
			}
		}
		if(maincolor=='N') {
			//There isn't a color have 4 or more cards. So it must be away from flush, so it must not be a royal flush
			return -1;
		}
		for(int i=0;i<5;i++) {
			if(cardnumbercount[(9+i)%13]>0) {
				//How many cards are there in the sequence.
				cardinRF+=cardnumbercount[(9+i)%13];
			}
		}
		if(cardinRF==5) {
			//There is one card duplicated in the royal flush. (e.g. have two '10's)
			//So search for the card not in the main color and change it
			for(int i=0;i<in.length;i++) {
				if(in[i].color!=maincolor) {
					return i;
				}
			}
		}else if(cardinRF==4) {
			//There is one card outside the royal flush.(e.g. have a '5')
			//So search for the card not in the royal flush sequence (10,J,Q,K,A).
			for(int i = 0; i < in.length;i++) {
				if( in[i].number!=1&&
					in[i].number!=10&&
					in[i].number!=11&&
					in[i].number!=12&&
					in[i].number!=13) {
					return i;
				}
			}
		}else {
			//There are less than 4 cards in the royal flush sequence, so it can't be a royal flush
			return -1;
		}
		return -1;
	}

	public int[] change3oS(Card[] in) {
		// TODO Auto-generated method stub
		countCard(in);
		char maincolor='N';
		int[] wrong = {-1,-1};
		int[] re= new int[2];
		int reindex=0;
		String list= "SHDC";
		for(int i = 0; i<cardcolorcount.length;i++) {
			if(cardcolorcount[i]==3) {
				//Look for the color with exactly 3 cards
				maincolor=list.charAt(i);
			}
		}
		if(maincolor=='N') {
			//There isn't a color have 3 cards. So it's not what we are looking for
			return wrong;
		}
		for(int i = 0;i<in.length;i++) {
			if(in[i].color!=maincolor) {
				//This card is not one of the 3 cards at same color
				if(reindex==re.length) {
					//Not supposed to work, but it prevents the error input
					return wrong;
				}
				//Save the index to the return value
				re[reindex]=i;
				reindex++;
			}
		}
		return re;
	}

	public int[] ThreeInSequence(Card[] in) {
		// TODO Auto-generated method stub
		//This one i don't want to take the case that the 3 cards are not adjacent.
		//(e.g) 3,5,6
		//Because this is very unlikely to get a better hand in this case.
		int[] wrong = {-1,-1};
		int[] re= {-1,-1};
		countCard(in);
		int cardindex=0;
		for(int i = cardnumbercount.length-1;i>2;i--) {
			if(cardnumbercount[i]>0&&cardnumbercount[i-1]>0&&cardnumbercount[i-2]>0) {
				//The largest 3 cards in a sequence
				if(i>2&&cardnumbercount[i-3]>0) {
					//There are 4 or more cards in sequence
					return wrong;
				}
				for(int a=0;a<in.length;a++) {
					if(in[a].number<i-2) {
						//If the card in input is smaller than the minimum card in sequence, then it should be changed.
						if(cardindex>2) return wrong;//If there is more than 2 cards need to be changed, return false
						re[cardindex]=a;
						cardindex++;
					}else if(in[a].number>i+1) {
						//if the card at index a is bigger than the maximum card in sequence, then it should be changed
						if(cardindex>2) return wrong;
						re[cardindex]=a;
						cardindex++;
					}else if(cardnumbercount[in[a].number-1]>1) {
						//If there is more card at same number as card at index a, then it should be changed
						if(cardindex>2) return wrong;
						re[cardindex]=a;
						cardindex++;
						a++;//Skip the next card (which is expected to be same as this one)
					}
				}
			}
		}
		
		return re;
	}
	public void displayHand() {
		player_hand=sortArray(player_hand);
		enemy_hand=sortArray(enemy_hand);
		System.out.println("Printing player's hand");
		for(Card c:player_hand) {
			System.out.print(c+", ");
		}
		System.out.println();
		System.out.println("Printing AIP's hand");
		for(Card c:enemy_hand) {
			System.out.print(c+", ");
		}
		System.out.println();
	}

	public void changeCard(boolean isEnemy, int[] in) {
		// TODO Auto-generated method stub
	}
}
