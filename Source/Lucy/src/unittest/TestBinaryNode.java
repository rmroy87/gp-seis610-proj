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
		
		float nodeVal = 0;
		Settings settings = Settings.get();
		BinaryNode[] binNodes = new BinaryNode[10];
		int[] x_domain = {-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5};
		
		System.out.println("Binary Node Unit Test:\n");
		
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

}
