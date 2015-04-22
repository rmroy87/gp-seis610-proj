package unittest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.LogManager;

import engine.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestBinaryNode {
	//ArrayList<BinaryNode> nodeList = new ArrayList<BinaryNode>();  
	
	
	@Test
	public void test() throws SecurityException, FileNotFoundException, IOException {
		LogManager.getLogManager().readConfiguration(new FileInputStream("./logging.properties"));
		
		test_creation_string_fitness();
		
		test_deepcopy_random_select();
		
		test_random_select_insert();
		
	}
	
	public void test_creation_string_fitness()
	{
		float nodeVal = 0;
		Settings settings = Settings.get();
		BinaryNode[] binNodes = new BinaryNode[10];
				
		int[] x_domain = {-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5};
		
		System.out.println("Binary Node Unit Test:");
		
		for(int i = 0; i < settings.MaxTreeDepth; i = i+1) {
			binNodes[i] = new BinaryNode(null, i);			
		
			System.out.print(String.format("Node->Depth    = %d\n", i));
			System.out.print(String.format("Node->Equation = f(x) = %s\n", 
					binNodes[i].ResolveNodeString()));
			System.out.print(String.format("Node->Resolved = "));
			for(int j=0; j < 11; j=j+1){
				nodeVal = binNodes[i].ResolveNodeValue(x_domain[j]);
				System.out.print(String.format("f(%d)= %4.2f, ",
						x_domain[j], nodeVal));
			}
			System.out.println();
			System.out.println();
		}		
	}
	
	public void test_deepcopy_random_select(){
		
		BinaryNode testCopy = null;
		
		BinaryNode OrigNode = null;
		BinaryNode RandSelectNode = null;
		
		System.out.println();
		System.out.println("Test the Deep Copy and Random Select:");
		OrigNode = new BinaryNode(null, 3);
		System.out.print(String.format("Original->Equation --> f(x) = %s\n", 
				OrigNode.ResolveNodeString()));
		testCopy = OrigNode.DeepCopy();
		System.out.print(String.format("Copy->Equation     --> f(x) = %s\n\n", 
				testCopy.ResolveNodeString()));
		
		for(int j=0; j < 10; j=j+1){
			RandSelectNode = OrigNode.GetBinaryNodeRandomly();
			System.out.print(String.format("Random Select[%d]->Equation   --> f(x) = %s\n", 
					j, RandSelectNode.ResolveNodeString()));
		}
		
	}
	
	
	public void test_random_select_insert(){
		BinaryNode TestParent1 = null;
		BinaryNode TestParent2 = null;
		BinaryNode OffSpring = null;
		BinaryNode CrossedOverNode = null;
		
		
		TestParent1 = new BinaryNode(null, 2);
		TestParent2 = new BinaryNode(null, 2);
		
		OffSpring       = TestParent1.DeepCopy();
		CrossedOverNode = TestParent2.GetBinaryNodeRandomly();
		
		System.out.println("\nTest the Random Copy and Random Insert:");
		
		System.out.print(String.format("Parent 1 -->        Equation   --> f(x) = %s Depth Top[%d]:L[%d]:R[%d]\n", 
				TestParent1.ResolveNodeString(),
				TestParent1.GetNodeDepth(),
				TestParent1.GetLeftNodeDepth(),
				TestParent1.GetRightNodeDepth() ));		
	
		System.out.print(String.format("Parent 2 -->        Equation   --> f(x) = %s Depth Top[%d]:L[%d]:R[%d]\n", 
				TestParent2.ResolveNodeString(),
				TestParent2.GetNodeDepth(),
				TestParent2.GetLeftNodeDepth(),
				TestParent2.GetRightNodeDepth() ));		
	
		System.out.print(String.format("Offspring Start --> Equation   --> f(x) = %s Depth Top[%d]:L[%d]:R[%d]\n", 
				OffSpring.ResolveNodeString(),
				OffSpring.GetNodeDepth(),
				OffSpring.GetLeftNodeDepth(),
				OffSpring.GetRightNodeDepth() ));		
		
		System.out.print(String.format("Selected Node -->   Equation   --> f(x) = %s Depth Top[%d]:L[%d]:R[%d]\n", 
				CrossedOverNode.ResolveNodeString(),
				CrossedOverNode.GetNodeDepth(),
				CrossedOverNode.GetLeftNodeDepth(),
				CrossedOverNode.GetRightNodeDepth() ));
		
		OffSpring.InsertBinaryNodeRandomly(CrossedOverNode);
		
		System.out.print(String.format("Offspring Cross --> Equation   --> f(x) = %s Depth Top[%d]:L[%d]:R[%d]\n", 
				OffSpring.ResolveNodeString(),
				OffSpring.GetNodeDepth(),
				OffSpring.GetLeftNodeDepth(),
				OffSpring.GetRightNodeDepth() ));	
	}

}
