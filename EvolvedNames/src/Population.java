import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Carl Argabright
 * @author Luke Gillmore 
 * @version April 11th, 2018
 */
public class Population {

	/**
	 * A data element that is equal to the most­fit Genome in the population.
	 */
	Genome mostFit;
	Double mutationRate;

	/* a list of Genomes representing the current population. */
	private List<Genome> population;


	/**
	 * <p>Initializes a Population with a number of default genomes.<br>
	 * 
	 * @param numGenomes desired number of initial genomes.
	 * @param mutationRate desired initial mutation rate.
	 */
	Population(final Integer numGenomes, Double mutationRate) {
		this.mutationRate = mutationRate;
		population = new ArrayList<Genome>();
		generateDefaultGenomes(numGenomes, mutationRate);
	}

	/**
	 * <p>This function is called every breeding cycle and carries out the following steps:<br>
	 * <ul>
	 * <li>Update mostFit variable to the most­fit Genome in the population.<i>(genome with lowest fitnes level.)</i>
	 * <li>Delete the least­fit half of the population.
	 * <li>Create new genomes from the remaining population until the number of genomes is restored by<br>
	 *     doing either of the following with equal chance:
	 *     <ul>
	 *     <li>pick a remaining genome at random and clone it (with the copy<br>
	 *         constructor) and mutate the clone.
	 *     <li>pick a remaining genome at random and clone it and then crossover the<br>
	 *         clone with another remaining genome selected at random and then mutate the result.
	 *     </ul>
	 * </ul>
	 */
	public void day() {

		// Update mostFit variable to the most­fit Genome in the population.
		updateMostFit();
		
		// Delete the least­fit half of the population.
		populationDecrease();

		/*Create new genomes to fill replenish population */
		while (population.size() < 100) {	
			int pickMutation = Main.RANDOM_NUMBER.nextInt(2);
			
			// Select a single remaining string at random
			// and add a mutated copy of this string to the population.

			if (pickMutation == 1) {
			    int cloneIndex = Main.RANDOM_NUMBER.nextInt(population.size());
				Genome clone = new Genome(population.get(cloneIndex));
//				System.out.print("Mutating: " + clone + " to: ");
				clone.mutate();
//				System.out.println(clone);
				population.add(clone);
				
			} else {
				int cloneIndex = Main.RANDOM_NUMBER.nextInt(population.size());
				Genome clone = new Genome(population.get(cloneIndex));
				
				int crossoverIndex = Main.RANDOM_NUMBER.nextInt(population.size());
				while (crossoverIndex == cloneIndex) {
					crossoverIndex = Main.RANDOM_NUMBER.nextInt(population.size());
				}
				
				// Cross clones
				Genome crossOverClone = population.get(crossoverIndex);
//				System.out.println("Crossing: " + clone + " with " + crossOverClone);
				clone.crossover(crossOverClone);
//				System.out.println("Crossover result: " + clone);
				// Mutate clone
//				System.out.print("Mutating: " + clone + " to: ");
                clone.mutate();
//                System.out.println(clone);
				population.add(clone);
			}
		}
	}

	public Genome getMostFit() {
		return mostFit;
	}

	private void generateDefaultGenomes(final Integer numberOfGenomes, final Double mutationRate) {
		int desiredNumberOfGenomes = numberOfGenomes;
		while (desiredNumberOfGenomes > 0) {
			Genome tempGenome = new Genome(mutationRate);
			population.add(tempGenome);
			desiredNumberOfGenomes--;
		}
		mostFit = population.get(0);
	}

	/**
	 * Cut population 
	 * in half removing last half of list. 
	 */
	private void populationDecrease() {
		int desiredPopulationSize = population.size() / 2;
		for(int i = population.size() - 1; i >= desiredPopulationSize; i-- ) {
			population.remove(i);
		}
	}
	/**
	 * <p> sorts Genomes by fitness level and returns mostFit
	 * @return mostFit.
	 */
	private Genome updateMostFit() {
		Collections.sort(population);
		mostFit = population.get(0);
		return mostFit;
	}
	
	@Override
	public String toString() {
	    updateMostFit();
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < population.size(); i++) {
            sb.append(population.get(i).toString() + "\n");
//            if (i != population.size() - 1) {
//                sb.append(", ");
//            }
        }
	    return sb.toString();
	}


}
