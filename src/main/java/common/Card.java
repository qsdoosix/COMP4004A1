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
		s+=number;
		return s;
	}

	@Override
	public int compareTo(Card c) {
		if(this.number>c.number) {
			return 1;
		}else if(this.number<c.number) {
			return -1;
		}else {
			String s = "CHSD";
			if(s.indexOf(this.color)<s.indexOf(c.color)) {
				return 1;//So the color of this card is bigger than the compared one;
			}else if(s.indexOf(this.color)>s.indexOf(c.color)) {
				return -1;
			}else {
				return 0;//Two same cards.
			}
		}
	}
	
	public boolean equals(Card c) {
		return (this.number==c.number&&this.color==c.color);
	}
}
