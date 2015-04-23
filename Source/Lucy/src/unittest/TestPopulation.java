package unittest;
import engine.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.LogManager;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Random;

public class TestPopulation {
	
		private Population ThePopulation;
		private Individual TheBestIndividual;
		
	  private Individual[] individuals;
	  private int[] population;           // selected Individuals
	  private int populationSize;        // size of the current population
	  private int N=20;  // number of Individuals
	  int n=10; //the number of this type to keep
	  private double BestFitnessValue;    // fitness of best individual
	  private int BestIndividual;         // index of the most fit individual

	  
	@Test
	public void test() throws SecurityException, FileNotFoundException, IOException {
		LogManager.getLogManager().readConfiguration(new FileInputStream("./logging.properties"));
		TestPopulationCreate();
		TestPopulationForGenerations(25);
		TestSorting();
		TestBestIndividual();

	}
		/*
		 * Unit test to test the basic create population function.
		 */
		public void TestPopulationCreate(){
			ThePopulation = new Population();
			
			System.out.println(" Initializing population ...");
			
			TheBestIndividual = ThePopulation.getBestIndividual();
			
			System.out.println("   Best Individual String:  " + TheBestIndividual.ToString()); 
			System.out.println("   Best Individual Fitness: " + TheBestIndividual.getFitnessValue());   
			
		}
		
		public void TestPopulationForGenerations(int numGens){
						
			System.out.println(" Running " + numGens + " Generations...");
			
			for (int i = 0; i < numGens; i++){
				ThePopulation = new Population(ThePopulation);
				
				TheBestIndividual = ThePopulation.getBestIndividual();
				
				System.out.println("Gen[" + i + "]--Best Individual String:  " + TheBestIndividual.ToString()); 
				System.out.println("Gen[" + i + "]--Best Individual Fitness: " + TheBestIndividual.getFitnessValue()); 
				
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

	      {
		System.out.println("Sorted by Fitness Value");
		for (int i = 0; i < populationSize; i++)
		  System.out.println(population[i] + " " 
				     + individuals[population[i]].getFitnessValue());}
		}
	  
		/*
		 * Unit test to test Best Individual
		 */
		public void TestBestIndividual(){  
				      
	        BestFitnessValue = individuals[0].getFitnessValue();
	        BestIndividual = 0;

	        double diff;
	        for (int j = 0; j < N; j++){
	        	diff = individuals[j].getFitnessValue() - BestFitnessValue;
	        	
	        	if ( diff < 0){
	        		BestFitnessValue = individuals[j].getFitnessValue();
	        		BestIndividual =j;
	      	  }
	        }	
	        System.out.println("Best Individual= "+BestIndividual);
	        System.out.println("Best Fitness Value= "+BestFitnessValue);
	        
		}	 
}		