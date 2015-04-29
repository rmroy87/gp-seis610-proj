package engine;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MutationOperator {

	private static Logger logger = Logger.getLogger( GeneticProgramManager.class.getName() );
	
	public MutationOperator(){
		
	}
	
	/*
	 * Method used to mutate the cloned Individual, and create a
	 * NEW Individual with a random mutation.
	 */
	public Individual Mutate(long mutationIndex, Individual clonedIndividual){
		
		/*
		 * Start with a deep copy clone of the doner individual
		 */
		Individual newMutation = clonedIndividual.DeepCopyClone();

		/*
		 * Randomly modify this Individual
		 */
		newMutation.ModifyIndividualRandomly();	
		
		logger.log(Level.FINEST, "Mutate[" + mutationIndex + "] - Doner - FIT: " + clonedIndividual.getFitnessValue() + " DEPTH: " + clonedIndividual.getIndividualTreeDepth() + " STRING: " + clonedIndividual.ToString());
		logger.log(Level.FINEST, "Mutate[" + mutationIndex + "] - Mutation - FIT: " + newMutation.getFitnessValue() + " DEPTH: " + newMutation.getIndividualTreeDepth() + " STRING: " + newMutation.ToString());
		
		return newMutation;	
	}
}
	
