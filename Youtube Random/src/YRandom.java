
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class YRandom {

	// Main Method
	public static void main(String[] args) throws IOException {

		Data comments = new Data("08eYH0gjjOg"); // Connect and Gather Data (Video ID)
		comments.loop(comments.getMin()); //Start Loop through pages (start Index)
		comments.printMe(); // Print results
		
		Rand rnd = new Rand(comments.getPeople()); //Creates a Rand Object
		rnd.rndGen(); // Generates a random number from 1 to the number of valid comments
		rnd.pickWinner();

	}

}
