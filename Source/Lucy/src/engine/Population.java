package engine;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Population {
	  
	  private int N;  // number of Individuals
	  private int CrossoverSize; // number of Individuals to crossover 
	  private int MutateSize;    // number of Individuals to mutate   
	  //private static Individual[] individuals;    // population of Individuals
	  ArrayList<Individual> individuals;
	  private double[] probabilities;     // selection probabilities 
	  private int[] population;           // selected Individuals
	  private int[] crossOverList; // Rob added
	  private int[] mutationList;  // Rob added
	  public static int populationSize;        // size of the population
	  private double BestFitnessValue;    // fitness of best individual getBestFitnessValueValue
	  private int BestIndividual;         // index of best individual getBestIndividual
	  private Random rand; // random generator

    
	  /*
	   * This constructor is used to create the INITIAL POPULATION, once an 
	   * initial population has been created, use the other constructor to
	   * work with the succeeding generations of a population.
	   */
	  public Population()
	  { 
			Settings settings = Settings.get();
					
			for(int i = 0; i < settings.InitPopulationSize; i++){			
				individuals.add(new Individual());
			}
			
			/*
			* calculate the number of individuals to crossover
			*/
			CrossoverSize = (int)(settings.CrossoverProbability*settings.PopulationSize); 
			 // needs to be a multiple of 2 
			if (CrossoverSize % 2 != 0)
				  CrossoverSize++;
			
			/*
			* calculate the number of individuals to mutate
			*/
			MutateSize = (int)(settings.MutationProbability*settings.PopulationSize);
			
			/*
			 * Sort he Population Array List, so that they are ordered based on
			 * the best fitness value
			 */
	    
	   }
	  
		  
	  /*
	   * This constructor is used to work on the "Next Generation" of a population.
	   */  
	  public Population(Population genX)
	  { 
			Settings settings = Settings.get();
			
			/*
			 * Do we need to seed the new population with our best X individuals without
			 * probability?  Don't know what the setting is for the auto keepers??
			 */
			for(int i = 0; i < 5; i++){			
				individuals.add(genX.individuals.get(i));
			}
			
			/*
			 * Select our best fitness individuals to carry forward based on probability
			 */
			Keepers(genX);
			
			/*
			 * Select and ADD our Cross Over candidates
			 */
			Crossover();
			
			/*
			 * Select and ADD our Mutation candidates
			 */
			Mutate();
			
			/*
			 * Sort the new population based on the new individuals
			 */
			
			
		    // Stub in
	  }

	/*
	* sort individuals by highest fitness value
	*/
	  private void selectBest(int n) 
	  {
		    populationSize = n ;
		    population = new int[populationSize];
	
		    double[] fitnessValues = new double[N];
		    
		    for (int i = 0; i < N; i++)
		      fitnessValues[i] = individuals.get(i).getFitnessValue();
		
		  
		    FitnessSelectionOperator.selectBest(fitnessValues, N,  
						population, populationSize);
	  }

	
	/*
	* probability of being selected 
	*/
	  private void selectMutateProb(int n)
	  {
			// compute individual selection probabilities based on fitness
			computeProbabilities();
					
			mutationList = new int[n];
			
			FitnessSelectionOperator.selectProb(N, probabilities, mutationList, n, rand);
	  }
	  
	/*
	* probability of being selected 
	*/
	  private void selectCrossOverProb(int n)
	  {
			// compute individual selection probabilities based on fitness
			computeProbabilities();			
			
			crossOverList = new int[n];
			
			FitnessSelectionOperator.selectProb(N, probabilities, crossOverList, n, rand);
	  }


	/*
	* associate probability with individuals
	*/
	  private void computeProbabilities()
	  {
		  	probabilities = new double[N];
		  	double overallProb = 0.0;
		  	
			for (int j = 0; j < N; j++){
			  // want the smallest fitness value have the greatest probability
				probabilities[j] = 1.0 / individuals.get(j).getFitnessValue();	
				overallProb += probabilities[j];
			}
	  
			// make the probabilities with sum equal to 1
		    for (int j = 0; j < N; j++)
		      probabilities[j] /= overallProb;

		    for (int j = 1; j < N - 1; j++)
		      probabilities[j] += probabilities[j - 1];
	   
		    probabilities[N - 1] = 1.0;
	  }

	  /*
	   * Perform the crossovers for this Generation...
	   */
	  private void Crossover()
	  {
		  CrossoverOperator crosser  = new CrossoverOperator();
		  
		  Individual offspring1;
		  Individual offspring2;
	  
		  selectCrossOverProb(CrossoverSize);
			
		  for (int i = 0; i < CrossoverSize - 1; i += 2){			
		
			  offspring1 = crosser.CrossOver(individuals.get(crossOverList[i]), individuals.get(crossOverList[i+1]));
			  offspring2 = crosser.CrossOver(individuals.get(crossOverList[i+1]), individuals.get(crossOverList[i]));
				
			  individuals.add(offspring1);
			  individuals.add(offspring2);
		  }
	  }
	  
	  /*
	   * Perform all of the Mutation for this Generation...
	   */
	  private void Mutate()
	  {	 
		  Individual newMutation;	
		  MutationOperator Mutator = new MutationOperator();
		  
		  selectMutateProb(MutateSize); 
		  
		  for (int i = 0; i < MutateSize; i++){				
			newMutation = Mutator.Mutate(individuals.get(mutationList[i]));
			
			individuals.add(newMutation);			
		  }			
	  }
	  
	/*
	 * select keepers based on probability, we will need the last generation to 
	 * complete this task
	 */	
	  private void Keepers(Population genX)
	  {	      
		    selectBest(N - CrossoverSize - MutateSize);
		    
			for (int i = 0 ; i < populationSize; i++){
				/*
				 * Copy the desired individuals from the last generation to the new
				 * generation.
				 */
				individuals.add(genX.individuals.get(population[i]));
			}
	  }
	  

		/*
		 * find individual with best fitness
		 */	
	  public Individual getBestIndividual()
	  {
		  return individuals.get(0);
	  }
	  
	  private void getBestFitnessValue()
	  {
		    BestFitnessValue = individuals.get(0).getFitnessValue();
		    BestIndividual = 0;
		  
		    double diff;
		    
		    for (int j = 0; j < N; j++){
		    	
		    	diff = individuals.get(j).getFitnessValue() - BestFitnessValue;
		    	if (diff < 0){
		    		BestFitnessValue = individuals.get(j).getFitnessValue();
		    		BestIndividual = j;
		    	}
		    }
	  }
	  	
	  //
	  // Deprecated with the new constructor to work on next generation
	  protected void NextGeneration(){
		//CrossoverOperator crosser  = new CrossoverOperator();
		//MutationOperator Mutator = new MutationOperator();
		
		//Settings settings = Settings.get();
		//Individual[] nextPopulation = new Individual[settings.PopulationSize];
		//ArrayList<Individual> nextPopulation = new ArrayList<Individual>();
		
		
		/*
		  for (int i = 0; i < settings.PopulationSize; i++)
		 
			//nextPopulation[i] = new Individual();
			individuals.add(new Individual());
		*/
		
		
	  }
	  }
	  
