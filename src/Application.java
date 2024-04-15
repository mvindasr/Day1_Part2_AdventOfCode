import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class Application {
    static PrintStream out = System.out;

    // Local Calibration FilePath
    static final String calibrationFilePath = "src/calibrationDocument/input.txt";

    // Map to store numbers in word format and their corresponding digit
    static final Map<String, String> numberMap = createNumberMap();

    public static void main(String[] args) {

        out.println("-------------------------------------------------------------");
        out.println("         Elves' Snow Operation - Calibration System         ");
        out.println("-------------------------------------------------------------");

        out.println("Reading provided input file...");

        // Add calibration phrases into an array to process...
        ArrayList<String> calibrationDocument = createCalibrationDocument();

        out.println("Calculating sum of calibration values...");
        // Determine calibration value by analyzing calibration document
        int calibrationValue = getCalibrationValue(calibrationDocument);

        // Print calibration value
        out.println("***************************************************");
        out.println("Your calibration value is: "+calibrationValue);
        out.println("***************************************************");
    }

    /**
     * Function that process .txt file and create an ArrayList of calibration lines
     * @return ArrayList of calibration Strings
     */
    static ArrayList<String> createCalibrationDocument () {
        ArrayList<String> calibrationDocument = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(calibrationFilePath))) {
            String line;
            // Reading each line...
            while ((line = br.readLine()) != null) {
                // Adding each line to ArrayList...
                calibrationDocument.add(line);
            }
            out.println("File uploaded!");

        } catch (IOException e) {
            out.println("Calibration document not found!");
        }
        return calibrationDocument;
    }

    /**
     * Function that returns the sum of all calibration values
     * @param pDocument Official Calibration document
     * @return Sum of calibration integers
     */
    static int getCalibrationValue (ArrayList<String> pDocument) {
        int calibrationValue = 0;
        for (String line : pDocument) {

            // Variable for first and last number of each line
            int firstDigit = getFirstNumber(line);
            int lastDigit = getLastNumber(line);

            // Combine first and last digit to form individual calibration number...
            int calibrationNumber = firstDigit * 10 + lastDigit;

            // Add calibration number to calibration value...
            calibrationValue = calibrationValue + calibrationNumber;
        }
        return calibrationValue;
    }

    /**
     * Function to create a map numbers in word format and their corresponding digit values
     * @return Map of numbers in word and digit format
     */
    static Map<String, String> createNumberMap() {
        Map<String, String> map = new HashMap<>();
        map.put("one", "1");
        map.put("two", "2");
        map.put("three", "3");
        map.put("four", "4");
        map.put("five", "5");
        map.put("six", "6");
        map.put("seven", "7");
        map.put("eight", "8");
        map.put("nine", "9");
        return map;
    }

    /**
     * Function that returns the first appearance of a number in a calibration line, no matter if it is in word or digit format.
     * @param pLine The line from the calibration document
     * @return First number appearing in the line as Integer
     */
    static int getFirstNumber(String pLine) {
        // Map for storing numbers and their position in the line
        HashMap<String, Integer> numberAndLineIndex = new HashMap<>();

        // First iteration for finding first occurrence of numbers in the line parameter in their word format...
        for (String numberAsWord : numberMap.keySet()) {
            int index = pLine.indexOf(numberAsWord);
            if (index != -1) {
                numberAndLineIndex.put(numberAsWord, index);
            }
        }

        // Second iteration for finding first occurrence of numbers in the line parameter in their digit format...
        for (String numberAsDigit : numberMap.values()) {
            int index = pLine.indexOf(numberAsDigit);
            if (index != -1) {
                numberAndLineIndex.put(numberAsDigit, index);
            }
        }

        // Then the first number appearing in the line is determined according to indexes in the map...
        int lowestLineIndex = Integer.MAX_VALUE;
        String firstDigit = "";
        for (String number : numberAndLineIndex.keySet()) {
            if (numberAndLineIndex.get(number) < lowestLineIndex) {
                lowestLineIndex = (numberAndLineIndex.get(number));
                firstDigit = number;
            }

        }

        // If first digit is a word, we have to get the corresponding digit value from the map...
        if (firstDigit.length() != 1) {
            firstDigit = numberMap.get(firstDigit);
        }

        return Integer.parseInt(firstDigit);
    }

    /**
     * Function that returns the last appearance of a number in a calibration line, no matter if it is in word or digit format.
     * @param pLine The line from the calibration document
     * @return Last number appearing in the line as Integer
     */
    static int getLastNumber(String pLine) {
        HashMap<String, Integer> numberAndLineIndex = new HashMap<>();

        // First iteration for finding last occurrence of numbers in the line parameter in their word format...
        for (String numberAsWord : numberMap.keySet()) {
            int index = pLine.lastIndexOf(numberAsWord);
            if (index != -1) {
                numberAndLineIndex.put(numberAsWord, index);
            }
        }

        // Second iteration for finding last occurrence of numbers in the line parameter in their digit format...
        for (String numberAsDigit : numberMap.values()) {
            int index = pLine.lastIndexOf(numberAsDigit);
            if (index != -1) {
                numberAndLineIndex.put(numberAsDigit, index);
            }
        }

        // Then the last number appearing in the line is determined according to indexes in the map...
        int highestLineIndex = -1;
        String lastDigit = "";
        for (String number : numberAndLineIndex.keySet()) {
            if (numberAndLineIndex.get(number) > highestLineIndex) {
                highestLineIndex = (numberAndLineIndex.get(number));
                lastDigit = number;
            }

        }

        // If last digit is a word, we have to get the corresponding digit value from the map...
        if (lastDigit.length() != 1) {
            lastDigit = numberMap.get(lastDigit);
        }

        return Integer.parseInt(lastDigit);

    }

}

