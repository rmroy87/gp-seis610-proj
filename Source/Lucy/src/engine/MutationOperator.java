package engine;

public class MutationOperator {

	public MutationOperator(){
		
	}
	
	/*
	 * Method used to mutate the cloned Individual, and create a
	 * NEW Individual with a random mutation.
	 */
	public Individual Mutate(Individual clonedIndividual){
		
		/*
		 * Start with a deep copy clone of the doner individual
		 */
		Individual newMutation = clonedIndividual.DeepCopyClone();

		/*
		 * Randomly modify this Individual
		 */
		newMutation.ModifyIndividualRandomly();	
		
		return newMutation;	
	}
}
	
