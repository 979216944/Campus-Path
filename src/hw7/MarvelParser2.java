package hw7;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Parser utility to load the Marvel Comics dataset.
 */
public class MarvelParser2 {
    /**
     * A checked exception class for bad data files
     */
    @SuppressWarnings("serial")
    public static class MalformedDataException extends Exception {
        public MalformedDataException() { }

        public MalformedDataException(String message) {
            super(message);
        }

        public MalformedDataException(Throwable cause) {
            super(cause);
        }

        public MalformedDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }

  /**
   * Reads the Marvel Universe dataset.
   * Each line of the input file contains a character name and a comic
   * book the character appeared in, separated by a tab character
   * 
   * @requires filename is a valid file path
   * @param filename the file that will be read
   * @throws MalformedDataException if the file is not well-formed:
   *          each line contains exactly two tokens separated by a tab,
   *          or else starting with a # symbol to indicate a comment line.
   * @return a map from characters to a map of other characters and shared
   * 		 books counts(to record shared books counts between two characters).
   */
  public static Map<String, HashMap<String, Integer>> parseData(String filename) 
		  throws MalformedDataException {
    // Why does this method accept the Collections to be filled as
    // parameters rather than making them a return value? To allows us to
    // "return" two different Collections. If only one or neither Collection
    // needs to be returned to the caller, feel free to rewrite this method
    // without the parameters. Generally this is better style.
	BufferedReader reader = null;
	// a map for the counts of shared books between two characters.
	Map<String, HashMap<String, Integer>> sharedBooksCounts = 
			new HashMap<String, HashMap<String, Integer>>();
    try {
		reader = new BufferedReader(new FileReader(filename));
		
		// a map for the book and its characters
		Map<String, ArrayList<String>> books = 
				new HashMap<String, ArrayList<String>>();
		
		String inputLine;
		while ((inputLine = reader.readLine()) != null) {
			
			// Ignore comment lines.
	        if (inputLine.startsWith("#")) {
	        	continue;
	        }
	        
	        // Parse the data, stripping out quotation marks and throwing
	        // an exception for malformed lines.
	        inputLine = inputLine.replace("\"", "");
	        String[] tokens = inputLine.split("\t");
	        if (tokens.length != 2) {
                throw new MalformedDataException("Line should contain exactly one tab: "
                        						 + inputLine);
	        }

	        String character = tokens[0];
	        String book = tokens[1];
	        
	        // add character to sharedBooksCounts
	        // if the character is not in the map(sharedBooksCounts) yet
	        if (!sharedBooksCounts.containsKey(character)) {
	        	sharedBooksCounts.put(character, new HashMap<String, Integer>());
	        }
	        
	        // add book to books
	        // if the book is not in the map(books) yet
	        if (!books.containsKey(book)) {
	        	books.put(book, new ArrayList<String>());
	        } else {
	        	// update all edge counts
	        	// add 1 count for character to other characters in the book and the reverse
	        	// if the book is already in the map(books), 
	        	for (String c2 : books.get(book)) {
	        		Map<String, Integer> edgeCountsFromC = sharedBooksCounts.get(character);
	        		if (!edgeCountsFromC.containsKey(c2)) {
	        			edgeCountsFromC.put(c2, 1);
	        		} else {
	        			edgeCountsFromC.put(c2, edgeCountsFromC.get(c2) + 1);
	        		}
	        		
	        		Map<String, Integer> edgeCountsFromC2 = sharedBooksCounts.get(c2);
	        		if (!edgeCountsFromC2.containsKey(character)) {
	        			edgeCountsFromC2.put(character, 1);
	        		} else {
	        			edgeCountsFromC2.put(character, edgeCountsFromC2.get(character) + 1);
	        		}
	        	}
	        }
	        // add the character to the book
	        books.get(book).add(character);
		}
    } catch (IOException e) {
        System.err.println(e.toString());
        e.printStackTrace(System.err);
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println(e.toString());
                e.printStackTrace(System.err);
            }
        }
    }
	return sharedBooksCounts;
  }

}