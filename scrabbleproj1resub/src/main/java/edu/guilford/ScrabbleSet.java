package edu.guilford;

import java.util.Arrays;
import java.util.Random;

public class ScrabbleSet {
private String[] letters;
    private int[] letterCount;
    private int[] letterValues;  // Array to store the point values for each letter
    private Random randomValue;

    public ScrabbleSet(boolean isEnglish) {
        if (isEnglish) {
            letters = new String[]{"A", "A", "A", "A", "A", "A", "A", "A", "A", "B", "B", "C", "C", "D", "D", "D", "D", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "F", "F", "G", "G", "G", "H", "H", "I", "I", "I", "I", "I", "I", "I", "I", "I", "J", "K", "L", "L", "L", "L", "M", "M", "N", "N", "N", "N", "N", "N", "O", "O", "O", "O", "O", "O", "O", "O", "P", "P", "Q", "R", "R", "R", "R", "R", "R", "S", "S", "S", "S", "T", "T", "T", "T", "T", "T", "U", "U", "U", "U", "V", "V", "W", "W", "X", "Y", "Y", "Z", " ", ""};
            letterCount = new int[]{9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1, 2};
            letterValues = new int[]{1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10, 0};
        } else {
            System.out.println("Error: Only English is supported at this time");
        }
    }

      // this is the Second constructor
      public ScrabbleSet() {
        letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
                "R", "S", "T", "U", "V", "W", "X", "Y", "Z", " "};
        letterCount = new int[27]; // letters and blank
        randomValue = new Random();
    
        int totalTiles = 100;
        // Assign random counts to each letter
        for (int indexofLetters = 0; indexofLetters < letters.length; indexofLetters++) {
            int maxCount = 9; // You can adjust the maximum count as needed
            int randomCount = randomValue.nextInt(maxCount) + 1;
            letterCount[indexofLetters] = randomCount;
            totalTiles -= randomCount;
        }
        // Handle the blank tile separately
       if (totalTiles < 0) {
            for (int remove = 0; remove < Math.abs(totalTiles); remove++) {
                // Reduce the count of a random letter to adjust the total tiles
                int randomIndex = randomValue.nextInt(letters.length);
                while (letterCount[randomIndex] <= 1) {
                    // Make sure not to reduce the count below 1
                    randomIndex = randomValue.nextInt(letters.length);
                }
                letterCount[randomIndex]--;
            }
        }
    
        // Initialize letterValues array with random values
        letterValues = new int[]{1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10, 0};

    }
    
    

    // Add a method to get the point value for a given letter
    public int getWordValue(String word) {
        int total = 0;
        //  A copy of the letter counts array to avoid modifying the original lettercount set.
        int[] availableLetterCount = Arrays.copyOf(letterCount, letterCount.length - 1);

        for (int charIndex = 0; charIndex < word.length(); charIndex++) {
            char letter = Character.toUpperCase(word.charAt(charIndex));
            if (letter >= 'A' && letter <= 'Z') {

                int index = letter - 'A';

                if (index >= 0 && index < availableLetterCount.length && availableLetterCount[index] > 0) {
                    total += letterValues[index];
                    availableLetterCount[index]--; // Reduce the count of the used letter
                } else {
                    // Not enough letters of the current kind to spell the word
                    return 0;
                }
            } else {
                // Handle invalid letter in the word
                return 0;
            }
        }
    
        // Return the total point value of the word
        return total;
    }

    @Override
    public String toString() {
        return "ScrabbleSet {\n" +
                "Letters: " + Arrays.toString(letters) + "\n" +
                "Letter Count: " + Arrays.toString(letterCount) + "\n" +
                "Letter Values: " + Arrays.toString(letterValues) + "\n" +
                "}\n";
    }

    public int getWordScore(String word) {
        // Check if the word is acceptable based on letter counts in the ScrabbleSet
        int[] tempLetterCount = Arrays.copyOf(letterCount, letterCount.length);

        for (char letter : word.toUpperCase().toCharArray()) {
            if (letter == ' ') {
                continue;
            }

            int index = letter - 'A';

            if (index >= 0 && index < tempLetterCount.length && tempLetterCount[index] > 0) {
                tempLetterCount[index]--;
            } else {
                // Invalid letter in the word or not enough tiles for this letter
                return 0;
            }
        }

        // If we reach here, the word is valid, calculate the score
        return getWordValue(word);
    }

     // Generate a random Scrabble set
     public static ScrabbleSet generateRandomScrabbleSet() {
        return new ScrabbleSet();
    }

    // Add a method to test words against a Scrabble set
    public boolean testWord(String word) {
        int wordScore = getWordValue(word);
        return wordScore >= 5;
    }

}
