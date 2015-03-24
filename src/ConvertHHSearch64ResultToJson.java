import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertHHSearch64ResultToJson {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.println("Reading File from Java code");
		// Name of the file
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("hhsearch.json", "UTF-8");
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
			int index = 0;
			// Read file line by line and print on the console
			while ((line = bufferReader.readLine()) != null) {

				if (line.contains(">")) {
					index++;
					String output = "\"" + "protein" + index + "\":\n{\n";

					// get protein name
					String proteinName = line.substring(1, 7);
					// System.out.println(proteinName);
					output += "\t\"name\":\"" + proteinName + "\",\n";
					// get EValue
					line = bufferReader.readLine();
					String arrayScore[] = line.trim().split("\\s+");
					String arrayEValue[] = arrayScore[1].split("=");
					// System.out.println(arrayEValue[1]);
					output += "\t\"EValue\":" + arrayEValue[1] + ",\n";
					// get Q ss_pred part information
					line = bufferReader.readLine();
					line = bufferReader.readLine();
					// System.out.println(line);
					String Qss_pred[] = line.trim().split("\\s+");
					output += "\t\"querySSPred\":\"" + Qss_pred[2] + "\",\n";
					// get Q pdb target part information
					line = bufferReader.readLine();
					String Qpdb[] = line.split("\\s+");
					output += "\t\"queryStartFrom\":" + Qpdb[2] + ",\n";
					output += "\t\"queryPDB\":\"" + Qpdb[3] + "\",\n";
					output += "\t\"queryFinishAt\":" + Qpdb[4] + ",\n";
					// get Q Consensus
					line = bufferReader.readLine();
					String QConsensus[] = line.split("\\s+");
					output += "\t\"queryConsensus\":\"" + QConsensus[3] + "\",\n";
					// get alignment information
					line = bufferReader.readLine();
					output += "\t\"alignment\":\"" + line.trim() + "\",\n";

					// get T Consensus
					line = bufferReader.readLine();
					String TConsensus[] = line.split("\\s+");
					output += "\t\"targetStartFrom\":" + TConsensus[2] + ",\n";
					output += "\t\"targetConsensus\":\"" + TConsensus[3] + "\",\n";
					output += "\t\"targetFinishAt\":" + TConsensus[4] + ",\n";

					// get T
					line = bufferReader.readLine();
					String T[] = line.split("\\s+");
					output += "\t\"target\":\"" + T[3] + "\",\n";

					// get T ss_pred
					line = bufferReader.readLine();
					String Tss_pred[] = line.split("\\s+");
					output += "\t\"targetSSPred\":\"" + Tss_pred[2] + "\",\n";
					// get Confidence
					line = bufferReader.readLine();
					String Confidence[] = line.split("\\s+");
					output += "\t\"confidence\":\"" + Confidence[1] + "\"\n";

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
