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
		Operand  operand;
		//Operators operators = settings.Operators;
		int cntOperands   = settings.Operands.size();
		int cntOperators  = settings.Operators.size();
		String tempOperand; 
		String tempOperator; 
		
		String[] myOperands;
		String[] myOperators;
		
		myOperands = new String[cntOperands];
		myOperators = new String[cntOperators];
		
		FitnessCalculated = false;
		FitnessValue = (float) 1.0;
		IndividualString = null;
		int x = 0;
		
		
	
		//System.out.println("Individual Construct:");
		//System.out.println(tempOperand);
		//System.out.println(tempOperator);
		
		TheBinaryTree = new BinaryTree(settings.MaxTreeDepth);
		
	}
	
	/*
	 * Method used to calculate the actual fitness value for
	 * this individual.  It will be calculated just one time, and
	 * save for future reference.
	 */
	public float CalculateFitness(){
	
		if(FitnessCalculated == false){
			FitnessValue = (float) 8.6;
		}
		
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
	
}
