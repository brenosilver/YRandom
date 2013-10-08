import java.util.ArrayList;
import java.util.Random;



public class Rand {
	
	private int number, limit;
	private Random rand;
	private ArrayList<People> people;
	private People winner;
	
	

	public Rand(ArrayList<People> people) {
		this.people = people;
		this.limit = people.size();
	}

	public int rndGen(){
		Random rand = new Random();
		this.number = rand.nextInt(limit)+1;
		System.out.println("random = " + this.number + " max size = " + this.limit);
		return this.number;
	}
	
	public People pickWinner(){
		winner = new People();
		winner = this.people.get(this.number - 1);
		System.out.println(winner);
		return winner;
	}
}
