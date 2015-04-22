package unittest;
import engine.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.LogManager;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Random;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.logging.LogManager;

import org.junit.Test;

import engine.BinaryNode;
import engine.FitnessSelectionOperator;
import engine.Individual;

public class TestMutationOperator {

	private Individual[] individuals;
	  private int[] population;           // selected Individuals
	  private int populationSize;        // size of the current population
	  private int N=12;  // number of Individuals
	  int n=8; //the number of this type to keep


	@Test
	public void test() throws SecurityException, FileNotFoundException, IOException {
		LogManager.getLogManager().readConfiguration(new FileInputStream("./logging.properties"));
		TestPopulationCreate();
		TestSorting();
		testMutationOperator();

	}
	public void TestPopulationCreate(){
	individuals = new Individual[20]; //replace with InitPop variable	
	for(int i = 0; i < 20; i++){					
		individuals[i] = new Individual();
	  }
	}
	
	/*
	 * Unit test to test sorting individuals by highest fitness value
	 */
	public void TestSorting(){

  populationSize = n ; 
  
  // create new population
  population = new int[populationSize];

  double[] fitnessValues = new double[N];
  for (int i = 0; i < N; i++)
    fitnessValues[i] = individuals[i].getFitnessValue();

  FitnessSelectionOperator.selectBest(fitnessValues, N,  
				population, populationSize);
	}

	/*
	 * Unit test to test Mutation
	 */	
	public void testMutationOperator(){  
		Individual newMutation;
		for (int i = 0; i < populationSize; i++){
			System.out.println("Before mutation");
			System.out.println(population[i] + " " + individuals[population[i]].getFitnessValue()+ " String: " + individuals[population[i]].ToString());
			/*
			 * Added by Rob.  Create a deep Copy Clone of the Individual that you wish to Mutate,
			 * and then execute the random mutation on that cloned copy.  The original
			 * Individual does not change.
			 */
			newMutation = individuals[population[i]].DeepCopyClone(); 
			newMutation.ModifyIndividualRandomly();
				  // removed by Rob individuals[population[i]].MutationOperator(rand);
			
			System.out.println("After mutation");
			// removed by Rob System.out.println(population[i] + " " + individuals[population[i]].getFitnessValue());
			System.out.println(population[i] + " " + newMutation.getFitnessValue() + " String:" + newMutation.ToString());				
		}	
	}
}	