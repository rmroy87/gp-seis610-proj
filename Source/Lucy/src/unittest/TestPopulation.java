package unittest;
import engine.BinaryNode;
import engine.FitnessSelectionOperator;
import engine.Individual;
import engine.OrderedPair;
import engine.Settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.LogManager;

import org.junit.Test;

import java.util.Random;

public class TestPopulation {
	  private Individual[] individuals;
	  private int[] population;           // selected Individuals
	  private int populationSize;        // size of the current population
	  private int N=15;  // number of Individuals
	  int n=10; //the number of this type to keep
	  private double[] probabilities; 
	  private Random rand; // random generator
	  private double BestFitnessValue;    // fitness of best individual
	  private int BestIndividual;         // index of the most fit individual
	  private int minimization=1;
	  private int CrossoverSize=6; // number of Individuals to crossover 

	  
	@Test
	public void test() throws SecurityException, FileNotFoundException, IOException {
		LogManager.getLogManager().readConfiguration(new FileInputStream("./logging.properties"));
		TestPopulationCreate();
		TestSorting();
		TestBestIndividual();
		TestMutation();
		TestCrossover();

	}
		/*
		 * Unit test to test the basic create population function.
		 */
		public void TestPopulationCreate(){
		individuals = new Individual[20]; //replace with InitPop variable	
		
		System.out.println(" Initializing population ...");
		for(int i = 0; i < 20; i++){					
			individuals[i] = new Individual();
			System.out.println("Individual= "+i);
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
	        	
	        	if ((minimization == 1 ? diff < 0: diff > 0)){
	        		BestFitnessValue = individuals[j].getFitnessValue();
	        		BestIndividual =j;
	      	  }
	        }	
	        System.out.println("Best Individual= "+BestIndividual);
	        System.out.println("Best Fitness Value= "+BestFitnessValue);
	        
		}
	        
		/*
		 * Unit test to test Mutation
		 */	
		public void TestMutation(){  
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
		/*
		 * Unit test to test Crossover
		 */		
		public void TestCrossover(){  
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
			    
				
			    // Commented out by Rob individuals[population[i]].CrossoverOperator(individuals[population[i+1]], rand);
			    offspring1.InsertBinaryNodeRandomly(crossSelect2);
				offspring2.InsertBinaryNodeRandomly(crossSelect1);
				
			    System.out.println("After crossover ");
			    System.out.println(population[i] + " " + offspring1.getFitnessValue() + " String: " + offspring1.ToString());
			    System.out.println(population[i+1] + " " + offspring2.getFitnessValue() + " String: " + offspring2.ToString());
			     
			}

		}

		/*
		 * Unit test to test probability of individual to be selected for crossover
		 */				
		
		public void TestCrossoverPop(){  
			//grab group for crossover from the current population */      

		    selectProb(CrossoverSize);
		      {
			// in population we have the selection
			System.out.println("Selected for CrossOver: ");
			for (int i = 0; i < populationSize; i++)
			  System.out.print(population[i] + " ");
			System.out.println();
		      }
		  }
		
		/*
		 * Unit test for probability creation
		 */	
	
	private void selectProb(int n)
	  {	
	    // compute individual selection probabilities based on their fitness
	    computeProbabilities();
	    
	    populationSize = n;

	    // create new population
	    population = new int[populationSize];
	    FitnessSelectionOperator.selectProb(N, probabilities, population, populationSize, rand);
	  }

	  //gets probabilities associated with individuals 

	  private void computeProbabilities()
	  {	  
	    double overallProb = 0.0;
	    for (int j = 0; j < N; j++)
	      {
		  // want the smallest fitness value to have the greatest probability
		 probabilities[j] = 1.0 / individuals[j].getFitnessValue();
		
		  probabilities[j] = individuals[j].getFitnessValue();
		
		overallProb += probabilities[j];
	      }
	  
	    // make the probabilities with sum equal to 1
	    for (int j = 0; j < N; j++)
	      probabilities[j] /= overallProb;

	      {
		System.out.println("Probabilities ");
		for (int j = 0; j < N; j++)
		  System.out.print(probabilities[j] + " ");
		System.out.println();
	      }
	    
	  }
}		