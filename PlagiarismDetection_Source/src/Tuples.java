import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains tuple related methods
 * @author mayurcherukuri
 *
 */
public class Tuples {

	private final static Charset Encoding = StandardCharsets.UTF_8;
	
	/**
	 * Method to retrieve all the tuples given a file
	 * 
	 * @param inpFile
	 * @param tupleSize
	 * @return List of tuples
	 * @throws IOException
	 */
	public static List<List<String>> createTuples(String inpFile, int tupleSize) throws IOException {
		
		List<List<String>> tuples = new ArrayList<List<String>>();
		Path path = Paths.get(inpFile);
		try (Scanner scanner = new Scanner(path, Encoding.name())) {
			
			while(scanner.hasNextLine()) {
				
				String[] strings = scanner.nextLine().toLowerCase().split(" ");
				if(strings.length > tupleSize) {
					for(int i=0;i<(strings.length-tupleSize+1);i++) {
						List<String> tuple = new ArrayList<String>();
						for(int j=i;j<(i+tupleSize);j++) {
							tuple.add(strings[j]);
						}
						tuples.add(tuple);
					}
				}
			}
		} catch (IOException e) {
			throw new IOException("Error while reading from: "+inpFile);
		}
		return tuples;
	}
}
