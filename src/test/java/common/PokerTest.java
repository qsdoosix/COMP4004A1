package common;

import junit.framework.TestCase;

public class PokerTest extends TestCase {
	//The test case for reading the rank of hand.
	public void testRank() {	
		
	}
	//The method cardcount() is to count how many of each color of card is on hand.
	public void testCardcount() {
		PokerTest test = new PokerTest();
		//The first test is to test the case that all card input is Spade.
		//The input for first test
		String[] in1 = {"S2","S3","S4","S5","S6"};
		//The expected result for first test
		int[] er1 = {5,0,0,0};
		//assertEquals(er1,test.cardCount(in1));
		
		//The second test is to test the case that most card has different color
		String[] in2 = {"S5","H3","C4","D5","D6"};
		int[] er2 = {1,1,2,1};
		//assertEquals(er2,test.cardCount(in2));
	}
}
