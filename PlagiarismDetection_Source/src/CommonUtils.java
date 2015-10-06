import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains all utility functions required to 
 * @author mayurcherukuri
 *
 */
public class CommonUtils {
	
	//Useful Map to find if two words are synonyms are not.
	private final static Charset Encoding = StandardCharsets.UTF_8;
	Logger Log = Logger.getLogger(CommonUtils.class.getName());
	
	public static Map<String, List<String>> createSynonymsMap(String synsFile) throws IOException {
		
		Map<String, List<String>> synonymsMap = new HashMap<String, List<String>>();
		Path path = Paths.get(synsFile);
		try (Scanner scanner = new Scanner(path, Encoding.name())) {
			while(scanner.hasNextLine()) {
				String[] strings = scanner.nextLine().toLowerCase().split(" ");
				List<String> syns = Arrays.asList(strings);
				
				for(String string: strings) {
					if(synonymsMap.containsKey(string)){
						Logger.getLogger(CommonUtils.class.getName()).log(Level.WARNING, "Assuming a word cannot have two meaning and discarding such instances");
					}
					else
						synonymsMap.put(string, syns);
				}
			}
		} catch (IOException e) {
			throw new IOException("Error while reading the Synonyms file");
		}
		
		return synonymsMap;
	}
	
	public static double percentOfTuplesMatched(List<List<String>> tuples1, List<List<String>> tuples2, Map<String, List<String>> synonyms) {
		
		int matches = 0;
		
		for(List<String> tuple1: tuples1) {
			for(List<String> tuple2: tuples2) {
				
				if(isMatch(tuple1, tuple2, synonyms)) {
					matches++;
					break;
				}
			}
		}
		return ((100*matches)/tuples1.size());
	}
	
	private static boolean isMatch(List<String> tuple1, List<String> tuple2, Map<String, List<String>> synonyms) {
		
		if(tuple1.size() != tuple2.size())
			throw new IllegalArgumentException("Tuple sizes are different");
		
		for(int i=0;i<tuple1.size();i++) {
			String s1 = tuple1.get(i);
			String s2 = tuple2.get(i);
			
			if(!s1.equals(s2)) {
				
				if(synonyms.containsKey(s1) && synonyms.containsKey(s2)) {
					
					List<String> list1 = synonyms.get(s1);
					List<String> list2 = synonyms.get(s2);
					
					if(!list1.equals(list2))
						return false;
				} else {
					return false;
				}
			}
		}
		return true;
	}
}
