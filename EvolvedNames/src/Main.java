import java.util.Random;

/**
 * TCSS 342 ­ Data Structures Assignment 2 ­- Evolved Names
 * @author Carl Argabright
 * @author Luke Gillmore
 * @version April 11th, 2018
 *
 */
public class Main {


    private static final double DEFAULT_MUTATION_RATE = 0.05;
    private static final int DEFAULT_GENOMES = 100;
    public static final String REQUIRED_STRING = "CHRISTOPHER PAUL MARRIOTT";
    public static Random RANDOM_NUMBER;

    /**
     * Main Method
     * this method should instantiate a population and call day() until
     * the target string is part of the population.
     * 
     * <ul>
     * <li>The target string has fitness zero so the loop should<br>
     *     repeat until the most fit genome has fitness zero.
     * <li>After each execution of day() output the most fit genome.
     * <li>To measure performance output the number of generations (i.e times day() is
     *     called) and the execution time.
     * </ul>
     * @param args
     */
    public static void main(String[] args) {

        /* The current most fit gene updated by the population class each day. */
        Genome mostFit;
        long runningTime;
        long startTime;
        long endTime;
        RANDOM_NUMBER = new Random();


        // Instantiate the Population class using the 
        // default number of genomes and mutations rate

        //        Population population = new Population(DEFAULT_GENOMES, DEFAULT_MUTATION_RATE);


        // call day() from the Population class until the fitness of the most fit genome is zero.

        // While most fit genome is > 0
        int totalNumberOfGenerations = 0;
        int simulationCount = 0;
        int desiredSimulations = 1;

        do {
            simulationCount++;
            System.out.println("\n*******  Starting simulation: " + simulationCount + " *******");
            Population population = new Population(DEFAULT_GENOMES, DEFAULT_MUTATION_RATE);
            int numberOfGenerations = 0;
            do  {
                startTime = System.currentTimeMillis();
                population.day();
                mostFit = population.getMostFit();

                // Output simulation progress by displaying the most fit genome.
                System.out.println(mostFit);
                numberOfGenerations++;
            } while (mostFit.fitness() > 0);
            //Output runtime statistics.
            endTime = System.currentTimeMillis();
            runningTime = endTime - startTime;
            System.out.println("Generations: " + numberOfGenerations);
            System.out.println("Running Time: " + runningTime + " milliseconds.");
            totalNumberOfGenerations += numberOfGenerations;
            System.out.println("*******  Finished simulation: " + simulationCount + " *******");
            System.out.println("\n**** Displaying Population toString() ****");
            System.out.println(population.toString());
            System.out.println("\n**** Finished displaying Population toString() ****");
        } while (simulationCount < desiredSimulations);

        
        System.out.println("\nAverage number of generations: " + (totalNumberOfGenerations / simulationCount ) + " (" + totalNumberOfGenerations + " / " + simulationCount + " = " + (totalNumberOfGenerations / simulationCount) + ")");

    }

    /**
     * this method tests the Genome class.
     */
    public static void testGenome() {

    }

    /**
     *  This method tests the Population class.
     */
    public static void testPopulation() {

    }

}
