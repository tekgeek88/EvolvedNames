import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * A genome represents a unique set of characters from the dataset<br>
 * <em>{ A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, _, - ’ }<br>
 * This class serves as a blueprint for all new genomes that are created.
 * 
 * @author Carl Argabright
 * @author Luke Gillmore 
 * @version April 11th, 2018
 */
public class Genome implements Comparable<Genome> {

    /* A data element that is initialized to your name. */
    private static String targetString = "CHRISTOPHER PAUL MARRIOTT";
//    private static String targetString = "LUKE";
    private String currentString;
    //  private static ArrayList<Character> POSSIBLE_CHARCTERS = null;
    //    public static Random RANDOM_NUMBER;
    int count = 0; 
    /* The mutation rate of this genome */
    private double mutationRate;

    private static final char[] POSSIBLE_CHARACTERS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '-', '\''};

    /**
     * <p>Initializes a genome with the default value ‘A’ and assigns the internal<br>
     * mutation rate to the desired mutation rate.
     * 
     * @param mutationRate The mutation rate
     * 
     *<p> The mutationRate must be between zero and
     */
    public Genome(final double mutationRate) {
        currentString = "A";
        this.mutationRate = mutationRate;
    }

    /**
     * <p>A copy constructor that initializes a Genome with the same values as the input gene.
     * @param mutationRate A genome
     * 
     *<p> The mutationRate must be between zero and 1
     */
    public Genome(final Genome gene) {
        currentString = gene.currentString;
        mutationRate = gene.mutationRate;
    }

    /**
     * This function mutates the string in this Genome using the following rules:
     * <ul>
     * <li>with mutationRate chance add a randomly selected<br>
     * character to a randomly selected position in the string.
     * <li>with mutationRate chance delete a single character<br>
     * from a randomly selected position of the string but do this<br>
     * only if the string has length at least 2.
     * <li>for each character in the string:
     * <ul>
     * <li>with mutationRate chance the character is replaced by a<br>
     * randomly selected character.
     * </ul>
     */
    public void mutate() {
        // ((v = someMethod()) != 0)
        double mutationRateChance = 0;
        if((mutationRateChance = Main.RANDOM_NUMBER.nextDouble()) < mutationRate) {
            addNewCharacter();
        }
        if((mutationRateChance = Main.RANDOM_NUMBER.nextDouble()) < mutationRate && currentString.length() > 2) {
            removeRandomCharacter();
        }   
        replaceRandomCharacter();


    }

    /**
     * <p>Internal helper method used by the mutate method to add<br>
     * a new character somewhere in the string.
     */
    private void addNewCharacter() {
        StringBuilder sb = new StringBuilder();
        ArrayList<Character> theCurrentStringList = new ArrayList<Character>();

        for (int i = 0; i < currentString.length(); i++) {
            theCurrentStringList.add(currentString.charAt(i));
        }

        // Get random char
        int randomPossibleCharIndex = Main.RANDOM_NUMBER.nextInt(POSSIBLE_CHARACTERS.length);
        int randomCharIndex = Main.RANDOM_NUMBER.nextInt(theCurrentStringList.size() + 1); 
        // Add it to the new list of chacters.
        theCurrentStringList.add(randomCharIndex, POSSIBLE_CHARACTERS[randomPossibleCharIndex]);

        // Append the list to a string
        for(int j = 0; j < theCurrentStringList.size(); j++) {
            sb.append(theCurrentStringList.get(j));
        }
        currentString = sb.toString();
    }

    /**
     * <p> 5% chance that each character in string is replaced 
     * with a random character. Then returns string. 
     */
    private void replaceRandomCharacter() {
        char[] tempString = currentString.toCharArray();
        for(int i = 0; i < tempString.length; i++) {
            double chance = Main.RANDOM_NUMBER.nextDouble();
            if(chance < mutationRate) {
                int randomeNum = Main.RANDOM_NUMBER.nextInt(POSSIBLE_CHARACTERS.length);
                tempString[i] = POSSIBLE_CHARACTERS[randomeNum];
            }
        }
        currentString =  new String(tempString);
    }

    /**
     * <p>Internal helper method used by the mutate method to<br>
     * remove a random character from the string.
     */
    private void removeRandomCharacter() {
        StringBuilder sb = new StringBuilder();
        int randomCharIndex = Main.RANDOM_NUMBER.nextInt(currentString.length() + 1);
        char[] temp = currentString.toCharArray();
        for(int i = 0; i < temp.length; i++) {
            if( i != randomCharIndex) {
                sb.append(temp[i]);
            }
        }
        currentString = sb.toString();
        //      POSSIBLE_CHARACTERS.remove(RANDOM_NUMBER.nextInt(POSSIBLE_CHARACTERS.length));
    }

    /**<p> This function will update the current Genome by crossing it over with other.<br>
     * Create the new list by following these steps for each index in the String starting<br>
     * the first index.
     * <ul>
     * <li> Randomly choose one of the two parent Strings.
     * <li> If the parent String has a character at this index <br> (i.e. it is long enough) 
     * copy that character into the new list. Otherwise end the new list here. 
     * </ul></ul>
     * 
     * @param other returns altered genome. 
     */
    void crossover(Genome other) {
        String parentString = currentString;
        int index = 0;
        boolean isEnd = false; 
        String child = "";

        while(!isEnd) {

            // Randomly choose a parent string.
            int randomBit = Main.RANDOM_NUMBER.nextInt(2);
            if (randomBit == 1) {
                // assign parent here.. 
                parentString = currentString;
            } else {
                // assign parent here.
                parentString = other.currentString;
            }
            if(index > parentString.length() - 1) {
                isEnd = true;
            }
            // If char can be written write it...
            if (!isEnd) {
                child += parentString.charAt(index);
            }
            index++;
        }
        currentString = child;
    }

    /**
     *  <p>Simple zero-­based fitness test.<br>
     *  Returns the fitness of the Genome calculated using the following algorithm:<br>
     *  <ul>
     *  <li>Let n be the length of the current string.<br>
     *      Let m be the length of the target string.
     *  <li>Let l be the max(n, m).
     *  <li>Let f be initialized to |m - n|.
     *  <li>For each character position 1 ≤ <i>i</i> ≤ <i>l</i> add one to <i>f</i> if the character in the current
     *       string is different from the character in the target string (or if one of the two
     *       characters does not exist). Otherwise add nothing to <i>f</i>.
     *  <li>Return <i>f</i>.
     *  </ul>
     * @return An integer representing the distance.
     */
    public Integer fitness() {

        int currentStringLength = currentString.length();
        int targetStringLength = targetString.length();
        int greatestLength = Math.max(currentStringLength, targetStringLength);
        String smallerString, biggerString;
        if (greatestLength > currentStringLength) {
            biggerString = targetString;
            smallerString = currentString;
        } else {
            biggerString = currentString;
            smallerString = targetString;
        }
//                System.out.println("smallerString: " + smallerString);
//                System.out.println("biggerString: " + biggerString);
        int distance = Math.abs(targetStringLength - currentStringLength);
//                System.out.println("Distance: " + distance);
        int sizeOfSmallerString = smallerString.length();
        for (int i = 0; i < greatestLength; i++) {
            if (i < sizeOfSmallerString) {
//                                System.out.println("i: " + i + " sizeOfSmaller: " + sizeOfSmallerString + " sizeOfBigger: " + biggerString.length());
                if (smallerString.charAt(i) != biggerString.charAt(i)) {
                    distance++;
                } 
            } 
            else {
                distance++;
            }
        }
        return distance;
    }

    /**
     * Uses the Wagner­Fischer algorithm for calculating Levenshtein edit distance:
     * @return
     */
    public Integer fitness2() {
        int len1 = currentString.length();
        int len2 = targetString.length();
        int[][] arr = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++)
            arr[i][0] = i;
        for (int i = 1; i <= len2; i++)
            arr[0][i] = i;
        for (int i = 1; i <= len1; i++)
        {
            for (int j = 1; j <= len2; j++)
            {
                int m = (currentString.charAt(i - 1) == targetString.charAt(j - 1)) ? 0 : 1;
                arr[i][j] = Math.min(
                                     Math.min(arr[i - 1][j] + 1, arr[i][j - 1] + 1),
                                     arr[i - 1][j - 1] + m);
            }
        }
        return arr[len1][len2];
    }

    /**
     * Display's the Genome’s character string and fitness in
     *  an easy to read format.
     */
    public String toString() {
        return "(\"" + currentString + "\", " + fitness() + ")";
    }

    public double getMutationRate() {
        return mutationRate;
    }


    //########## NEED TO THINK ANOTHER IMPLEMENTATION FOR WAGNER FISCHER ####///
    ///BOOLEAN ..?
    //  public int compareToWEGNER(Genome o) {
    //      int fitLevel = this.fitnessWagnerFischer().compareTo(o.fitnessWagnerFischer());
    //      return fitLevel == 0 ? this.toString().compareTo(o.toString()) : fitLevel;
    //  }
    /* 
     * Sorts by fitness level if equal
     *  compare strings natural order
     */ 

    @Override
    public int compareTo(Genome o) {
        int fitLevel = this.fitness().compareTo(o.fitness());
        int result = 0;
        if ( fitLevel < 0) {
            result = -1;
        } else if (fitLevel > 0) {
            result = 1;
        }
        return result;
        //      return fitLevel < 0 ? this.toString().compareTo(o.toString()) : fitLevel;
    }




}
