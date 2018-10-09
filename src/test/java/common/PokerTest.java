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
		Card[] er1 = {new Card('C',1),new Card('D',2),new Card('C',5),new Card('H',5),new Card('S',5)};
		for(int i = 0; i < er1.length;i++) {
			assertEquals(in1[i].number,er1[i].number);
			assertEquals(in1[i].color,er1[i].color);
		}
	}
	public void testRoyalflush() {
		Poker test = new Poker();
		//Case 1, ordered case
		test.enemy_hand[0] = new Card('H',10);
		test.enemy_hand[1] = new Card('H',11);
		test.enemy_hand[2] = new Card('H',12);
		test.enemy_hand[3] = new Card('H',13);
		test.enemy_hand[4] = new Card('H',1);
		boolean rf1 = test.isRoyalFlush(test.enemy_hand);
		assertEquals(rf1,true);
		
		//Case 2, reverse ordered case
		test.enemy_hand[0] = new Card('D',1);
		test.enemy_hand[1] = new Card('D',13);
		test.enemy_hand[2] = new Card('D',12);
		test.enemy_hand[3] = new Card('D',11);
		test.enemy_hand[4] = new Card('D',10);
		boolean rf2 = test.isRoyalFlush(test.enemy_hand);
		assertEquals(rf2,true);
		
		//Case 3, mix ordered case
		test.enemy_hand[0] = new Card('S',13);
		test.enemy_hand[1] = new Card('S',11);
		test.enemy_hand[2] = new Card('S',1);
		test.enemy_hand[3] = new Card('S',12);
		test.enemy_hand[4] = new Card('S',10);
		boolean rf3 = test.isRoyalFlush(test.enemy_hand);
		assertEquals(rf3,true);

		//Case 4, mix ordered case 2
		test.enemy_hand[0] = new Card('C',11);
		test.enemy_hand[1] = new Card('C',13);
		test.enemy_hand[2] = new Card('C',10);
		test.enemy_hand[3] = new Card('C',1);
		test.enemy_hand[4] = new Card('C',12);
		boolean rf4 = test.isRoyalFlush(test.enemy_hand);
		assertEquals(rf4,true);
		
		//Case 5, Not royal flush case because of color (but it is a straight)
		test.enemy_hand[0] = new Card('S',11);
		test.enemy_hand[1] = new Card('S',13);
		test.enemy_hand[2] = new Card('C',10);
		test.enemy_hand[3] = new Card('S',1);
		test.enemy_hand[4] = new Card('S',12);
		boolean rf5 = test.isRoyalFlush(test.enemy_hand);
		assertEquals(rf5,false);
		
		//Case 6, Not royal flush case because of number (but it is a straight flush)
		test.enemy_hand[0] = new Card('S',11);
		test.enemy_hand[1] = new Card('S',13);
		test.enemy_hand[2] = new Card('S',10);
		test.enemy_hand[3] = new Card('S',9);
		test.enemy_hand[4] = new Card('S',12);
		boolean rf6 = test.isRoyalFlush(test.enemy_hand);
		assertEquals(rf6,false);
	}
	public void testStraightFlush() {
		Poker test = new Poker();
		
		//Case 1, a straight flush with random order
		test.enemy_hand[0] = new Card('S',11);
		test.enemy_hand[1] = new Card('S',13);
		test.enemy_hand[2] = new Card('S',10);
		test.enemy_hand[3] = new Card('S',9);
		test.enemy_hand[4] = new Card('S',12);
		boolean rf1 = test.isStraightFlush(test.enemy_hand);
		assertEquals(rf1,true);

		//Case 2, a straight flush with normal order
		test.enemy_hand[0] = new Card('D',3);
		test.enemy_hand[1] = new Card('D',4);
		test.enemy_hand[2] = new Card('D',5);
		test.enemy_hand[3] = new Card('D',6);
		test.enemy_hand[4] = new Card('D',7);
		boolean rf2 = test.isStraightFlush(test.enemy_hand);
		assertEquals(rf2,true);

		//Case 3, a straight flush with reverse order
		test.enemy_hand[0] = new Card('C',13);
		test.enemy_hand[1] = new Card('C',12);
		test.enemy_hand[2] = new Card('C',11);
		test.enemy_hand[3] = new Card('C',10);
		test.enemy_hand[4] = new Card('C',9);
		boolean rf3 = test.isStraightFlush(test.enemy_hand);
		assertEquals(rf3,true);

		//Case 4, a straight flush with random order
		test.enemy_hand[0] = new Card('H',9);
		test.enemy_hand[1] = new Card('H',5);
		test.enemy_hand[2] = new Card('H',7);
		test.enemy_hand[3] = new Card('H',6);
		test.enemy_hand[4] = new Card('H',8);
		boolean rf4 = test.isStraightFlush(test.enemy_hand);
		assertEquals(rf4,true);
		

		//Case 5, Not a straight flush (but is a straight)
		test.enemy_hand[0] = new Card('S',9);
		test.enemy_hand[1] = new Card('H',5);
		test.enemy_hand[2] = new Card('H',7);
		test.enemy_hand[3] = new Card('H',6);
		test.enemy_hand[4] = new Card('H',8);
		boolean rf5 = test.isStraightFlush(test.enemy_hand);
		assertEquals(rf5,false);

		//Case 6, Not a straight flush (but is a straight)
		test.enemy_hand[0] = new Card('H',2);
		test.enemy_hand[1] = new Card('H',3);
		test.enemy_hand[2] = new Card('S',4);
		test.enemy_hand[3] = new Card('H',5);
		test.enemy_hand[4] = new Card('H',6);
		boolean rf6 = test.isStraightFlush(test.enemy_hand);
		assertEquals(rf6,false);
		
		//Case 6, Not a straight flush (but with same color)
		test.enemy_hand[0] = new Card('H',9);
		test.enemy_hand[1] = new Card('H',8);
		test.enemy_hand[2] = new Card('H',6);
		test.enemy_hand[3] = new Card('H',6);
		test.enemy_hand[4] = new Card('H',5);
		boolean rf7 = test.isStraightFlush(test.enemy_hand);
		assertEquals(rf7,false);
	}
	public void testFlush() {
		Poker test = new Poker();
		//Case 1, Is a flush
		test.enemy_hand[0] = new Card('H',6);
		test.enemy_hand[1] = new Card('H',11);
		test.enemy_hand[2] = new Card('H',12);
		test.enemy_hand[3] = new Card('H',3);
		test.enemy_hand[4] = new Card('H',1);
		boolean rf1 = test.isFlush(test.enemy_hand);
		assertEquals(rf1,true);
		
		//Case 2, Is a flush with other color
		test.enemy_hand[0] = new Card('D',2);
		test.enemy_hand[1] = new Card('D',6);
		test.enemy_hand[2] = new Card('D',4);
		test.enemy_hand[3] = new Card('D',5);
		test.enemy_hand[4] = new Card('D',9);
		boolean rf2 = test.isFlush(test.enemy_hand);
		assertEquals(rf2,true);

		//Case 3, Not a flush
		test.enemy_hand[0] = new Card('D',1);
		test.enemy_hand[1] = new Card('C',5);
		test.enemy_hand[2] = new Card('S',7);
		test.enemy_hand[3] = new Card('H',8);
		test.enemy_hand[4] = new Card('D',13);
		boolean rf3 = test.isFlush(test.enemy_hand);
		assertEquals(rf3,false);
		

		//Case 4, Is a flush with other color
		test.enemy_hand[0] = new Card('S',1);
		test.enemy_hand[1] = new Card('S',2);
		test.enemy_hand[2] = new Card('S',3);
		test.enemy_hand[3] = new Card('S',4);
		test.enemy_hand[4] = new Card('S',5);
		boolean rf4 = test.isFlush(test.enemy_hand);
		assertEquals(rf4,true);

		//Case 5, Is a flush with other color
		test.enemy_hand[0] = new Card('C',6);
		test.enemy_hand[1] = new Card('C',7);
		test.enemy_hand[2] = new Card('C',8);
		test.enemy_hand[3] = new Card('C',9);
		test.enemy_hand[4] = new Card('C',10);
		boolean rf5 = test.isFlush(test.enemy_hand);
		assertEquals(rf5,true);
	}
	public void test4oK() {
		Poker test = new Poker();
		//Case 1, Is a 4 of a kind with one other card at start
		test.enemy_hand[0] = new Card('H',6);
		test.enemy_hand[1] = new Card('C',11);
		test.enemy_hand[2] = new Card('S',11);
		test.enemy_hand[3] = new Card('D',11);
		test.enemy_hand[4] = new Card('H',11);
		boolean rf1 = test.is4oK(test.enemy_hand);
		assertEquals(rf1,true);

		//Case 2, Is a 4 of a kind with one other card at middle
		test.enemy_hand[0] = new Card('H',3);
		test.enemy_hand[1] = new Card('C',3);
		test.enemy_hand[2] = new Card('S',11);
		test.enemy_hand[3] = new Card('S',3);
		test.enemy_hand[4] = new Card('D',3);
		boolean rf2 = test.is4oK(test.enemy_hand);
		assertEquals(rf2,true);

		//Case 3, Is a 4 of a kind with one other card at end
		test.enemy_hand[0] = new Card('H',4);
		test.enemy_hand[1] = new Card('C',4);
		test.enemy_hand[2] = new Card('D',4);
		test.enemy_hand[3] = new Card('S',4);
		test.enemy_hand[4] = new Card('D',5);
		boolean rf3 = test.is4oK(test.enemy_hand);
		assertEquals(rf3,true);
		
		//Case 4, one card away from 4 of a kind
		test.enemy_hand[0] = new Card('H',5);
		test.enemy_hand[1] = new Card('C',5);
		test.enemy_hand[2] = new Card('D',8);
		test.enemy_hand[3] = new Card('S',7);
		test.enemy_hand[4] = new Card('D',5);
		boolean rf4 = test.is4oK(test.enemy_hand);
		assertEquals(rf4,false);
		
		//Case 5, 2 cards away from 4 of a kind
		test.enemy_hand[0] = new Card('H',2);
		test.enemy_hand[1] = new Card('C',2);
		test.enemy_hand[2] = new Card('D',8);
		test.enemy_hand[3] = new Card('S',7);
		test.enemy_hand[4] = new Card('D',11);
		boolean rf5 = test.is4oK(test.enemy_hand);
		assertEquals(rf5,false);
	}
	public void testFullHouse() {
		Poker test = new Poker();
		//Case 1, Is a FullHouse ordered 3 and then 2
		test.enemy_hand[0] = new Card('H',6);
		test.enemy_hand[1] = new Card('D',6);
		test.enemy_hand[2] = new Card('S',6);
		test.enemy_hand[3] = new Card('C',3);
		test.enemy_hand[4] = new Card('H',3);
		boolean rf1 = test.isFHouse(test.enemy_hand);
		assertEquals(rf1,true);
		
		//Case 2, Is a FullHouse but not ordered
		test.enemy_hand[0] = new Card('C',1);
		test.enemy_hand[1] = new Card('S',13);
		test.enemy_hand[2] = new Card('D',1);
		test.enemy_hand[3] = new Card('H',1);
		test.enemy_hand[4] = new Card('C',13);
		boolean rf2 = test.isFHouse(test.enemy_hand);
		assertEquals(rf2,true);
		
		//Case 3, Is a FullHouse ordered 2 and then 3
		test.enemy_hand[0] = new Card('H',2);
		test.enemy_hand[1] = new Card('C',2);
		test.enemy_hand[2] = new Card('H',12);
		test.enemy_hand[3] = new Card('S',12);
		test.enemy_hand[4] = new Card('D',12);
		boolean rf3 = test.isFHouse(test.enemy_hand);
		assertEquals(rf3,true);
		
		//Case 4, Not a full house
		test.enemy_hand[0] = new Card('D',1);
		test.enemy_hand[1] = new Card('H',3);
		test.enemy_hand[2] = new Card('C',2);
		test.enemy_hand[3] = new Card('C',11);
		test.enemy_hand[4] = new Card('S',12);
		boolean rf4 = test.isFHouse(test.enemy_hand);
		assertEquals(rf4,false);
		
		//Case 5, Is a FullHouse but in different order
		test.enemy_hand[0] = new Card('C',13);
		test.enemy_hand[1] = new Card('S',1);
		test.enemy_hand[2] = new Card('D',13);
		test.enemy_hand[3] = new Card('H',1);
		test.enemy_hand[4] = new Card('C',13);
		boolean rf5 = test.isFHouse(test.enemy_hand);
		assertEquals(rf5,true);
		
		//Case 6, Is not a FullHouse with 1+2+2
		test.enemy_hand[0] = new Card('C',10);
		test.enemy_hand[1] = new Card('S',8);
		test.enemy_hand[2] = new Card('D',6);
		test.enemy_hand[3] = new Card('H',8);
		test.enemy_hand[4] = new Card('C',6);
		boolean rf6 = test.isFHouse(test.enemy_hand);
		assertEquals(rf6,false);
		
		//Case 7, Is not a FullHouse with 2+1+2
		test.enemy_hand[0] = new Card('C',8);
		test.enemy_hand[1] = new Card('S',8);
		test.enemy_hand[2] = new Card('D',6);
		test.enemy_hand[3] = new Card('H',4);
		test.enemy_hand[4] = new Card('C',4);
		boolean rf7 = test.isFHouse(test.enemy_hand);
		assertEquals(rf7,false);
		
		//Case 8, Is not a FullHouse with 2+2+1
		test.enemy_hand[0] = new Card('C',9);
		test.enemy_hand[1] = new Card('S',9);
		test.enemy_hand[2] = new Card('D',7);
		test.enemy_hand[3] = new Card('H',7);
		test.enemy_hand[4] = new Card('C',11);
		boolean rf8 = test.isFHouse(test.enemy_hand);
		assertEquals(rf8,false);
	}

	public void test3oK() {
		Poker test = new Poker();
		
		//Case 1:Not 3 of a kind because this is 4
		test.enemy_hand[0] = new Card('H',6);
		test.enemy_hand[1] = new Card('C',13);
		test.enemy_hand[2] = new Card('S',13);
		test.enemy_hand[3] = new Card('D',13);
		test.enemy_hand[4] = new Card('H',13);
		boolean rf1 = test.is3oK(test.enemy_hand);
		assertEquals(rf1,false);
		
		//Case 2:3 of a kind with 3 cards at beginning
		test.enemy_hand[0] = new Card('C',1);
		test.enemy_hand[1] = new Card('D',1);
		test.enemy_hand[2] = new Card('S',1);
		test.enemy_hand[3] = new Card('H',2);
		test.enemy_hand[4] = new Card('C',3);
		boolean rf2 = test.is3oK(test.enemy_hand);
		assertEquals(rf2,true);
		
		//Case 3:3 of a kind with 3 cards at middle
		test.enemy_hand[0] = new Card('D',4);
		test.enemy_hand[1] = new Card('S',5);
		test.enemy_hand[2] = new Card('H',5);
		test.enemy_hand[3] = new Card('C',5);
		test.enemy_hand[4] = new Card('D',6);
		boolean rf3 = test.is3oK(test.enemy_hand);
		assertEquals(rf3,true);
		
		//Case 4:3 of a kind with 3 cards at end
		test.enemy_hand[0] = new Card('H',7);
		test.enemy_hand[1] = new Card('C',8);
		test.enemy_hand[2] = new Card('D',9);
		test.enemy_hand[3] = new Card('H',9);
		test.enemy_hand[4] = new Card('S',9);
		boolean rf4 = test.is3oK(test.enemy_hand);
		assertEquals(rf4,true);
		
		//Case 5:3 of a kind with 3 cards separated
		//All numbers 1~13 are covered, all colors are covered
		test.enemy_hand[0] = new Card('S',10);
		test.enemy_hand[1] = new Card('H',11);
		test.enemy_hand[2] = new Card('C',10);
		test.enemy_hand[3] = new Card('S',12);
		test.enemy_hand[4] = new Card('H',10);
		boolean rf5 = test.is3oK(test.enemy_hand);
		assertEquals(rf5,true);
		
		//Case 6:3 of a kind with 3 cards separated 2+1
		test.enemy_hand[0] = new Card('S',10);
		test.enemy_hand[1] = new Card('H',10);
		test.enemy_hand[2] = new Card('C',11);
		test.enemy_hand[3] = new Card('S',12);
		test.enemy_hand[4] = new Card('H',10);
		boolean rf6 = test.is3oK(test.enemy_hand);
		assertEquals(rf6,true);
		
		//Case 7:Not 3 of a kind
		test.enemy_hand[0] = new Card('S',10);
		test.enemy_hand[1] = new Card('H',5);
		test.enemy_hand[2] = new Card('C',11);
		test.enemy_hand[3] = new Card('S',12);
		test.enemy_hand[4] = new Card('H',10);
		boolean rf7 = test.is3oK(test.enemy_hand);
		assertEquals(rf7,false);
	}
	public void test2Pairs() {
		Poker test = new Poker();
		
		//Case 1:Two pairs 2+2+1
		test.enemy_hand[0] = new Card('H',1);
		test.enemy_hand[1] = new Card('C',1);
		test.enemy_hand[2] = new Card('H',2);
		test.enemy_hand[3] = new Card('C',2);
		test.enemy_hand[4] = new Card('H',3);
		boolean rf1 = test.is2Pair(test.enemy_hand);
		assertEquals(rf1,true);
		
		//Case 2:Two pairs 2+1+2
		test.enemy_hand[0] = new Card('C',4);
		test.enemy_hand[1] = new Card('H',4);
		test.enemy_hand[2] = new Card('C',5);
		test.enemy_hand[3] = new Card('H',6);
		test.enemy_hand[4] = new Card('C',6);
		boolean rf2 = test.is2Pair(test.enemy_hand);
		assertEquals(rf2,true);
		
		//Case 3:Two pairs 2+2+1
		test.enemy_hand[0] = new Card('D',7);
		test.enemy_hand[1] = new Card('S',7);
		test.enemy_hand[2] = new Card('D',8);
		test.enemy_hand[3] = new Card('S',8);
		test.enemy_hand[4] = new Card('D',9);
		boolean rf3 = test.is2Pair(test.enemy_hand);
		assertEquals(rf3,true);
		
		//Case 4:Two pairs inserted in middle
		test.enemy_hand[0] = new Card('S',10);
		test.enemy_hand[1] = new Card('D',11);
		test.enemy_hand[2] = new Card('S',10);
		test.enemy_hand[3] = new Card('D',12);
		test.enemy_hand[4] = new Card('S',12);
		boolean rf4 = test.is2Pair(test.enemy_hand);
		assertEquals(rf4,true);

		//Case 5:Two pairs inserted in the other way
		//All colors and numbers are covered
		test.enemy_hand[0] = new Card('H',13);
		test.enemy_hand[1] = new Card('C',13);
		test.enemy_hand[2] = new Card('S',1);
		test.enemy_hand[3] = new Card('D',2);
		test.enemy_hand[4] = new Card('S',1);
		boolean rf5 = test.is2Pair(test.enemy_hand);
		assertEquals(rf5,true);

		//Case 6:Not two pairs but full house
		test.enemy_hand[0] = new Card('C',3);
		test.enemy_hand[1] = new Card('S',3);
		test.enemy_hand[2] = new Card('H',3);
		test.enemy_hand[3] = new Card('S',4);
		test.enemy_hand[4] = new Card('C',4);
		boolean rf6 = test.is2Pair(test.enemy_hand);
		assertEquals(rf6,false);

		//Case 7:Not two pairs but one pair
		test.enemy_hand[0] = new Card('H',5);
		test.enemy_hand[1] = new Card('D',5);
		test.enemy_hand[2] = new Card('C',6);
		test.enemy_hand[3] = new Card('S',7);
		test.enemy_hand[4] = new Card('S',8);
		boolean rf7 = test.is2Pair(test.enemy_hand);
		assertEquals(rf7,false);
	}
	public void testPair() {
		Poker test = new Poker();		
		//Case 1, a pair together at beginning
		test.enemy_hand[0] = new Card('C',1);
		test.enemy_hand[1] = new Card('S',1);
		test.enemy_hand[2] = new Card('S',2);
		test.enemy_hand[3] = new Card('S',3);
		test.enemy_hand[4] = new Card('S',4);
		boolean rf1 = test.isPair(test.enemy_hand);
		assertEquals(rf1,true);
		//Case 2, a pair together at middle
		test.enemy_hand[0] = new Card('H',5);
		test.enemy_hand[1] = new Card('H',6);
		test.enemy_hand[2] = new Card('D',6);
		test.enemy_hand[3] = new Card('H',7);
		test.enemy_hand[4] = new Card('H',8);
		boolean rf2 = test.isPair(test.enemy_hand);
		assertEquals(rf2,true);
		//Case 3, a pair together at end
		test.enemy_hand[0] = new Card('S',9);
		test.enemy_hand[1] = new Card('D',10);
		test.enemy_hand[2] = new Card('D',11);
		test.enemy_hand[3] = new Card('D',12);
		test.enemy_hand[4] = new Card('H',12);
		boolean rf3 = test.isPair(test.enemy_hand);
		assertEquals(rf3,true);
		
		//Case 4, a pair separated two ends;
		test.enemy_hand[0] = new Card('C',13);
		test.enemy_hand[1] = new Card('S',1);
		test.enemy_hand[2] = new Card('S',2);
		test.enemy_hand[3] = new Card('S',3);
		test.enemy_hand[4] = new Card('D',13);
		boolean rf4 = test.isPair(test.enemy_hand);
		assertEquals(rf4,true);
		

		//Case 5, a pair separated all in middle;
		test.enemy_hand[0] = new Card('S',4);
		test.enemy_hand[1] = new Card('D',5);
		test.enemy_hand[2] = new Card('D',6);
		test.enemy_hand[3] = new Card('C',5);
		test.enemy_hand[4] = new Card('H',7);
		boolean rf5 = test.isPair(test.enemy_hand);
		assertEquals(rf5,true);
		
		//Case 6, Not a pair;
		test.enemy_hand[0] = new Card('S',1);
		test.enemy_hand[1] = new Card('D',2);
		test.enemy_hand[2] = new Card('D',4);
		test.enemy_hand[3] = new Card('C',7);
		test.enemy_hand[4] = new Card('H',9);
		boolean rf6 = test.isPair(test.enemy_hand);
		assertEquals(rf6,false);
		
		//Case 7, 3 of a kind, not a pair;
		test.enemy_hand[0] = new Card('S',1);
		test.enemy_hand[1] = new Card('D',1);
		test.enemy_hand[2] = new Card('D',4);
		test.enemy_hand[3] = new Card('C',1);
		test.enemy_hand[4] = new Card('H',9);
		boolean rf7 = test.isPair(test.enemy_hand);
		assertEquals(rf7,false);
		
		//Case 8, 4 of a kind, not a pair;
		test.enemy_hand[0] = new Card('S',1);
		test.enemy_hand[1] = new Card('D',1);
		test.enemy_hand[2] = new Card('D',4);
		test.enemy_hand[3] = new Card('C',1);
		test.enemy_hand[4] = new Card('H',1);
		boolean rf8 = test.isPair(test.enemy_hand);
		assertEquals(rf8,false);
		
		//Case 9, full house, not a pair;
		test.enemy_hand[0] = new Card('S',1);
		test.enemy_hand[1] = new Card('D',1);
		test.enemy_hand[2] = new Card('D',5);
		test.enemy_hand[3] = new Card('C',5);
		test.enemy_hand[4] = new Card('H',1);
		boolean rf9 = test.isPair(test.enemy_hand);
		assertEquals(rf9,false);
		
		//Case 10, two pairs, not a pair;
		test.enemy_hand[0] = new Card('S',1);
		test.enemy_hand[1] = new Card('D',1);
		test.enemy_hand[2] = new Card('D',5);
		test.enemy_hand[3] = new Card('C',5);
		test.enemy_hand[4] = new Card('H',6);
		boolean rf10 = test.isPair(test.enemy_hand);
		assertEquals(rf10,false);
	}
	public void testStraight() {
		Poker test = new Poker();		
		//Case 1, a Royal flush with random order (it is still a straight)
		test.enemy_hand[0] = new Card('S',11);
		test.enemy_hand[1] = new Card('S',13);
		test.enemy_hand[2] = new Card('S',10);
		test.enemy_hand[3] = new Card('S',1);
		test.enemy_hand[4] = new Card('S',12);
		boolean rf1 = test.isStraight(test.enemy_hand);
		assertEquals(rf1,true);

		//Case 2, a straight flush with normal order
		test.enemy_hand[0] = new Card('D',3);
		test.enemy_hand[1] = new Card('D',4);
		test.enemy_hand[2] = new Card('D',5);
		test.enemy_hand[3] = new Card('D',6);
		test.enemy_hand[4] = new Card('D',7);
		boolean rf2 = test.isStraight(test.enemy_hand);
		assertEquals(rf2,true);

		//Case 3, a straight flush with reverse order
		test.enemy_hand[0] = new Card('C',13);
		test.enemy_hand[1] = new Card('C',12);
		test.enemy_hand[2] = new Card('C',11);
		test.enemy_hand[3] = new Card('C',10);
		test.enemy_hand[4] = new Card('C',9);
		boolean rf3 = test.isStraight(test.enemy_hand);
		assertEquals(rf3,true);

		//Case 4, not a straight with one card missing at end
		test.enemy_hand[0] = new Card('H',8);
		test.enemy_hand[1] = new Card('H',5);
		test.enemy_hand[2] = new Card('S',7);
		test.enemy_hand[3] = new Card('C',6);
		test.enemy_hand[4] = new Card('D',12);
		boolean rf4 = test.isStraight(test.enemy_hand);
		assertEquals(rf4,false);
		

		//Case 5, Not a straight flush (but is a straight)
		test.enemy_hand[0] = new Card('S',9);
		test.enemy_hand[1] = new Card('H',5);
		test.enemy_hand[2] = new Card('H',7);
		test.enemy_hand[3] = new Card('H',6);
		test.enemy_hand[4] = new Card('H',8);
		boolean rf5 = test.isStraight(test.enemy_hand);
		assertEquals(rf5,true);

		//Case 6, Not a straight flush (but is a straight)
		test.enemy_hand[0] = new Card('H',2);
		test.enemy_hand[1] = new Card('H',3);
		test.enemy_hand[2] = new Card('S',4);
		test.enemy_hand[3] = new Card('H',5);
		test.enemy_hand[4] = new Card('H',6);
		boolean rf6 = test.isStraight(test.enemy_hand);
		assertEquals(rf6,true);
		
		//Case 6, Not a straight flush (but with same color)
		test.enemy_hand[0] = new Card('H',9);
		test.enemy_hand[1] = new Card('H',8);
		test.enemy_hand[2] = new Card('H',6);
		test.enemy_hand[3] = new Card('H',6);
		test.enemy_hand[4] = new Card('H',5);
		boolean rf7 = test.isStraight(test.enemy_hand);
		assertEquals(rf7,false);
		
	}
	/*
	public void testAnalyse() {	
		Poker test = new Poker();
		//Case 1, one card from straight flush by the end
		test.enemy_hand[0] = new Card('H',2);
		test.enemy_hand[1] = new Card('H',3);
		test.enemy_hand[2] = new Card('H',4);
		test.enemy_hand[3] = new Card('H',5);
		test.enemy_hand[4] = new Card('S',9);
		//The return value is how much should we change, followed by the index to be changed. In this case it is change 1 card, the index is 4.
		int[] er1 = {1,4};
		int[] ar1 = test.Analyse(test.enemy_hand);
		for(int i = 0; i < er1.length;i++) {
			assertEquals(er1[i],ar1[i]);
		}		

		//Case 2, one card from straight flush in the middle
		test.enemy_hand[0] = new Card('H',2);
		test.enemy_hand[1] = new Card('D',2);
		test.enemy_hand[2] = new Card('H',3);
		test.enemy_hand[3] = new Card('H',4);
		test.enemy_hand[4] = new Card('H',6);
		int[] er2 = {1,3};
		int[] ar2 = test.Analyse(test.enemy_hand);
		for(int i = 0; i < er2.length;i++) {
			assertEquals(er2[i],ar2[i]);
		}		

		//Case 3, one card from Four of a kind 
		test.enemy_hand[0] = new Card('S',3);
		test.enemy_hand[1] = new Card('H',3);
		test.enemy_hand[2] = new Card('D',3);
		test.enemy_hand[3] = new Card('C',5);
		test.enemy_hand[4] = new Card('C',6);
		//Change two cards at index 1 and 3
		int[] er3 = {2,1,3};
		int[] ar3 = test.Analyse(test.enemy_hand);
		for(int i = 0; i < er3.length;i++) {
			assertEquals(er3[i],ar3[i]);
		}		
		

		//Case 4, one card from Full house with 3+1+1
		//I found this is not useful as it should be interpreted as "One card from 4 of a kind"
		//Because on the Poker website, 4 of a kind is better than full house.
		test.enemy_hand[0] = new Card('C',2);
		test.enemy_hand[1] = new Card('C',6);
		test.enemy_hand[2] = new Card('S',10);
		test.enemy_hand[3] = new Card('H',10);
		test.enemy_hand[4] = new Card('D',10);
		//Change the card at index 1 (Change 1 and 3 both get full house, but card at index 3 is bigger)
		int[] er4 = {1,1};
		int[] ar4 = test.Analyse(test.enemy_hand);
		for(int i = 0; i < er4.length;i++) {
			assertEquals(er4[i],ar4[i]);
		}
		
		
		//Case 5, one card from Full house with 2+2+1
		test.enemy_hand[0] = new Card('H',6);
		test.enemy_hand[1] = new Card('C',6);
		test.enemy_hand[2] = new Card('S',10);
		test.enemy_hand[3] = new Card('H',10);
		test.enemy_hand[4] = new Card('D',12);
		//Change the card at index 4
		int[] er5 = {1,4};
		int[] ar5 = test.Analyse(test.enemy_hand);
		for(int i = 0; i < er5.length;i++) {
			assertEquals(er5[i],ar5[i]);
		}		
		
		//Case 6, 3 card with same color and 3 cards with same number
		test.enemy_hand[0] = new Card('H',6);
		test.enemy_hand[1] = new Card('S',10);
		test.enemy_hand[2] = new Card('H',10);
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
		test.enemy_hand[0] = new Card('H',6);
		test.enemy_hand[1] = new Card('S',10);
		test.enemy_hand[2] = new Card('H',10);
		test.enemy_hand[3] = new Card('C',10);
		test.enemy_hand[4] = new Card('D',12);
		//Change the card at index 4
		int[] er7 = {2,1,4};
		int[] ar7 = test.Analyse(test.enemy_hand);
		for(int i = 0; i < er7.length;i++) {
			assertEquals(er7[i],ar7[i]);
		}	
		
		//Case 8, one pair
		test.enemy_hand[0] = new Card('S',2);
		test.enemy_hand[1] = new Card('C',4);
		test.enemy_hand[2] = new Card('H',6);
		test.enemy_hand[3] = new Card('H',12);
		test.enemy_hand[4] = new Card('D',12);
		//Change 3 cards at index 1, 2, and 3.
		int[] er8 = {3,0,1,2};
		int[] ar8 = test.Analyse(test.enemy_hand);
		for(int i = 0; i < er8.length;i++) {
			assertEquals(er8[i],ar8[i]);
		}	
		
		//Case 9, 5 not related cards
		test.enemy_hand[0] = new Card('S',2);
		test.enemy_hand[1] = new Card('C',4);
		test.enemy_hand[2] = new Card('H',6);
		test.enemy_hand[3] = new Card('H',10);
		test.enemy_hand[4] = new Card('D',12);
		//Change 3 cards at index 1, 2, and 3.
		int[] er9 = {3,1,2,3};
		int[] ar9 = test.Analyse(test.enemy_hand);
		for(int i = 0; i < er9.length;i++) {
			assertEquals(er9[i],ar9[i]);
		}	
		
		//Case 10, 5 cards with same color
		test.enemy_hand[0] = new Card('S',2);
		test.enemy_hand[1] = new Card('S',4);
		test.enemy_hand[2] = new Card('S',6);
		test.enemy_hand[3] = new Card('S',10);
		test.enemy_hand[4] = new Card('S',12);
		//Change 3 cards at index 1, 2, and 3.
		int[] er10 = {0};
		int[] ar10 = test.Analyse(test.enemy_hand);
		for(int i = 0; i < er10.length;i++) {
			assertEquals(er10[i],ar10[i]);
		}
		
		//Case 11, 4 cards with same number
		test.enemy_hand[0] = new Card('S',2);
		test.enemy_hand[1] = new Card('D',2);
		test.enemy_hand[2] = new Card('H',2);
		test.enemy_hand[3] = new Card('C',2);
		test.enemy_hand[4] = new Card('S',12);
		//Change 3 cards at index 1, 2, and 3.
		int[] er11 = {0};
		int[] ar11 = test.Analyse(test.enemy_hand);
		for(int i = 0; i < er11.length;i++) {
			assertEquals(er11[i],ar11[i]);
		}
		
		//Case 12, 5 cards in sequence (but not same color)
		test.enemy_hand[0] = new Card('S',4);
		test.enemy_hand[1] = new Card('D',5);
		test.enemy_hand[2] = new Card('C',6);
		test.enemy_hand[3] = new Card('D',7);
		test.enemy_hand[4] = new Card('H',8);
		//Change 3 cards at index 1, 2, and 3.
		int[] er12 = {0};
		int[] ar12 = test.Analyse(test.enemy_hand);
		for(int i = 0; i < er12.length;i++) {
			assertEquals(er12[i],ar12[i]);
		}
	}*/
	
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
