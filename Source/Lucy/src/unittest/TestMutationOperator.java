
package unittest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.LogManager;

import engine.*;
import static org.junit.Assert.*;

import org.junit.Test;


public class TestMutationOperator {

	@Test
	public void testMutationOperator() {

	Individual parent1 = new Individual();
	Individual parent2 = new Individual();
	Individual mutatedIndividual1 = parent1.DeepCopyClone();
	Individual mutatedIndividual2 = parent2.DeepCopyClone();
	
	System.out.println(String.format("\nIndividual - Unit Test: (Mutate Individuals)\n"));
	
	System.out.print(String.format("Individual 1 ------> f(x) = %s [Fitness = %f]\n", parent1.ToString(), parent1.getFitnessValue() ));
	System.out.print(String.format("Individual 2 ------> f(x) = %s [Fitness = %f]\n", parent2.ToString(), parent2.getFitnessValue() ));
	System.out.print(String.format("Cloned 1 ----------> f(x) = %s [Fitness = %f]\n", mutatedIndividual1.ToString(), mutatedIndividual1.getFitnessValue() ));
	System.out.print(String.format("Cloned 2 ----------> f(x) = %s [Fitness = %f]\n\n", mutatedIndividual2.ToString(), mutatedIndividual2.getFitnessValue() ));
	
	mutatedIndividual1.ModifyIndividualRandomly();
	mutatedIndividual2.ModifyIndividualRandomly();
	
			
	System.out.print(String.format("Mutated Clone 1 ---> f(x) = %s [Fitness = %f]\n", 
			mutatedIndividual1.ToString(), mutatedIndividual1.getFitnessValue() ));
	System.out.print(String.format("Mutated Clone 2 ---> f(x) = %s [Fitness = %f]\n\n",
			mutatedIndividual2.ToString(), mutatedIndividual2.getFitnessValue() ));
			
}
}