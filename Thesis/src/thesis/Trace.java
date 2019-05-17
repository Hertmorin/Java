package thesis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Trace {

	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter("Output.txt"));
		Memory memory = new Memory(writer);
		File file = new File("myTool.out");
		Scanner sc = new Scanner(file);

		while (sc.hasNextLine()) {
			StringTokenizer st = new StringTokenizer(sc.nextLine(), " ");
			memory.process(new Operation(st.nextToken(), st.nextToken().substring(2), st.nextToken().charAt(0),
					Integer.parseInt(st.nextToken())));
		}

		System.out.println("\nL1");
		System.out.println(memory.getL1_Access());
		System.out.println(memory.getL1_Hits());
		System.out.println(memory.getL1_Misses());
		System.out.println("L2");
		System.out.println(memory.getL2_Access());
		System.out.println(memory.getL2_Hits());
		System.out.println(memory.getL2_Misses());
	
		writer.write("L1 Rates:\nTotal Access: " + String.format("%.0f", memory.getL1_Access()) + "\nTotal Hits: " + String.format("%.0f", memory.getL1_Hits()) + " ("
				+ String.format("%.2f", (memory.getL1_Hits() / memory.getL1_Access() * 100)) + "%)\nTotal Miss: " + String.format("%.0f", memory.getL1_Misses()) + " ("
				+ String.format("%.2f", (memory.getL1_Misses() / memory.getL1_Access() * 100)) + "%)\n" );
		writer.write("L2 Rates:\nTotal Access: " + String.format("%.0f", memory.getL2_Access()) + "\nTotal Hits: " + String.format("%.0f", memory.getL2_Hits()) + " ("
				+ String.format("%.2f", (memory.getL2_Hits() / memory.getL2_Access() * 100)) + "%)\nTotal Miss: " + String.format("%.0f", memory.getL2_Misses()) + " ("
				+ String.format("%.2f", (memory.getL2_Misses() / memory.getL2_Access() * 100)) + "%)" );


		writer.close();
		sc.close();
	}
}
