package readability;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;

/**
 * The Main class serves as an entry point for the readability score calculator.
 *
 * It initializes the calculator and processes user input.
 *
 * @version 1.10 14 Aug 2023
 * @author Clayton Mercer
 */
public class Main {

    /**
     * The main method reads in a text file provided by the user.
     *
     * @param args Command-line arguments (text-file name in working directory)
     * @throws FileNotFoundException If the specified file does not exist
     */
    public static void main(String[] args) throws FileNotFoundException {
        // Create a File object to read the file-path from the command line argument
        File file = new File(args[0]);

        // Create Scanners to read user input
        Scanner fileScanner = new Scanner(file);
        Scanner inputScanner = new Scanner(System.in);

        // Variable to store contents of the file
        String sentence = null;

        // Attempt to read file
        try {
            while (fileScanner.hasNext()) {
                sentence = fileScanner.nextLine();
            }

        } catch (NoSuchElementException e) {
            System.out.println("File reading completed!");
        }

        // Print contents of the file
        System.out.printf("""
                The text is:
                %s
                
                """, sentence);

        // Initialize the ReadabilityScore class to calculate a score
        ReadabilityScore rs = new ReadabilityScore(sentence);

        // Processes user input for type of test
        rs.test(inputScanner.next());

        // Closes the Scanners
        fileScanner.close();
        inputScanner.close();
    }
}

