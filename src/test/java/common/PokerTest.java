package common;

import java.io.IOException;

import junit.framework.TestCase;

public class PokerTest extends TestCase {
	//The test case for reading the rank of hand.
	public void testReadCard() {
		Poker test = new Poker();
		test.readCard("C1,HA,SJ,D0,C2,H5,S3,D4,C6,S7,D8,H9,HQ,DK");
		Card[] expected = new Card[18];
		expected[0]=new Card('C',1);
		expected[1]=new Card('H',1);
		expected[2]=new Card('S',11);
		expected[3]=new Card('D',10);
		expected[4]=new Card('C',2);
		expected[5]=new Card('H',5);
		expected[6]=new Card('S',3);
		expected[7]=new Card('D',4);
		expected[8]=new Card('C',6);
		expected[9]=new Card('S',7);
		expected[10]=new Card('D',8);
		expected[11]=new Card('H',9);
		expected[12]=new Card('H',12);
		expected[13]=new Card('D',13);
		//Here I read 14 cards, containing 4 colors and all 13 possible numbers (A and 1 are both tested)
		//So this test has covered all possible situations.
		for(int i = 0; i < test.num_card;i++) {
			assertEquals(expected[i].color,test.card_buffer[i].color);
			assertEquals(expected[i].number,test.card_buffer[i].number);
		}
	}
	public void testinitialize() {
		Poker test = new Poker();
		//There should be no card when the game is started
		assertEquals(test.num_card,0);
		try {
			test.initializegame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//There should be 52 cards in the buffer when the game is initialized.
		assertEquals(test.num_card,52);
		
		test.countCard(test.card_buffer);
		//There should be 13 cards for each color, 4 cards for each number
		int[] erc = {13,13,13,13};
		int[] ern = {4,4,4,4,4,4,4,4,4,4,4,4,4};
		for(int i = 0; i < erc.length;i++) {
			assertEquals(erc[i],test.cardcolorcount[i]);
		}
		for(int i = 0; i < ern.length;i++) {
			assertEquals(ern[i],test.cardnumbercount[i]);
		}
	}

	public void testSort() {
		Poker test = new Poker();
		Card[] in1 = new Card[5];
		in1[0]=new Card('H',5);
		in1[1]=new Card('S',5);
		in1[2]=new Card('C',1);
		in1[3]=new Card('D',2);
		in1[4]=new Card('C',5);
		//This test case contains A>B, B>A A=B but better in Color, A=B but lower in color. So it covers all cases.
		in1=test.sortArray(in1);
		Card[] er1 = {new Card('C',1),new Card('D',2),new Card('S',5),new Card('H',5),new Card('C',5)};
		for(int i = 0; i < er1.length;i++) {
			System.out.println(in1[i]+", "+er1[i]);
		}
		for(int i = 0; i < er1.length;i++) {
			assertEquals(in1[i].number,er1[i].number);
			assertEquals(in1[i].color,er1[i].color);
		}
	}
	
	public void testAnalyse() {	
		Poker test = new Poker();
		//Case 1, one card from straight flush by the end
		test.enemy_hand[0] = new Card('H',4);
		test.enemy_hand[1] = new Card('H',2);
		test.enemy_hand[2] = new Card('H',3);
		test.enemy_hand[3] = new Card('H',5);
		test.enemy_hand[4] = new Card('S',9);
		//The return value is how much should we change, followed by the index to be changed. In this case it is change 1 card, the index is 4.
		int[] er1 = {1,4};
		int[] ar1 = test.Analyse(test.enemy_hand);
		for(int i = 0; i < er1.length;i++) {
			assertEquals(er1[i],ar1[i]);
		}		

		//Case 2, one card from straight flush in the middle
		test.enemy_hand[0] = new Card('H',4);
		test.enemy_hand[1] = new Card('H',2);
		test.enemy_hand[2] = new Card('H',3);
		test.enemy_hand[3] = new Card('D',2);
		test.enemy_hand[4] = new Card('H',6);
		int[] er2 = {1,3};
		int[] ar2 = test.Analyse(test.enemy_hand);
		for(int i = 0; i < er2.length;i++) {
			assertEquals(er2[i],ar2[i]);
		}		

		//Case 3, one card from Four of a kind 
		test.enemy_hand[0] = new Card('H',3);
		test.enemy_hand[1] = new Card('C',5);
		test.enemy_hand[2] = new Card('S',3);
		test.enemy_hand[3] = new Card('C',6);
		test.enemy_hand[4] = new Card('D',3);
		//Change two cards at index 1 and 3
		int[] er3 = {2,1,3};
		int[] ar3 = test.Analyse(test.enemy_hand);
		for(int i = 0; i < er3.length;i++) {
			assertEquals(er3[i],ar3[i]);
		}		
		

		//Case 4, one card from Full house with 3+1+1
		test.enemy_hand[0] = new Card('H',10);
		test.enemy_hand[1] = new Card('C',2);
		test.enemy_hand[2] = new Card('S',10);
		test.enemy_hand[3] = new Card('C',6);
		test.enemy_hand[4] = new Card('D',10);
		//Change the card at index 1 (Change 1 and 3 both get full house, but card at index 3 is bigger)
		int[] er4 = {1,1};
		int[] ar4 = test.Analyse(test.enemy_hand);
		for(int i = 0; i < er4.length;i++) {
			assertEquals(er4[i],ar4[i]);
		}		
		
		//Case 5, one card from Full house with 2+2+1
		test.enemy_hand[0] = new Card('H',10);
		test.enemy_hand[1] = new Card('H',6);
		test.enemy_hand[2] = new Card('S',10);
		test.enemy_hand[3] = new Card('C',6);
		test.enemy_hand[4] = new Card('D',12);
		//Change the card at index 4
		int[] er5 = {1,4};
		int[] ar5 = test.Analyse(test.enemy_hand);
		for(int i = 0; i < er5.length;i++) {
			assertEquals(er5[i],ar5[i]);
		}		
		
		//Case 6, 3 card with same color and 3 cards with number 
		test.enemy_hand[0] = new Card('H',10);
		test.enemy_hand[1] = new Card('H',6);
		test.enemy_hand[2] = new Card('S',10);
		test.enemy_hand[3] = new Card('C',10);
		test.enemy_hand[4] = new Card('H',12);
		//Change the card at index 2 and 3
		//The color is more important by the assignment requirement
		//So the card with same number is changed
		int[] er6 = {2,2,3};
		int[] ar6 = test.Analyse(test.enemy_hand);
		for(int i = 0; i < er6.length;i++) {
			assertEquals(er6[i],ar6[i]);
		}	
		
		//Case 7, 3 card with same number
		test.enemy_hand[0] = new Card('H',10);
		test.enemy_hand[1] = new Card('H',6);
		test.enemy_hand[2] = new Card('S',10);
		test.enemy_hand[3] = new Card('C',10);
		test.enemy_hand[4] = new Card('D',12);
		//Change the card at index 4
		int[] er7 = {2,1,4};
		int[] ar7 = test.Analyse(test.enemy_hand);
		for(int i = 0; i < er7.length;i++) {
			assertEquals(er7[i],ar7[i]);
		}	
	}
	
	//The method cardcount() is to count how many of each color of card is in array.
	public void testCardcount() {

		Poker test = new Poker();
		//The first test is to test the case that all card input is Spade.
		//The input for first test
		Card[] in1 = {new Card('S',1),new Card('S',2),new Card('S',3),new Card('S',4),new Card('S',5)};
		//The expected result for first test
		//The numbers are Club, Heart, Spade, Diamond
		int[] erc1 = {0,0,5,0};//The result for color, which has 5 spades
		int[] ern1 = {1,1,1,1,1,0,0,0,0,0,0,0,0};//The result for number, which has one '1', one '2', one '3' and so on.
		//Count the card in the poker
		test.countCard(in1);
		
		//Test case 1, All cards are in same color
		for(int i = 0; i < erc1.length;i++) {
			assertEquals(erc1[i],test.cardcolorcount[i]);
		}
		for(int i = 0; i < ern1.length;i++) {
			assertEquals(ern1[i],test.cardnumbercount[i]);
		}
		
		//Test case 2, 4 cards with different color
		Card[] in2 = {new Card('S',1),new Card('H',1),new Card('D',1),new Card('C',1),new Card('S',10)};
		test.countCard(in2);
		int[] erc2 = {1,1,2,1};
		int[] ern2 = {4,0,0,0,0,0,0,0,0,1,0,0,0};
		for(int i = 0; i < erc2.length;i++) {
			assertEquals(erc2[i],test.cardcolorcount[i]);
		}
		for(int i = 0; i < ern2.length;i++) {
			assertEquals(ern2[i],test.cardnumbercount[i]);
		}
		

		//Test case 3, 2 cards with same color and 3 cards with same color
		Card[] in3 = {new Card('S',6),new Card('S',7),new Card('C',8),new Card('S',9),new Card('C',9)};
		test.countCard(in3);
		int[] erc3 = {2,0,3,0};
		int[] ern3 = {0,0,0,0,0,1,1,1,2,0,0,0,0};
		for(int i = 0; i < erc3.length;i++) {
			assertEquals(erc3[i],test.cardcolorcount[i]);
		}
		for(int i = 0; i < ern3.length;i++) {
			assertEquals(ern3[i],test.cardnumbercount[i]);
		}
	}
}
