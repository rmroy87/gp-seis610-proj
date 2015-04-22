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
import engine.Individual;

public class TestCrossoverOperator {
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
		testCrossOverOperator();

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
	 * Unit test to test Crossover
	 */		 
	public void testCrossOverOperator(){
			BinaryNode crossSelect1 = null;
			BinaryNode crossSelect2 = null;
			Individual offspring1;
			Individual offspring2;
			
			System.out.println();
			// in population have the pair population[i], population[i+1] to use in crossover
			for (int i = 0; i < populationSize - 1; i += 2){
			    System.out.println("Before crossover: ");
			    System.out.println(population[i] + " " + individuals[population[i]].getFitnessValue() + " String: " + individuals[population[i]].ToString());
			    System.out.println(population[i+1] + " " + individuals[population[i+1]].getFitnessValue() + " String: " + individuals[population[i+1]].ToString() ); 
			    
			    offspring1 = individuals[population[i]].DeepCopyClone();
			    offspring2 = individuals[population[i+1]].DeepCopyClone();
			    
			    crossSelect1 = individuals[population[i]].GetBinaryNodeRandomly();
				crossSelect2 = individuals[population[i+1]].GetBinaryNodeRandomly();
				System.out.println("Cross Select 1: String: " + crossSelect1.ResolveNodeString());
				System.out.println("Cross Select 2: String: " + crossSelect2.ResolveNodeString());
			    

			    offspring1.InsertBinaryNodeRandomly(crossSelect2);
				offspring2.InsertBinaryNodeRandomly(crossSelect1);
				
			    System.out.println("After crossover ");
			    System.out.println(population[i] + " " + offspring1.getFitnessValue() + " String: " + offspring1.ToString());
			    System.out.println(population[i+1] + " " + offspring2.getFitnessValue() + " String: " + offspring2.ToString());
			     
			}

		}
}