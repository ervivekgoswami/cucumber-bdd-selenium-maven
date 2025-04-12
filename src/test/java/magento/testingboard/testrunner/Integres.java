package magento.testingboard.testrunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Integres {

	

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter a string: ");
            String input = scanner.nextLine();
            
            // Step 2: Split the string into words
            String[] words = input.toLowerCase().split("\\s+");
            
            // Step 3: Store word counts
            Map<String, Integer> wordCountMap = new HashMap<>();
            for (String word : words) {
                wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
            }
            
            // Step 4: Identify and count duplicate words
            int duplicateWordCount = 0;
            System.out.println("Duplicate words:");
            for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
                if (entry.getValue() > 1) {
                    duplicateWordCount++;
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
            }
            
            // Step 5: Display the number of duplicate words
            System.out.println("Number of duplicate words: " + duplicateWordCount);
	}

}
	
}
