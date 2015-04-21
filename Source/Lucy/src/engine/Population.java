package engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Population extends ArrayList<Individual> {
	  
	  private int N;  // number of Individuals
	  private int CrossoverSize; // number of Individuals to crossover 
	  private int MutateSize;    // number of Individuals to mutate   
	  private Individual[] individuals;    // population of Individuals
	  private double[] probabilities;     // selection probabilities 
	  private int[] population;           // selected Individuals
	  private int populationSize;        // size of the population --need to figure out if this is size of population
	  private double BestFitnessValue;    // fitness of best chromosome getBestFitnessValueValue
	  private int BestIndividual;         // index of the most fit chromosome getBestIndividual
	  private Random rand; // random generator
	  private int minimization;    
    
	  public Population()
	  { 
		Settings settings = Settings.get();
		
		//creates initial population
		individuals = new Individual[settings.InitPopulationSize];
		
			for(int i = 0; i < settings.InitPopulationSize; i++){					
				individuals[i] = new Individual();
			}

		/*
		* calculate the number of individuals to crossover
		*/
	    CrossoverSize = (int)(settings.CrossoverProbability*settings.PopulationSize); 
	      /** needs to be a multiple of 2 */
	      if (CrossoverSize % 2 != 0)
		CrossoverSize++;
	    
	    /*
		* calculate the number of individuals to mutate
		*/
	    MutateSize = (int)(settings.MutationProbability*settings.PopulationSize);
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
	      fitnessValues[i] = individuals[i].getFitnessValue();
	  
	    FitnessSelectionOperator.selectBest(fitnessValues, N,  
					population, populationSize);
	  }


	    /*
		* probability of being selected 
		*/
	  private void selectProb(int n)
	  {
    // compute individual selection probabilities based on fitness
	    computeProbabilities();
	    
	    populationSize = n;
	    population = new int[populationSize];
	    FitnessSelectionOperator.selectProb(N, probabilities, population, populationSize, rand);
	  }

	    /*
		* associate probability with individuals
		*/
	  private void computeProbabilities()
	  {

	    double overallProb = 0.0;
	    for (int j = 0; j < N; j++)
	      {
		  // want the smallest fitness value have the greatest probability
		 probabilities[j] = 1.0 / individuals[j].getFitnessValue();
		
		  probabilities[j] = individuals[j].getFitnessValue();
		
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
		 * probability of individual to be selected for crossover
		 */		
	  private void selectToCrossover()
	  {
	    selectProb(CrossoverSize);
	  }

		/*
		 * probability of individual to be selected for mutation
		 */		
	  private void selectToMutate()
	  {	    
	      selectProb(MutateSize); 
	  }


		/*
		 * select keepers based on probability
		 */	
	  private void selectToKeep()
	  {	      
	    //  selectProb(N - #crossover - #mutate);
	    selectBest(N - CrossoverSize - MutateSize);
	  }

		/*
		 * find individual with best fitness
		 */	
	  private void findBestFitnessValue()
	  {
	    BestFitnessValue = individuals[0].getFitnessValue();
	    BestIndividual = 0;
	  
	    double diff;
	    for (int j = 0; j < N; j++)
	      {
		diff = individuals[j].getFitnessValue() - BestFitnessValue;
		if ((minimization == 1 ? diff < 0: diff > 0))
		  {
		    BestFitnessValue = individuals[j].getFitnessValue();
		    BestIndividual = j;
		  }
	      }
	  }

}
