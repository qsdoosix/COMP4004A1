import java.io.*;

public class Poker {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File file = new File("src/main/resources/Cards1.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       System.out.println(line);
		    }
		}

	}

}
