import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("2018510122 Tutku Ekin Bayrý");
		Graph graph = new Graph();
		try {
			File folder = new File("karate_club_network.txt");
			Scanner sc = new Scanner(folder);
			
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] words = line.split(" ");
				graph.addEdge(words[0], words[1]);
			}
			graph.calculateClosenessAndBetweenness("Zachary Karate Club Network");
			
			Graph graph2 = new Graph();
			File folder2 = new File("facebook_social_network.txt");
			Scanner sc2 = new Scanner(folder2);
		
			while(sc2.hasNextLine()) {
				String line2 = sc2.nextLine();
				String[] words2 = line2.split(" ");
				graph2.addEdge(words2[0], words2[1]);
			}
			System.out.println();
			graph2.calculateClosenessAndBetweenness("Facebook Social Network");
		}
		catch(Exception e){
			System.out.println("System cannot find the path. Check file name.");
		}
		
	}

}
