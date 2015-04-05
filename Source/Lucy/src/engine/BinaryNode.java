/**
 * 
 */
package engine;

import java.util.Random;


/**
 * @author royr4
 *
 */
public class BinaryNode {
	public static final int ROOT_TYPE = 0;
	public static final int BRANCH_TYPE = 1;
	public static final int LEAF_TYPE = 2;
	
	public static final int EXP_CONS = 0;
	public static final int EXP_X_VAR = 1;
	public static final int EXP_OPERATOR = 2;	
	
	private Random engine;
	private int Type;           // Root, Branch, Leaf
	private int Opperand;       // One of the operands, an X or a constant
	private int ExpressionType; // Type of Expression: Constant, X, operator 
	private int DepthOfNode;
	private String ExpressionOperator;
	private String NodeString;
	
	private BinaryNode ParentNode;  // If ROOT, this is NULL
	private BinaryNode LeftBranch;  // If Leaf, this is NULL
	private BinaryNode RightBranch; // If Leaf, this is NULL
	
	public BinaryNode(BinaryNode NodeParent, int NodeDepth){
		
		Settings settings = Settings.get();

		int randNum;
		ParentNode = NodeParent; 
		engine =  new Random();
		DepthOfNode = NodeDepth;
		//System.out.print(String.format("Node In: Depth=%d\n", NodeDepth));
		//
		// If the NODE Depth is 0, then we are a leaf
		if(NodeDepth == 0){
			LeftBranch  = null;
			RightBranch = null;
			// 
			// A LEAF_TYPE, either X Variable or a constant
			randNum = engine.nextInt(100);
			//System.out.print(String.format("Leaf Node: Var or Cons=%d\n", randNum));
			//
			// Even Numbers mean a CONSTANT, ODD is X Variable
			if((randNum % 2) == 0){
				//
				// Leaf node with one of the constant operands, so
				// randomly select one of the operands.
				ExpressionType = EXP_CONS;
				randNum = engine.nextInt(settings.Operands.size());
				Opperand = Integer.parseInt(Settings.get().Operands.get(randNum).getValue());
			}else{
				//
				// Leaf node will be an X variable.
				ExpressionType = EXP_X_VAR;
			}
			//System.out.print(String.format("Leaf Node: TYPE=%d, Opperand=%d\n", ExpressionType,Opperand));
		}else{
			//
			// We are either a ROOT, or a BRANCH, but it doesn't
			// matter, the creation is the same. 
			randNum = engine.nextInt(settings.Operators.size());
			ExpressionType = EXP_OPERATOR;
			ExpressionOperator = Settings.get().Operators.get(randNum).getValue();
			//System.out.print(String.format("Branch Node: TYPE=%d, Opperator=%s\n", ExpressionType,ExpressionOperator));
			NodeDepth   = NodeDepth - 1;
			LeftBranch  = new BinaryNode(this, NodeDepth);
			RightBranch = new BinaryNode(this, NodeDepth);
			//System.out.println();
		}
	}
	
	//
	// Resolve the Binary Node to a String representing the nodes
	// equation.
	//
	public String ResolveNodeString(){
		String LeftNode;
		String RightNode;
		
		if(DepthOfNode == 0){
			if(ExpressionType == EXP_X_VAR){
				NodeString = "X";
			}else{
				NodeString = String.format("%d", Opperand);
			}			
		}else{
			LeftNode  = LeftBranch.ResolveNodeString();
			RightNode = RightBranch.ResolveNodeString();
			
			NodeString = "(" + LeftNode + " " + ExpressionOperator + " " + RightNode + ")";
		}
		
		return (NodeString);
	}
	
	//
	// Resolve the NODE, and it's associated children nodes for a
	// given value of X
	//
	public float ResolveNodeValue(float X_Value){
		float LeftNode;
		float RightNode;
		float ResolvedValue = (float) 0.0;
		
		if(DepthOfNode == 0){
			if(ExpressionType == EXP_X_VAR){
				ResolvedValue = (float) X_Value;
			}else{
				ResolvedValue = (float) Opperand;
			}			
		}else{
			LeftNode  = LeftBranch.ResolveNodeValue(X_Value);
			RightNode = RightBranch.ResolveNodeValue(X_Value);
			
			switch(ExpressionOperator){
			
			    case "+" :
			    	ResolvedValue = LeftNode + RightNode;
			    	break;			       
			    case "-" :
			    	ResolvedValue = LeftNode - RightNode;
			       break;
			    case "*" :
			    	ResolvedValue = LeftNode * RightNode;
			       break;				       
			    case "/" :
			    	if(RightNode != 0){
			    		ResolvedValue = LeftNode / RightNode;
			    	}else{
			    		ResolvedValue = (float) 0.0;
			    	}
			       break;			       
			    default:
			    	ResolvedValue = (float) 0.0;
			}
		}		
		
		return ResolvedValue;
	}
}
