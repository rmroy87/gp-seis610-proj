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
	
	public Individual(){
		Settings settings = Settings.get();
		
		FitnessCalculated = false;
		FitnessValue = (float) 0.0;
		IndividualString = null;
		
		TheBinaryTree = new BinaryTree(settings.MaxTreeDepth);	
		
		CalculateFitness();
	}
	
	/*
	 * Method used to calculate the actual fitness value for
	 * this individual.  It will be calculated just one time, and
	 * save for future reference.
	 */
	private float CalculateFitness(){
		Training trainData = Training.get();
		float tempYData;
				
		FitnessValue = (float) 0.0;
		//System.out.print("Fitness Calc:");
		//
		// Go through each ordered pair set of the training
		// Data.
		for(OrderedPair pair : trainData.OrderedPairs)  {
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
	
}
