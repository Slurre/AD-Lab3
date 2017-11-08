import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.lang.*;

/**
 * Class contains an array of strings and methods to manipulate it.
 * It also contains variables to keep track of the number
 * of comparisons and exchanges needed when sorting the string array
 * in various ways. The sorting methods are selection sort and insertion sort.
 * Running main runs some smaller tests visible in the console and creates a file
 * containing information from a somewhat bigger test.
 *
 * Created by Ludwig von Feilitzen, 06-Nov-17. Compiled with Javac and edited in IntelliJ.
 */
public class Sorting {

    public int numberOfComparisons;
    public int numberOfExchanges;
    String[] stringArray;

    /**
     * Class constructor, generates instance from string array
     * @param inputArray String array to be stored in class
     */
    public Sorting(String[] inputArray) {
        stringArray = inputArray;
        numberOfComparisons = 0;
        numberOfExchanges = 0;
    }

    /**
     * Main runs tests for both sorting methods with a
     * predefined string array from slide set and a randomly
     * generated string array of 1024 strings.
     * @param args
     */
    public static void main (String[] args) {
        //Test with 1024 random strings
        Sorting test1 = new Sorting(generateRandomStrings(10, 1024));
        Sorting test2 = new Sorting(generateRandomStrings(10, 1024));
        test1.selectionSort();
        test2.insertionSort();
        System.out.println("-------------- SELECTION SORT --------------");
        test1.printArray();
        System.out.println("-------------- INSERTION SORT --------------");
        test2.printArray();

        //Test with lecture slide example
        String[] fromSlides1 = {"it", "was", "the", "best", "of", "times", "ever", "seen"};
        String[] fromSlides2 = {"it", "was", "the", "best", "of", "times", "ever", "seen"};
        Sorting selectionTest = new Sorting(fromSlides1);
        Sorting insertionTest = new Sorting(fromSlides2);
        selectionTest.selectionSort();
        insertionTest.insertionSort();
        System.out.println("-------------- SELECTION SORT --------------");
        selectionTest.printArray();
        System.out.println("-------------- INSERTION SORT --------------");
        insertionTest.printArray();

       //Test for string arrays of various sizes and save to text file
        try{
            numOfOpAndExch();
        }catch(IOException e){}
    }

    /**
     * Tests best case, worst case and average case for both sorting methods.
     * Creates file with results.
     * @throws IOException
     */
    public static void numOfOpAndExch() throws IOException{
        File file = new File("src/comparisons.txt");
        file.createNewFile();
        FileWriter filewriter = new FileWriter(file);

        ArrayList<Sorting> stringsToCompare = new ArrayList<>();
        for(int k = 1; k <=10; k++)
        {
            stringsToCompare.add(new Sorting(generateRandomStrings(10, (int)Math.pow(2, k))));
        }

        filewriter.write("--------------- SELECTION SORT --------------\n");
        for(Sorting stringArray: stringsToCompare)
        {
            stringArray.selectionSort();
            filewriter.write("Number of strings: " + stringArray.stringArray.length + "\n");
            filewriter.write("\tAverage case: \n");
            filewriter.write("\t\t-Number of comparisons: " + stringArray.numberOfComparisons + "\n");
            filewriter.write("\t\t-Number of exchanges: " + stringArray.numberOfExchanges + "\n");

            stringArray.selectionSort(); //Sorts already sorted
            filewriter.write("\tBest case: \n");
            filewriter.write("\t\t-Number of comparisons: " + stringArray.numberOfComparisons + "\n");
            filewriter.write("\t\t-Number of exchanges: " + stringArray.numberOfExchanges + "\n");

            stringArray.reverse(); //Reversed sorted order
            stringArray.selectionSort();
            filewriter.write("\tWorst case: \n");
            filewriter.write("\t\t-Number of comparisons: " + stringArray.numberOfComparisons + "\n");
            filewriter.write("\t\t-Number of exchanges: " + stringArray.numberOfExchanges + "\n");
        }


        stringsToCompare = new ArrayList<>();
        for(int k = 1; k <=10; k++)
        {
            stringsToCompare.add(new Sorting(generateRandomStrings(10, (int)Math.pow(2, k))));
        }

        filewriter.write("--------------- INSERTION SORT --------------\n");
        for(Sorting stringArray: stringsToCompare)
        {
            stringArray.insertionSort();
            filewriter.write("Number of strings: " + stringArray.stringArray.length + "\n");
            filewriter.write("\tAverage case: \n");
            filewriter.write("\t\t-Number of comparisons: " + stringArray.numberOfComparisons + "\n");
            filewriter.write("\t\t-Number of exchanges: " + stringArray.numberOfExchanges + "\n");

            stringArray.insertionSort(); //Sorts already sorted
            filewriter.write("\tBest case: \n");
            filewriter.write("\t\t-Number of comparisons: " + stringArray.numberOfComparisons + "\n");
            filewriter.write("\t\t-Number of exchanges: " + stringArray.numberOfExchanges + "\n");

            stringArray.reverse(); //Reversed sorted order
            stringArray.insertionSort();
            filewriter.write("\tWorst case: \n");
            filewriter.write("\t\t-Number of comparisons: " + stringArray.numberOfComparisons + "\n");
            filewriter.write("\t\t-Number of exchanges: " + stringArray.numberOfExchanges + "\n");
        }

        filewriter.flush();
        filewriter.close();


    }

