package edu.guilford;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class ScrabbleProjectDriver {
 public static void main(String[] args) {
        // Create a ScrabbleCalculator instance
        ScrabbleSet english = new ScrabbleSet(true);
        ScrabbleSet randomSet = new ScrabbleSet();
       
        
        // Words to calculate Scrabble values for
        String[] words = {"Banana", "Video", "Computer", "Phone", "Bottle"};

        // Calculate and print Scrabble values for each word
        for (String word : words) {
            int score = english.getWordScore(word);
            System.out.println("Word: '" + word + "', Scrabble Score: " + score);
        }

       // Test words against the random ScrabbleSet
       System.out.println("");
       System.out.println("Words tested against the random Scrabble set");
       String[] wordss = {"Banana", "Video", "Computer", "Phone", "Bottle"};
       for (String word : words) {
           int score = randomSet.getWordScore(word);
boolean isAcceptable = score > 0;

           System.out.println("Word: '" + word + "', Acceptable: " + isAcceptable);
        
       }

       File file = new File("../wordfile.txt");
       Map<String, Integer> wordScores = new HashMap<>();
       ScrabbleSet englishh = new ScrabbleSet();
       try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
           String line;
           while ((line = reader.readLine()) != null) {
               String[] wordsInLine = line.split("\\s+");
               for (String word : wordsInLine) {
                   // Scrabble score calculation
                   int score = english.getWordValue(word);
                   wordScores.put(word, score);
               }
           }
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }

       // Create a priority queue to store words based on their scores
       PriorityQueue<Map.Entry<String, Integer>> wordQueue = new PriorityQueue<>(new Comparator<Map.Entry<String, Integer>>() {
           @Override
           public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
               return o2.getValue() - o1.getValue();
           }
       });

       // Add all entries from wordScores to the priority queue
       wordQueue.addAll(wordScores.entrySet());
       System.out.println("");
       System.out.println("Top 5 highest scoring words from the wordfile text file");
       // Print the 5 highest scoring words
       for (int i = 0; i < 5; i++) {
       
           Map.Entry<String, Integer> entry = wordQueue.poll();
           System.out.println("Word: '" + entry.getKey() + "', Score: " + entry.getValue());
       }

       // Test these words against a random ScrabbleSet
       ScrabbleSet randomSett = ScrabbleSet.generateRandomScrabbleSet();
       System.out.println("");
       System.out.println("Testing 5 words against a random Scrabble set:");
       for (int i = 0; i < 5; i++) {
           Map.Entry<String, Integer> entry = wordQueue.poll();
           System.out.println("Word: '" + entry.getKey() + "', Test Result: " + randomSet.testWord(entry.getKey()));
       }


        // Read words from the "Frankenstein" text file and find the highest Scrabble score
        File filePath = new File("../frankenstein.txt");
        String highestScoringWord = "";
        int highestScore = 0;

        // Shortest non-valid word
        String shortestNonValidWord = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] wordsInLine = line.split("\\s+");
                for (String word : wordsInLine) {
                    // Scrabble score calculation
                    int score = english.getWordValue(word);

                    // Update highest scoring word if needed
                    if (score > highestScore) {
                        highestScore = score;
                        highestScoringWord = word;
                    }

                    // Update shortest non-valid word if needed
                    if (isNonValidWord(word) && (shortestNonValidWord == null || word.length() < shortestNonValidWord.length())) {
                        if (english.getWordScore(word) == 0) {
                            shortestNonValidWord = word;
                        }
                    }

                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found");
        } catch (IOException e) {
            System.out.println("Error: IO Exception");
        }

        // Print the highest scoring word and its score
        System.out.println("Word with the Highest Scrabble Score: '" + highestScoringWord + "', Score: " + highestScore);

        // Print the shortest non-valid word
        System.out.println("Shortest Non-Valid Word: '" + shortestNonValidWord + "'");
    }

    // Helper method to check if a word is non-valid (contains digits or punctuation)
    private static boolean isNonValidWord(String word) {
        return word.matches("[a-zA-Z]+") && !word.matches(".*\\d.*");

        
    }

}
