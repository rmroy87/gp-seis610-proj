/**
 * 
 */
package engine;

/**
 * @author royr4
 *
 */
public class BinaryTree {
	private BinaryNode TreeRoot;  // Root Node for the Binary Tree
	private GP_Random engine;
	private int BinaryTreeDepth;
	
	/*
	 * The standard radnomly generated Binary Tree
	 */
	public BinaryTree(int MaxTreeDepth){
		Settings settings = Settings.get();		
		//
		// Randomly select the Depth of this BinaryTree
		engine = GP_Random.get();
		
		BinaryTreeDepth = engine.NextInt(settings.MaxTreeDepth);
		//System.out.print(String.format("Binary Tree: Depth=%d\n", BinaryTreeDepth));
		
		//
		// Generate the TreeRoot node, the nodes will self
		// populate from that point on.
		TreeRoot = new BinaryNode(null, BinaryTreeDepth);
	}
	
	/*
	 * The private constructor used for Cloning the Binary Tree
	 */
	private BinaryTree(BinaryTree theClone){
			
		//
		// Randomly select the Depth of this BinaryTree
		engine = GP_Random.get();
		
		BinaryTreeDepth = theClone.BinaryTreeDepth;
		//System.out.print(String.format("Binary Tree: Depth=%d\n", BinaryTreeDepth));
		
		//
		// Generate the TreeRoot node, the nodes will self
		// populate from that point on.
		TreeRoot = theClone.TreeRoot.DeepCopy();
	}
	
	/*
	 *  Do the Deep Copy Clone for the Binary Tree
	 */
	public BinaryTree DeepCopyClone(){
		BinaryTree theCopy = new BinaryTree(this);
		
		return theCopy;
	}
	
	/*
	 * Randomly modify one of the NODES within this Binary Tree.
	 */
	public void ModifyTreeRandomly(){
		Settings settings = Settings.get();
		BinaryNode modifiedNode = null;
		int depthOfTree;
		int randomNum;
		
		depthOfTree = TreeRoot.GetNodeDepth();
		/*
		 * Create a random number for the modified nodes depth
		 * based on the current depth of the tree.
		 */
		
		if((settings.MaxTreeDepth - depthOfTree) <= 0){
			randomNum = 0;
		}else{
			randomNum = engine.NextInt(settings.MaxTreeDepth - depthOfTree);
		}
		
		
		/*
		 * Create the space and randomly populate the node.
		 */
		modifiedNode = new BinaryNode(null, randomNum);
		/*
		 * Wedge that new NODE somewhere into the Binary Tree
		 */
		InsertBinaryNodeRandomly(modifiedNode);
	}
	
	/*
	*   Get a Random Binary Node from this Individuals Binary Tree, it will Clone the
	*   Binary NODE.
	*/
	public BinaryNode GetBinaryNodeRandomly(){
		return TreeRoot.GetBinaryNodeRandomly();
	}
	
	/*
	*   Insert a Binary Node randomly for this Individuals Binary Tree, the NODE must be
	*   a complete NODE, and not a reference to another node.
	*/
	public void InsertBinaryNodeRandomly(BinaryNode nodeToInsert){
		Settings settings = Settings.get();
		
		if((TreeRoot.GetNodeDepth() == 0) || (nodeToInsert.GetNodeDepth() == settings.MaxTreeDepth - 1 ) ){
			TreeRoot = nodeToInsert;
		}else{
			TreeRoot.InsertBinaryNodeRandomly(nodeToInsert);
		}
		
	}
	
	/*
	* Resolve the Binary Node to a String representing the nodes
	* equation.
	*/
	public String ResolveNodeString(){
		return TreeRoot.ResolveNodeString();
	}
	
	/*
	* Resolve the Binary Tree for a given value of Y
	*/
	public float ResolveBinaryTree(float X_Value){		
		return TreeRoot.ResolveNodeValue(X_Value);
	}
	
	/*
	* Resolve the Binary Tree for a given value of Y
	*/
	public int DepthofBinaryTree(){	
		BinaryTreeDepth = TreeRoot.GetNodeDepth();
		return BinaryTreeDepth;
	}	
}
