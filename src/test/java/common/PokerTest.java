package common;

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
	public void testAnalyse() {	
		Poker test = new Poker();
		//Case 1, one card from straight flush
		int[] inc1 = {0,5,0,0};
		int[] inn1 = {1,1,0,1,1,1,0,0,0,0,0,0,0};
		//The return value is how much should we change, and the index to be changed. In this case it is chance 1 card, the index is 5.
		int[] er1 = {1,5};
	}
	//The method cardcount() is to count how many of each color of card is on hand.
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