    /**
     * Uses selection sort to sort the Sorting class instance's
     * string array by the first letter in each string.
     * NOTE: Sorts ONLY by first letter to better work
     * with the basic sorting theory learned in class, where we only
     * sort by ONE item in a file(one char in a string in this case).
     */
    public void selectionSort() {
        numberOfComparisons = 0; //Reset
        numberOfExchanges = 0;
        int i;
        int j;

        for (i = 0; i < this.stringArray.length - 1; i++)
        {
            int min = i;
            for (j = i + 1; j < this.stringArray.length; j++) //Find min index
            {
                if (lessThan(this.stringArray[j], this.stringArray[min])) min = j; //Save current min index
            }
            exch(this.stringArray, i, min); //Switch values
        }
    }

    /**
     * Uses insertion sort to sort the Sorting class instance's
     * string array by the first letter in each string.
     * NOTE: Sorts ONLY by first letter to better work
     * with the basic sorting theory learned in class, where we only
     * sort by ONE item in a file(one char in a string in this case).
     */
    public void insertionSort(){
        numberOfComparisons = 0; //reset
        numberOfExchanges = 0;
        for(int i = 1; i < this.stringArray.length; i++)
        {
            int counter = 1;
            String temp = this.stringArray[i];
            //Exchanges 2 adjacent elements, moving backwards through array,
            //as long as current element (temp) is smaller than the elements before it
            while((i - counter) >= 0 && lessThan(temp, this.stringArray[i - counter]))
            {
                exch(this.stringArray, i - counter + 1, i - counter);
                counter++;
            }
        }
    }

    /**
     * Compares two integers to see which is larger. Also
     * updates the class instance's comparison variable
     * to count comparisons.
     * @param a first integer
     * @param b second integer
     * @return true if first int is smaller than second int.
     */
    public boolean lessThan(Comparable a, Comparable b) {
        numberOfComparisons++;
        return a.compareTo(b) < 0;
    }

    /**
     * Mutates input string by exchanging the items
     * at the input indexes
     * @param array array to be changed
     * @param a index of first item
     * @param b index of second item
     */
    public void exch(String[] array, int a, int b)
    {
        String temp = array[a];
        array[a] = array[b];
        array[b]=temp;

        numberOfExchanges++;
    }

    /**
     * Generates an array of strings with both
     * uppercase and lowercase characters
     *
     * @param length          length of each string
     * @param numberOfStrings number of strings to generate
     * @return an array of random strings
     */
    public static String[] generateRandomStrings(int length, int numberOfStrings) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVXYZabcdefghijklmnopqrstuvxyz";
        int alphabetLength = alphabet.length();
        Random r = new Random();

        String[] outputArray = new String[numberOfStrings];
        for (int i = 0; i < numberOfStrings; i++) {
            String currentString = "";
            for (int letter = 0; letter < length; letter++) {
                int index = r.nextInt(alphabetLength); //Random index from alphabet string
                currentString += alphabet.charAt(index);
            }
            outputArray[i] = currentString;
        }
        return outputArray;
    }

    /**
     * Prints the strings in the string array for a class instance.
     * Also prints the current comparison and exchange variables.
     */
    public void printArray() {
        for (String current : stringArray) {
            System.out.println(current);
        }

        System.out.println("Number of comparisons used to sort array: " + numberOfComparisons);
        System.out.println("Number of exchanges used to sort array: " + numberOfExchanges);
    }

    /**
     * Reverses the Sorting class's stringArray
     */
    public void reverse()
    {
        String[] outputArray = new String[stringArray.length];
        for(int i = 0; i < stringArray.length; i++)
        {
            outputArray[stringArray.length - i - 1] = stringArray[i];
        }
        stringArray = outputArray;
    }

}
