package unittest;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.LogManager;

import org.junit.Test;

import engine.GP_Random;

public class TestGP_Random {

	@Test
	public void test() throws SecurityException, FileNotFoundException, IOException {
		LogManager.getLogManager().readConfiguration(new FileInputStream("./logging.properties"));
		
		testBinaryRandom(2, 10000);
		testBinaryRandom(4, 10000);
		testBinaryRandom(10, 10000);
		
	}
	
	public void testBinaryRandom(int range, int sampleSize) {
		int[] randVals = new int[range];
		int x;
		GP_Random engine = GP_Random.get();
		
		System.out.println("\nGP Random - Test - Range[" + range + "]");
		for(int i = 0; i < range; i = i+1) {
			randVals[i] = 0;
		}
		
		for(int i = 0; i < sampleSize; i = i+1) {
			x = engine.NextInt(range);			
			randVals[x] = randVals[x] + 1;		
		}
		
		for(int i = 0; i < range; i = i+1) {
			System.out.println("  Rand[" + i + "] --> " + randVals[i]);			
		}
		
		System.out.println("GP Random - Test - Done");
	}

}
