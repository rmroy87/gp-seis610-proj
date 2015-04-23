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

    
	  public Population()
	  { 
		Settings settings = Settings.get();
	    
		//creates initial population
		//individuals = new Individual[settings.InitPopulationSize];
		
			for(int i = 0; i < settings.InitPopulationSize; i++){					
				//individuals[i] = new Individual();
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
	  }
	  
	  public Population(Population genX)
	  { 
		Settings settings = Settings.get();
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
	  {probabilities = new double[N];
	    double overallProb = 0.0;
	    for (int j = 0; j < N; j++)
	      {
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
		 * selects individuals for crossover based on probability
		 */		
	  private void selectToCrossover()
	  {
	    selectProb(CrossoverSize);
	  }

		/*
		 * selects individuals for mutation based on probability
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
	  public Individual getBestIndividual()
	  {
		  return individuals.get(0);
	  }
	  
	  private void getBestFitnessValue()
	  {
	    BestFitnessValue = individuals.get(0).getFitnessValue();
	    BestIndividual = 0;
	  
	    double diff;
	    for (int j = 0; j < N; j++)
	      {
		diff = individuals.get(j).getFitnessValue() - BestFitnessValue;
		if (diff < 0)
		  {
		    BestFitnessValue = individuals.get(j).getFitnessValue();
		    BestIndividual = j;
		  }
	      }
	  }
	  
	  //public void set(int index, Individual individual) {
		 //individuals[index] = individual;  
	  //}
	  
	  protected void NextGeneration(){
		CrossoverOperator crosser  = new CrossoverOperator();
		MutationOperator Mutator = new MutationOperator();
		
		Settings settings = Settings.get();
		//Individual[] nextPopulation = new Individual[settings.PopulationSize];
		//ArrayList<Individual> nextPopulation = new ArrayList<Individual>();
		
		
		/*
		  for (int i = 0; i < settings.PopulationSize; i++)
		 
			//nextPopulation[i] = new Individual();
			individuals.add(new Individual());
		*/
		
		int index = 0;
		
		selectToKeep();
		// copy to the next generation
		for (int i = 0 ; i < populationSize; i++, index++)
			//nextPopulation[index].set(individuals[population[i]]);
			nextPopulation.set(index) = this.
		
	  
		selectToCrossover();
			
		Individual offspring1;
		Individual offspring2;{
		
		for (int i = 0; i < Population.populationSize - 1; i += 2){
			offspring1 = crosser.CrossOver(individuals[population[i]], individuals[population[i+1]]);
			offspring2 = crosser.CrossOver(individuals[population[i+1]], individuals[population[i]]);
		    
			// copy to the next generation
			for (int i = 0 ; i < populationSize; i++, index++)
				nextPopulation[index].set(individuals[population[i]]);
				
		selectToMutate();
		Individual newMutation;{
			for (int i = 0; i < populationSize; i++){				
				newMutation = Mutator.Mutate(individuals[population[i]]);				
			}
			// copy to the next generation
			for (int i = 0 ; i < populationSize; i++, index++)
			nextPopulation[index].set(individuals[population[i]]);
			
			// replace the old generation
			for (int i = 0; i < N; i++)
			  individuals[i].set(nextPopulation[i]);
		}

	}
}

}
