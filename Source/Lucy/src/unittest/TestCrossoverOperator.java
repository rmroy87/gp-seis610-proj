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
	  private CrossoverOperator crosser  = new CrossoverOperator();
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
		
		System.out.println("\nPopulation Create:");
		for(int i = 0; i < 20; i++){					
			individuals[i] = new Individual();
			System.out.println(i + " " + individuals[i].getFitnessValue() + " String: " + individuals[i].ToString());
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
			
		Individual offspring1;
		Individual offspring2;
		
		System.out.println("\nTest Cross-Over:");
		for(int j=0; j< 20; j++){
			System.out.println("\nGeneration: " + j);
			// in population have the pair population[i], population[i+1] to use in crossover
			for (int i = 0; i < N - 1; i += 2){
			    System.out.println("Before crossover: ");
			    System.out.println(i + " " + individuals[i].getFitnessValue() + " String: " + individuals[i].ToString());
			    System.out.println((i+1) + " " + individuals[i+1].getFitnessValue() + " String: " + individuals[i+1].ToString() ); 
			    
			    offspring1 = crosser.CrossOver(i,individuals[i], individuals[i+1]);
			    offspring2 = crosser.CrossOver(i+1,individuals[i+1], individuals[i]);
			    				
			    System.out.println("After crossover ");
			    System.out.println(i + " " + offspring1.getFitnessValue() + " String: " + offspring1.ToString());
			    System.out.println((i+1) + " " + offspring2.getFitnessValue() + " String: " + offspring2.ToString());
			     
			    individuals[i]   = offspring1;
			    individuals[i+1] = offspring2;
			}
		}
		
		System.out.println("\nTest Cross-Over Done!");
	}
	
	public void TestPopulationAfterCrossOver(){
		
		System.out.println("\nPopulation After Cross-Over:");
		
		for(int i = 0; i < N; i++){					
			System.out.println(i + " " + individuals[i].getFitnessValue() + " String: " + individuals[i].ToString());
			 
		 }
		System.out.println("\nPopulation Test Done");
	}
}