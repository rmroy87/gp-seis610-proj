package engine;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Population extends ArrayList<Individual> {
	  
	  private int N;  // number of Individuals
	  private int CrossoverSize; // number of Individuals to crossover 
	  private int MutateSize;    // number of Individuals to mutate   
	  private static Individual[] individuals;    // population of Individuals
	  private double[] probabilities;     // selection probabilities 
	  private int[] population;           // selected Individuals
	  public static int populationSize;        // size of the population
	  private double BestFitnessValue;    // fitness of best individual getBestFitnessValueValue
	  private int BestIndividual;         // index of best individual getBestIndividual
	  private Random rand; // random generator

    
	  public Population()
	  { 
		Settings settings = Settings.get();
	    this.rand = rand;
	    
		//creates initial population
		individuals = new Individual[settings.InitPopulationSize];
		
			for(int i = 0; i < settings.InitPopulationSize; i++){					
				individuals[i] = new Individual();
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
	  {probabilities = new double[N];
	    double overallProb = 0.0;
	    for (int j = 0; j < N; j++)
	      {
		  // want the smallest fitness value have the greatest probability
		 probabilities[j] = 1.0 / individuals[j].getFitnessValue();	
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
	  private void findBestFitnessValue()
	  {
	    BestFitnessValue = individuals[0].getFitnessValue();
	    BestIndividual = 0;
	  
	    double diff;
	    for (int j = 0; j < N; j++)
	      {
		diff = individuals[j].getFitnessValue() - BestFitnessValue;
		if (diff < 0)
		  {
		    BestFitnessValue = individuals[j].getFitnessValue();
		    BestIndividual = j;
		  }
	      }
	  }
	  
	  public void set(int index, Individual individual) {
		 individuals[index] = individual;  
	  }
	  
	  protected void NextGeneration(){
		Settings settings = Settings.get();
		Individual[] nextPopulation = new Individual[settings.PopulationSize];
		for (int i = 0; i < settings.PopulationSize; i++)
		nextPopulation[i] = new Individual();
		
		int index = 0;
		
		selectToKeep();
			// copy to the next generation
			for (int i = 0 ; i < populationSize; i++, index++)
				nextPopulation[index].set(individuals[population[i]]);
	  
		selectToCrossover();
			BinaryNode crossSelect1 = null;
			BinaryNode crossSelect2 = null;
			Individual offspring1;
			Individual offspring2;{
		
		for (int i = 0; i < Population.populationSize - 1; i += 2){

		    offspring1 = individuals[population[i]].DeepCopyClone();
		    offspring2 = individuals[population[i+1]].DeepCopyClone();
		    
		    crossSelect1 = individuals[population[i]].GetBinaryNodeRandomly();
			crossSelect2 = individuals[population[i+1]].GetBinaryNodeRandomly();
		   
		    offspring1.InsertBinaryNodeRandomly(crossSelect2);
			offspring2.InsertBinaryNodeRandomly(crossSelect1);}
			// copy to the next generation
				for (int i = 0 ; i < populationSize; i++, index++)
				nextPopulation[index].set(individuals[population[i]]);
				
		selectToMutate();
		Individual newMutation;{
			for (int i = 0; i < populationSize; i++){
				newMutation = individuals[population[i]].DeepCopyClone(); 
				newMutation.ModifyIndividualRandomly();
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
