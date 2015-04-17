package unittest;

import static org.junit.Assert.*;

import org.junit.Test;

import engine.BinaryNode;
import engine.Individual;

public class TestCrossoverOperator {

	@Test
	public void testCrossOverOperator(){
		BinaryNode crossSelect1 = null;
		BinaryNode crossSelect2 = null;
		Individual parent1 = new Individual();
		Individual parent2 = new Individual();
		Individual offspring1 = parent1.DeepCopyClone();
		Individual offspring2 = parent2.DeepCopyClone();
		
		System.out.println(String.format("\nIndividual - Unit Test: (Cross-Over Individuals)\n"));
		
		System.out.print(String.format("Parent 1 -----> f(x) = %s [Fitness = %f]\n", parent1.ToString(), parent1.getFitnessValue() ));
		System.out.print(String.format("Parent 2 -----> f(x) = %s [Fitness = %f]\n", parent2.ToString(), parent2.getFitnessValue() ));
		System.out.print(String.format("Cloned 1 -----> f(x) = %s [Fitness = %f]\n", offspring1.ToString(), offspring1.getFitnessValue() ));
		System.out.print(String.format("Cloned 2 -----> f(x) = %s [Fitness = %f]\n\n", offspring2.ToString(), offspring2.getFitnessValue() ));
		
		crossSelect1 = parent1.GetBinaryNodeRandomly();
		crossSelect2 = parent2.GetBinaryNodeRandomly();
		
		System.out.print(String.format("Cross Select 1 --> f(x) = %s\n", crossSelect1.ResolveNodeString()) );
		System.out.print(String.format("Cross Select 2 --> f(x) = %s\n\n", crossSelect2.ResolveNodeString() ));
		
		offspring1.InsertBinaryNodeRandomly(crossSelect2);
		offspring2.InsertBinaryNodeRandomly(crossSelect1);
		
		System.out.print(String.format("Offspring 1 --> f(x) = %s [Fitness = %f]\n", offspring1.ToString(), offspring1.getFitnessValue() ));
		System.out.print(String.format("Offspring 2 --> f(x) = %s [Fitness = %f]\n\n", offspring2.ToString(), offspring2.getFitnessValue() ));
	
	}
}