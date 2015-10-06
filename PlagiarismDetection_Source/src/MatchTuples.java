import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.List;
import java.util.Map;

/**
 * Entry point for matching tuples of two files to detect plagiarism
 * 
 * @author mayurcherukuri
 *
 */
public class MatchTuples {
	
	private final static Charset Encoding = StandardCharsets.UTF_8;
	private final static String INSTRUCTIONS_FILE_NAME = "Instructions.txt";
	private static int TUPLE_SIZE = 3;
	
	public static void main(String[] args) throws IOException {
		
		if(args.length < 3) {
			Path path = Paths.get(INSTRUCTIONS_FILE_NAME);
			try (Scanner scanner = new Scanner(path, Encoding.name())) {
				while (scanner.hasNextLine()) {
					System.out.println(scanner.nextLine());
				}
				System.out.println("\n");
			} catch (IOException e) {
				throw new IOException("Error while reading the instructions file!");
			}
		}
		
		if(args.length>3) {
			TUPLE_SIZE = Integer.parseInt(args[3]);
		}
		
		Map<String, List<String>> synonymsMap = CommonUtils.createSynonymsMap(args[0]);
		List<List<String>> tuplesInFile1 = Tuples.createTuples(args[1], TUPLE_SIZE);
		List<List<String>> tuplesInFile2 = Tuples.createTuples(args[2], TUPLE_SIZE);
		
		System.out.println(CommonUtils.percentOfTuplesMatched(tuplesInFile1, tuplesInFile2, synonymsMap)+"%");
	}
}
