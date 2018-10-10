package common;


public class Card implements Comparable<Card> {

	char color;
	int number;
	
	public Card(char c, int n) {
		color=c;
		number=n;
	}
	
	public String toString() {
		String s="";
		if(color=='H') {
			s+="Heart ";
		}else if (color == 'S') {
			s+="Spade ";
		}else if (color == 'C') {
			s+="Club ";
		}else if (color == 'D') {
			s+="Diamond ";
		}
		if(number==1) {
			s+='A';
		}else if(number==11) {
			s+='J';
		}else if(number==12) {
			s+='Q';
		}else if(number==13) {
			s+='K';
		}else{
			s+=number;
		}
		return s;
	}

	@Override
	public int compareTo(Card c) {
		String s = "SHDC";//Spade, Heart, Diamond, Club
		if(this.number>c.number) {
			return 1;
		}else if(this.number<c.number) {
			return -1;
		}else {
			if(s.indexOf(this.color)<s.indexOf(c.color)) {
				return 1;//The color of this card is bigger than the compared one;
			}else if(s.indexOf(this.color)>s.indexOf(c.color)) {
				return -1;
			}else {
				return 0;//Two same cards.
			}
		}
	}
}
