import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class ConvertBlastResultToJson {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.println("Reading File from Java code");
		// Name of the file
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("blast.json", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		writer.println("{");
		String fileName = args[0];
		try {

			// Create object of FileReader
			FileReader inputFile = new FileReader(fileName);

			// Instantiate the BufferedReader Class
			BufferedReader bufferReader = new BufferedReader(inputFile);

			// Variable to hold the one line data
			String line;
			int index=0;
			// Read file line by line and print on the console
			while ((line = bufferReader.readLine()) != null) {
				
				if (line.contains(">")) {
					index++;
					String output = "\"" + "protein"+index + "\":\n{\n";
					
					// get protein name
					String proteinName = line.substring(1, 7);
					// System.out.println(proteinName);
					output += "\t\"name\":\"" + proteinName + "\",\n";
					// get protein length
					line = bufferReader.readLine();
					String arr[] = line.trim().split(" = ");
					// System.out.println(arr[1]);
					output += "\t\"length\":" + arr[1] + ",\n";
					// get EValue
					line = bufferReader.readLine();
					line = bufferReader.readLine();
					String arrayScore[] = line.trim().split(", ");
					String arrayEValue[] = arrayScore[1].split(" = ");
					// System.out.println(arrayEValue[1]);
					output += "\t\"EValue\":" + arrayEValue[1] + ",\n";
					// get the query part information
					line = bufferReader.readLine();
					line = bufferReader.readLine();
					line = bufferReader.readLine();
					// System.out.println(line);
					String query[] = line
							.split("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");

					// System.out.println("QueryStartFrom: "+
					// Integer.parseInt(query[1].trim()));
					output += "\t\"QueryStartFrom\":" + query[1] + ",\n";
					// System.out.println("Query part is: " + query[2].trim());
					output += "\t\"QueryPart\":\"" + query[2].trim() + "\",\n";
					// System.out.println("QueryFinishAt: "+
					// Integer.parseInt(query[3].trim()));
					output += "\t\"QueryFinishAt\":" + query[3] + ",\n";
					// get the alignment info
					line = bufferReader.readLine();
					// System.out.println("Alignment is: "+line.trim());
					output += "\t\"Alignment\":\"" + line.trim() + "\",\n";
					// get the subject part
					line = bufferReader.readLine();
					String subject[] = line
							.split("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");
					// System.out.println("SubjectStartFrom: " +
					// Integer.parseInt(subject[1].trim()));
					output += "\t\"SubjectStartFrom\":" + subject[1] + ",\n";
					// System.out.println("Subject part is: " +
					// subject[2].trim());
					output += "\t\"SubjectPart\":\"" + subject[2].trim()
							+ "\",\n";
					// System.out.println("SubjectFinishAt: "+
					// Integer.parseInt(subject[3].trim()));
					output += "\t\"SubjectFinishAt\":" + subject[3] + "\n";
					output += "},\n";
					writer.println(output);
				}

			}
			// Close the buffer reader
			bufferReader.close();
			writer.println("\"finish\":\"end\"");
			writer.println("}");
			writer.close();
		} catch (Exception e) {
			System.out.println("Error while reading file line by line:"
					+ e.getMessage());
		}

	}

}
