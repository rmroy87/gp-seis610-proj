package engine;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CrossoverOperator {
	
	private static Logger logger = Logger.getLogger( GeneticProgramManager.class.getName() );

	public CrossoverOperator(){
		
	}

	/*
	 * Create a NEW Individual using a cloned parent, and a doner parent.
	 */
	public Individual CrossOver(long crossOverIndex, Individual cloneParent, Individual donerParent){
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

		logger.log(Level.FINEST, "Cross-Over[" + crossOverIndex + "] - Parent - FIT: " + cloneParent.getFitnessValue() + " DEPTH: " + cloneParent.getIndividualTreeDepth() + " STRING: " + cloneParent.ToString());
		logger.log(Level.FINEST, "Cross-Over[" + crossOverIndex + "] - Select: - DEPTH: " + crossSelect.GetNodeDepth() + " STRING: " + crossSelect.ResolveNodeString());
		logger.log(Level.FINEST, "Cross-Over[" + crossOverIndex + "] - Offspring - FIT: " + offspring.getFitnessValue() + " DEPTH: " + offspring.getIndividualTreeDepth() + " STRING: " + offspring.ToString());
		
		/*
		 * Return a NEW Individual, created from a cross over
		 */
		return offspring;
	}
}
