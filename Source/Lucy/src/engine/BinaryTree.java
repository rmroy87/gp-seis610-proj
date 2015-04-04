/**
 * 
 */
package engine;

import java.util.Random;

/**
 * @author royr4
 *
 */
public class BinaryTree {
	private BinaryNode TreeRoot;  // Root Node for the Binary Tree
	private Random engine;
	private int BinaryTreeDepth;
	
	public BinaryTree(int MaxTreeDepth){
		Settings settings = Settings.get();		
		//
		// Randomly select the Depth of this BinaryTree
		engine =  new Random();
		
		BinaryTreeDepth = engine.nextInt(settings.MaxTreeDepth);
		//System.out.print(String.format("Binary Tree: Depth=%d\n", BinaryTreeDepth));
		
		//
		// Generate the TreeRoot node, the nodes will self
		// populate from that point on.
		TreeRoot = new BinaryNode(null, BinaryTreeDepth);
	}
	
	//
	// Resolve the Binary Node to a String representing the nodes
	// equation.
	//
	public String ResolveNodeString(){
		return TreeRoot.ResolveNodeString();
	}
	
	//
	// Resolve the Binary Tree for a given value of Y
	//
	public float ResolveBinaryTree(int X_Value){		
		return TreeRoot.ResolveNodeValue(X_Value);
	}
	
}
