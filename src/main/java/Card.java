
public class Card {

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
}
