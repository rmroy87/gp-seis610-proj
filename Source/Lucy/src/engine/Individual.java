/**
 * 
 */
package engine;

/**
 * @author royr4
 *
 */
public class Individual {
	
	private float     FitnessValue;      // Actual calculated fitness for individual
	private boolean   FitnessCalculated; // Have we calculated fitness?
	private String    IndividualString;  // Binary Tree converted to a string
	private BinaryTree  TheBinaryTree;   // Binary tree for this individual
	
	/*
	*   Basic Constructor, will randomly generate an Individual
	*/
	public Individual(){
		Settings settings = Settings.get();
		
		FitnessCalculated = false;
		FitnessValue = (float) 0.0;
		IndividualString = null;
		
		TheBinaryTree = new BinaryTree(settings.MaxTreeDepth);	
		
		CalculateFitness();
	}
	
	/*
	*   The Deep Copy/Clone Constructor, willperform a deep copy of the
	*   Individual.
	*/
	private Individual(Individual toClone){		
		
		FitnessCalculated 	= toClone.FitnessCalculated;
		FitnessValue 		= toClone.FitnessValue;
		IndividualString 	= null;		
		TheBinaryTree 		= toClone.TheBinaryTree.DeepCopyClone();			
	}
	
	/*
	*   Get a Random Binary Node from this Individuals Binary Tree, it will Clone the
	*   Binary NODE.
	*/
	public BinaryNode GetBinaryNodeRandomly(){	
		return TheBinaryTree.GetBinaryNodeRandomly();
	}
	
	/*
	*   Insert a Binary Node randomly for this Individuals Binary Tree, the NODE must be
	*   a complete NODE, and not a reference to another node.
	*/
	public int InsertBinaryNodeRandomly(BinaryNode nodeToInsert){
		
		/*
		 * Insert the Node into the Binary Tree
		 */
		TheBinaryTree.InsertBinaryNodeRandomly(nodeToInsert);		
		/*
		 * Calculate the NEW Fitness for this Individual
		 */
		CalculateFitness();
		
		IndividualString = TheBinaryTree.ResolveNodeString();
		
		return 0;
	}
	
	/*
	*   Modify this Individual in a Random fashion.  Think of a 
	*   mutation.
	*/
	public int ModifyIndividualRandomly(){		
		/*
		 * Insert the Node into the Binary Tree
		 */
		TheBinaryTree.ModifyTreeRandomly();		
		/*
		 * Calculate the NEW Fitness for this Individual
		 */
		CalculateFitness();
		
		IndividualString = TheBinaryTree.ResolveNodeString();
		
		return 0;
	}
	
	/*
	 * Make a complete deep copy CLONE of this Individual.  That means to actually
	 * COPY each of the sub objects for this individual, and not use their
	 * references.
	 */
	public Individual DeepCopyClone(){
		Individual clonedCopy = new Individual(this);		
			
		return clonedCopy;
	}
	
	
	/*
	 * Method used to calculate the actual fitness value for
	 * this individual.  It will be calculated just one time, and
	 * save for future reference.
	 */
	private float CalculateFitness(){
		Settings settings = Settings.get();
		
		float tempYData;
				
		FitnessValue = (float) 0.0;
		//System.out.print("Fitness Calc:");
		//
		// Go through each ordered pair set of the training
		// Data.
		for(OrderedPair pair : settings.Training)  {
			//System.out.print(String.format("{FI[%4.2f]", FitnessValue));
			tempYData = TheBinaryTree.ResolveBinaryTree(pair.X);
			//
			// Take the absolute value of the difference between
			// the resolved value and the Y value.
			if(tempYData > pair.Y){
				FitnessValue = FitnessValue + (tempYData - pair.Y);	
			}else{
				FitnessValue = FitnessValue + (pair.Y - tempYData);	
			}
			//System.out.print(String.format("FO[%4.2f]->X[%4.2f]C[%4.2f]Y[%4.2f]} ", 
			//		FitnessValue, pair.X, tempYData, pair.Y));
		}
		//System.out.println();
		FitnessCalculated = true;		
		
		
		return FitnessValue;
	}

	/*
	 * Method used to generate the string equation version of
	 * the binary tree for this individual.
	 */
	public String ToString(){
		
		if(IndividualString == null){
			IndividualString = TheBinaryTree.ResolveNodeString();
		}
		
		return IndividualString;
	}
	
	/* 
	 * Method to simply get the Fitness, if it has not been calculated
	 * then calculate it quickly
	 */
	public float getFitnessValue()
	{
		if(FitnessCalculated == false){
			CalculateFitness();
		}
		
		return FitnessValue;
	}
	
	/*
	 * Method to get the depth of the Binary Tree
	 */
	public int getIndividualTreeDepth()
	{
		return TheBinaryTree.DepthofBinaryTree();
	}
	
}
