package engine;

public class MutationOperator {

	public MutationOperator(){
		
	}
	
	/*
	 * Method used to mutate the cloned Individual, and create a
	 * NEW Individual with a random mutation.
	 */
	public Individual Mutate(Individual clonedIndividual){
		
		System.out.println("MOO -- 1:" + clonedIndividual.ToString());
		/*
		 * Start with a deep copy clone of the doner individual
		 */
		Individual newMutation = clonedIndividual.DeepCopyClone();

		/*
		 * Randomly modify this Individual
		 */
		newMutation.ModifyIndividualRandomly();	
		System.out.println("MOO -- 2:" + newMutation.ToString());
		
		return newMutation;	
	}
}
	
