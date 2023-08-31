package readability;

import java.util.List;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The ReadabilityScore class simulates a calculator for the readability score.
 *
 * @version 1.10 14 Aug 2023
 * @author Clayton Mercer
 */
public class ReadabilityScore {

    // Variables used to calculate the score for different methods
    private String text;
    private String[] words;
    private String[] sentences;
    private List<String> characters;
    private int syllables;
    private int polysyllables;

    // Variables used to calculate the final age group from an average of multiple methods
    private int totalAge;
    private double averageAge;

    /**
     * Constructor to initialize the score calculator.
     *
     * @param text Text from the input file
     */
    public ReadabilityScore(String text) {
        this.text = text;

        // Displays different statistics for the input text
        showStatistics();
    }

    /**
     * Displays statistics about the text and prompts the user for a readability score calculation choice.
     * This method calculates and presents information such as the number of words, sentences, characters,
     * syllables, and polysyllables in the provided text.
     */
    private void showStatistics() {
        // Split the text into words and sentences
        words = text.split("\\s");
        sentences = text.split("[!.?]");

        // Create a list of individual characters from the text
        List<String> allCharacters = Arrays.asList(text.split(""));

        // Filter out non-whitespace characters to count actual characters
        characters = allCharacters.stream()
                .filter(character -> character.matches("\\S"))
                .collect(Collectors.toList());

        // Calculate the number of syllables and other readability-related values
        numberOfSyllables();

        // Display the calculated statistics
        System.out.printf("""
            Words: %d
            Sentences: %d
            Characters: %d
            Syllables: %d
            Polysyllables: %d
            """, words.length, sentences.length, characters.size(), syllables, polysyllables);

        // Prompt the user for the type of readability score to calculate
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
    }

    /**
     * Calculates the number of syllables in a word.
     *
     * @param word Individual word from a sentence
     * @return Number of syllables in the word, returns 1 if syllable count is 0
     */
    private static long syllableCount(String word) {
        Pattern syllablePattern = Pattern.compile("(?i)[aeiouy](?=[^aeiouy\\W])|[aiouy](?=\\b)");
        Matcher syllableMatcher = syllablePattern.matcher(word);
        long syllableCount = syllableMatcher.results().count();
        return syllableCount == 0L ? 1L : syllableCount;
    }

    /**
     * Calculates the number of syllables for every word.
     */
    private void numberOfSyllables() {
        for (String word : words) {
            syllables += syllableCount(word);
            if (syllableCount(word) > 2) {
                polysyllables ++;
            }
        }
    }

    /**
     * Retrieves an age group according to the score provided.
     *
     * @param score Score calculated from selected test
     * @return Age group corresponding to the score
     */
    private String ageGroup(double score) {
        String ageGroup = null;
        for (Table group: Table.values()) {
            if (Math.ceil(score) >= group.getScore()) {
                ageGroup = group.getAgeGroup();
            }
        }
        return ageGroup;
    }

    /**
     * Handles user input for the type of test.
     *
     * @param test Readability test selected by the user
     */
    public void test(String test) {
        test = test.toUpperCase();
        String testName = null;
        double score;
        switch (test) {
            case "ALL" -> {
                test("ARI");
                test("FK");
                test("SMOG");
                test("CL");
                averageAge = (double) totalAge / 4;
                printAverageAge();
                return;
            }
            case "ARI" -> {
                score = 4.71 * characters.size() / words.length + 0.5 * words.length / sentences.length - 21.43;
                testName = "Automated Readability Index";
                totalAge += Integer.parseInt(ageGroup(score));
                break;
            }
            case "FK" -> {
                score = 0.39 * words.length / sentences.length + 11.8 * syllables / words.length - 15.59;
                testName = "Flesch-Kincaid readability tests";
                totalAge += Integer.parseInt(ageGroup(score));
                break;
            }
            case "SMOG" -> {
                score = 1.043 * Math.sqrt((double) polysyllables * 30 / sentences.length) + 3.1291;
                testName = "Simple Measure of Gobbledygook";
                totalAge += Integer.parseInt(ageGroup(score));
                break;
            }
            case "CL" -> {
                score = 0.0588 * characters.size() / words.length * 100 - 0.296 * sentences.length / words.length * 100 - 15.8;
                testName = "Coleman-Liau index";
                totalAge += Integer.parseInt(ageGroup(score));
                break;
            }
            default -> {
                return;
            }
        }
        System.out.printf("%s: %.2f (about %s-year-olds).\n", testName, score, ageGroup(score));

    }

    /**
     * Prints the average age group across different methods of score calculation.
     */
    private void printAverageAge() {
        System.out.printf("\nThis text should be understood in average by %.2f year-olds.", averageAge);
    }

}
