package engine;

public class CrossoverOperator {
	
	public CrossoverOperator(){
		
	}

	/*
	 * Create a NEW Individual using a cloned parent, and a doner parent.
	 */
	public Individual CrossOver(Individual cloneParent, Individual donerParent){
		BinaryNode crossSelect = null;		
		Individual offspring;
		
		/*
		 * Make a deep copy of the cloneParent
		 */
		offspring = cloneParent.DeepCopyClone();
		
		/*
		 * Randomly grab a node from the Doner Parent
		 */
		crossSelect = donerParent.GetBinaryNodeRandomly();
		
		/*
		 * Okay, now insert the doner node into the cloned offspring
		 */
		offspring.InsertBinaryNodeRandomly(crossSelect);

		/*
		 * Return a NEW Individual, created from a cross over
		 */
		return offspring;
	}
}
