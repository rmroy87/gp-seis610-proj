/**
 * 
 */
package engine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	
	
	/*
	 * The typical BinayrNode for a random generation based on a
	 * desired node depth
	 */
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
	
	/*
	 * Over-loaded constructor to create an empty NODE.  Will be used
	 * for sure with the deep copy and cloning we will need to do.
	 */
	public BinaryNode(BinaryNode NodeParent){	
		ParentNode  = NodeParent; 		
		DepthOfNode = 0;			
		engine      =  new Random();		
		LeftBranch  = null;
		RightBranch = null;		
	}
	
	/*
	 * Make a deep copy of the Binary Node to include all of the sub-branches for 
	 * this NODE.
	 */
	public BinaryNode DeepCopy(){
		BinaryNode deepCopyNode = null;		
		/*
		 *  Create a NEW NODE for the storage of our CLONED/DEEP Copy.
		 *  We don't want it auto filled though, so just create a blank
		 *  NODE with matching depth.
		 */
		deepCopyNode = new BinaryNode(ParentNode);
		
		deepCopyNode.DepthOfNode = DepthOfNode;
				
		//System.out.print(String.format("Node In: Depth=%d\n", NodeDepth));
		//
		// If the NODE Depth is 0, then we are a leaf
		if(DepthOfNode == 0){
			LeftBranch  = null;
			RightBranch = null;
			
			deepCopyNode.ExpressionType = ExpressionType;
			deepCopyNode.Opperand       = Opperand;
			
			//System.out.print(String.format("Leaf Node: TYPE=%d, Opperand=%d\n", ExpressionType,Opperand));
		}else{
			//
			// We are either a ROOT, or a BRANCH, but it doesn't
			// matter, the creation is the same. 
			deepCopyNode.ExpressionType     = ExpressionType;
			deepCopyNode.ExpressionOperator = ExpressionOperator;
			
			//System.out.print(String.format("Branch Node: TYPE=%d, Opperator=%s\n", ExpressionType,ExpressionOperator));
			
			deepCopyNode.LeftBranch  = LeftBranch.DeepCopy();			
			deepCopyNode.RightBranch = RightBranch.DeepCopy();
			//System.out.println();
		}
		
		return deepCopyNode;
	}
		
	/*
	*   Return one of the Binary Nodes, from this Node.  Based on the NODE depth, we will 
	*   randomly select a Node to return, and then we will Clone that NODE and return a reference
	*   to that cloned copy.
	*/
	public BinaryNode GetBinaryNodeRandomlyOld(){
		BinaryNode deepCopyBinaryNode = null;
		BinaryNode nodeSelectedToCopy = null;
		int randNum;
		int depthToGo;
	
		nodeSelectedToCopy = this;
		/*
		 * Determine which NODE we want, in a totally random fashion
		 */
		if(DepthOfNode > 0){
			
			depthToGo = engine.nextInt(DepthOfNode);
			
			do{			
				randNum   = engine.nextInt(100);
				
				if((randNum % 2) == 0){
					if(nodeSelectedToCopy.LeftBranch != null){
						nodeSelectedToCopy = nodeSelectedToCopy.LeftBranch;
						depthToGo = depthToGo - 1;
					}else{
						depthToGo = 0;
					}
				}else{
					if(nodeSelectedToCopy.RightBranch != null){
						nodeSelectedToCopy = nodeSelectedToCopy.RightBranch;
						depthToGo = depthToGo - 1;
					}else{
						depthToGo = 0;
					}					
				}				
			}while(depthToGo > 0);			
		}
		
		/*
		 * Once we select the NODE, then make a deep clone copy of that NODE
		 * and return that copy by reference.
		 */
		deepCopyBinaryNode = nodeSelectedToCopy.DeepCopy();		
		
		return deepCopyBinaryNode;
	}
	
	
	/*
	*   Return one of the Binary Nodes, from this Node.  Based on the NODE depth, we will 
	*   randomly select a Node to return, and then we will Clone that NODE and return a reference
	*   to that cloned copy.
	*/
	private BinaryNode GetBinaryNodeWithRecursion(int depthToGo){
		BinaryNode nodeSelectedToCopy = null;
		int randNum;
	
		randNum   = engine.nextInt(2); /* Either a ZERO or a ONE */	
		/*
		 * If we are at the Depth that we would like to be at, simply
		 * flip the coin and determine if we go LEFT or RIGHT. Then
		 * do a deep copy on whicever is selected, and return as 
		 * a NEW node.
		 */
		if(depthToGo == 0){
			if(randNum == 0){
				nodeSelectedToCopy = LeftBranch.DeepCopy();
			}else{
				nodeSelectedToCopy = RightBranch.DeepCopy();  
			}			
		}else{
			depthToGo = depthToGo - 1;
			/*
			 * We are not yet at the depth we want to be at, so based on our
			 * coin flip, go LEFT or RIGHT and continue to recurse.
			 */
			if(randNum == 0){	
				/*
				 * We want to go deeper, but we may have an even branch, and there 
				 * may not be another NODE on the left.
				 */
				if((LeftBranch == null) || (LeftBranch.DepthOfNode == 0)){
					nodeSelectedToCopy = this.DeepCopy();					
				}else{
					nodeSelectedToCopy = LeftBranch.GetBinaryNodeWithRecursion(depthToGo);
				}
			}else{
				/*
				 * We want to go deeper, but we may have an even branch, and there 
				 * may not be another NODE on the right.
				 */
				if((RightBranch == null) || (RightBranch.DepthOfNode == 0)){
					nodeSelectedToCopy = this.DeepCopy();
				}else{
					nodeSelectedToCopy = RightBranch.GetBinaryNodeWithRecursion(depthToGo);
				}				
			}			
		}		
		return nodeSelectedToCopy;
	}
	
	/*
	*   Return one of the Binary Nodes, from this Node.  Based on the NODE depth, we will 
	*   randomly select a Node to return, and then we will Clone that NODE and return a reference
	*   to that cloned copy.
	*/
	public BinaryNode GetBinaryNodeRandomly(){
		BinaryNode nodeSelectedToCopy = null;	
		int depthToGo;
		
		/*
		 * Determine which NODE we want, in a totally random fashion
		 */
		if(DepthOfNode > 0){
			depthToGo = engine.nextInt(DepthOfNode);
			nodeSelectedToCopy = GetBinaryNodeWithRecursion(depthToGo);	
		}else{
			nodeSelectedToCopy = this.DeepCopy();
		}			
		
		return nodeSelectedToCopy;
	}	
	
	/*
	*   Recurssive Method designed actually insert a NODE based on a certain
	*   depth value, then a random left or right selection.
	*/
	private void InsertNode(BinaryNode nodeToInsert, int depthToRecurse)
	{
		int randNum;
		
		randNum   = engine.nextInt(2); /* Either a ZERO or a ONE */	
	
		/*
		 * If we are at the Depth that we would like to be at, simply
		 * flip the coin and determine if we go LEFT or RIGHT.
		 */
		if(depthToRecurse == 0){
			if(randNum == 0){
				LeftBranch  = null;  /* With luck, force the garbage collection to cleanup */
				LeftBranch  = nodeToInsert;
			}else{
				RightBranch  = null;  /* With luck, force the garbage collection to cleanup */
				RightBranch  = nodeToInsert;
			}
			nodeToInsert.ParentNode = this; 
			DepthOfNode = nodeToInsert.DepthOfNode + 1;
		}else{
			depthToRecurse = depthToRecurse - 1;
			/*
			 * We are not yet at the depth we want to be at, so based on our
			 * coin flip, go LEFT or RIGHT and continue to recurse.
			 */
			if(randNum == 0){
				if((LeftBranch == null) || (LeftBranch.DepthOfNode == 0)){
					LeftBranch  = nodeToInsert;
					nodeToInsert.ParentNode = this; 
					
				}else{
					LeftBranch.InsertNode(nodeToInsert, depthToRecurse);
				}
			}else{
				if((RightBranch == null) || (RightBranch.DepthOfNode == 0)){
					RightBranch  = nodeToInsert;
					nodeToInsert.ParentNode = this; 
					
				}else{
					RightBranch.InsertNode(nodeToInsert, depthToRecurse);
				}
								
			}	
			
			if(LeftBranch.DepthOfNode > RightBranch.DepthOfNode){
				DepthOfNode = LeftBranch.DepthOfNode + 1;
			}else{
				DepthOfNode = RightBranch.DepthOfNode + 1;
			}
		}
	}
	
	/*
	*   Insert a Binary Node randomly into this BinaryNode.  The depth will have to be considered
	*   to determine at what level we can insert the NODE at.  The NODE must be
	*   a complete NODE, and not a reference to another node.
	*/
	public void InsertBinaryNodeRandomly(BinaryNode nodeToInsert){	
		int maxPossibleDepth;
		int recurseIters;		
		/*
		 * Calculate how deep we can go with the NODE insertion.
		 */
		if( DepthOfNode > 0){
			
			if( DepthOfNode > nodeToInsert.DepthOfNode){
				
				maxPossibleDepth = DepthOfNode - nodeToInsert.DepthOfNode;
				recurseIters = engine.nextInt(maxPossibleDepth);
			}else{
				/*
				 * This is the depth that we will need to insert at
				 */
				recurseIters = 0;
			}		
			/*
			 * Okay, this will start the recursion...and Insert the
			 * NODE into the tree.
			 */
			InsertNode(nodeToInsert, recurseIters);
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
	
	/*
	 * Accessor to get the depth of the node.
	 */
	public int GetNodeDepth(){
		return DepthOfNode;
	}
	/*
	 * Accessor to get the depth of the node.
	 */
	public int GetLeftNodeDepth(){
		int depth = 0;
		
		if(LeftBranch != null)
			depth = LeftBranch.DepthOfNode;
		
		return depth;
	}
	/*
	 * Accessor to get the depth of the node.
	 */
	public int GetRightNodeDepth(){
		int depth = 0;
		
		if(RightBranch != null)
			depth = RightBranch.DepthOfNode;
		
		return depth;
	}
}
