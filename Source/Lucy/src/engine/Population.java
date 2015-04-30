package engine;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Population {
	  private static Logger logger = Logger.getLogger( GeneticProgramManager.class.getName() );
	
	  private int N;  // number of Individuals
	  private long mutationCount;   // Total Cross-overs created for this population
	  private long crossoverCount;  // Total Mutations created for this population
	  private long individualCount; // Total Individuals created for this population
	  private int CrossoverSize; // number of Individuals to crossover 
	  private int MutateSize;    // number of Individuals to mutate 
	  private int CrossOverSelectFromSize;
	  private int MutationSelectFromSize;
	  
	  //private static Individual[] individuals;    // population of Individuals
	  public ArrayList<Individual> individuals;
	  ArrayList<Individual> sortedIndividuals;
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
				
			mutationCount  = 0;
			crossoverCount = 0;
			individualCount = 0;
			  
			individuals = new ArrayList<Individual>();
			sortedIndividuals = new ArrayList<Individual>();
			
			N = settings.InitPopulationSize;
			
			for(int i = 0; i < settings.InitPopulationSize; i++){			
				individuals.add(new Individual());
				logger.log(Level.FINEST, "Individual[" + i + "] - FIT: " + individuals.get(i).getFitnessValue() + " DEPTH: " + individuals.get(i).getIndividualTreeDepth() + " STRING: "  + individuals.get(i).ToString());
			
				individualCount++;
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
			SortThePopulation(N);
	   }
	  
		  
	  /*
	   * This constructor is used to work on the "Next Generation" of a population.
	   */  
	  public Population(Population genX)
	  { 
		  	individuals = new ArrayList<Individual>();
			sortedIndividuals = new ArrayList<Individual>();
			
			Settings settings = Settings.get();
			
			N = settings.PopulationSize;
			
			mutationCount  = genX.mutationCount;
			crossoverCount = genX.crossoverCount;
			individualCount = genX.individualCount;
			
			/*
			* calculate the number of individuals to mutate
			*/
			MutateSize = genX.MutateSize;
			/*
			* calculate the number of individuals to crossover
			*/
			CrossoverSize = genX.CrossoverSize;
			
			//NewPopBaseSize = N - CrossoverSize - MutateSize;
			
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
			 * Sort the new population based on the new individuals, so that they are ordered 
			 * based on the best fitness value
			 */
			SortThePopulation(N);
			
		    // Stub in
	  }
	  
	  /*
	   * Sort the indivudals arraylist based on the best fitness order
	   */
	  private void SortThePopulation(int popSize)
	  {		  
		  /*
		   * This will fill in the population array, and then we will copy
		   */
		  selectBest(popSize);
		  
		  if(popSize > individuals.size()){
			  popSize = individuals.size();
		  }
		  for (int i = 0; i < popSize; i++){
			  if(population[i] < individuals.size())
				  sortedIndividuals.add(individuals.get(population[i]));
			  else
				  System.out.println("Bad Copy: " + population[i]);
		  }
		  
		  /*
		   * Individuals is now sorted...
		   */
		  individuals = sortedIndividuals;		  
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
	  private void selectMutateProb(int n, int mutateSelectSize)
	  {
			// compute individual selection probabilities based on fitness
			computeProbabilities();
					
			mutationList = new int[n];
			
			FitnessSelectionOperator.selectProb(mutateSelectSize, probabilities, mutationList, n, rand);
	  }
	  
	/*
	* probability of being selected 
	*/
	  private void selectCrossOverProb(int n, int crossOverSelectSize)
	  {
			// compute individual selection probabilities based on fitness
			computeProbabilities();			
			
			crossOverList = new int[n];
			
			FitnessSelectionOperator.selectProb(crossOverSelectSize, probabilities, crossOverList, n, rand);
	  }


	/*
	* associate probability with individuals
	*/
	  private void computeProbabilities()
	  {
		  	probabilities = new double[N];
		  	double overallProb = 0.0;
		  	int sizePop = individuals.size();
		  	
		  	//if (sizePop > NewPopBaseSize){
		  	//	sizePop = NewPopBaseSize;
		  	//}
		  	
			for (int j = 0; j < sizePop; j++){
			  // want the smallest fitness value have the greatest probability
				probabilities[j] = 1.0 / individuals.get(j).getFitnessValue();	
				overallProb += probabilities[j];
			}
	  
			// make the probabilities with sum equal to 1
		    for (int j = 0; j < sizePop; j++)
		      probabilities[j] /= overallProb;

		    for (int j = 1; j < sizePop - 1; j++)
		      probabilities[j] += probabilities[j - 1];
	   
		    probabilities[sizePop - 1] = 1.0;
	  }

	  /*
	   * Perform the crossovers for this Generation...
	   */
	  private void Crossover()
	  {
		  CrossoverOperator crosser  = new CrossoverOperator();
		  
		  Individual offspring1;
		  Individual offspring2;
	  
		  selectCrossOverProb(CrossoverSize, individuals.size() - 1);
			
		  for (int i = 0; i < CrossoverSize - 1; i += 2){			
			  //System.out.println("CR[" + i + "]- 1: " + crossOverList[i] + " String: " + individuals.get(crossOverList[i]).ToString());
			  //System.out.println("CR[" + (i+1) + "]- 2: " + crossOverList[i + 1] + " String: " + individuals.get(crossOverList[i+1]).ToString());
			  
			  offspring1 = crosser.CrossOver(crossoverCount,individuals.get(crossOverList[i]), individuals.get(crossOverList[i+1]));
			  crossoverCount++;
			  offspring2 = crosser.CrossOver(crossoverCount,individuals.get(crossOverList[i+1]), individuals.get(crossOverList[i]));
			  crossoverCount++;
			  //System.out.println("CR[" + i + "]- OUT: ");
			  
			  individuals.add(offspring1);
			  individuals.add(offspring2);
			  individualCount += 2;
		  }
	  }
	  
	  /*
	   * Perform all of the Mutation for this Generation...
	   */
	  private void Mutate()
	  {	 
		  Individual newMutation;	
		  MutationOperator Mutator = new MutationOperator();
		  
		  selectMutateProb(MutateSize, individuals.size() - 1); 
		  
		  for (int i = 0; i < MutateSize; i++){	
			  //System.out.println("MU[" + i + "]- 1: " + mutationList[i]);
			newMutation = Mutator.Mutate(mutationCount, individuals.get(mutationList[i]));
			
			individuals.add(newMutation);
			mutationCount++;
			individualCount ++;
		  }			
	  }
	  
	/*
	 * select keepers based on probability, we will need the last generation to 
	 * complete this task
	 */	
	  private void Keepers(Population genX)
	  {	 
		    int numKeepers = N - CrossoverSize - MutateSize;
		    int[] theHashes = new int[numKeepers];
		    int hashCount = 0;
		    boolean hashTagFound = false;
		    int newHashCode;
		    GP_Random engine = GP_Random.get();
		    int randIndex;
		    MutationOperator Mutator = new MutationOperator();
		    
		    // Don't need here with sorted array list from last Gen 
		    // selectBest(N - CrossoverSize - MutateSize);
		    
			for (int i = 0 ; i < numKeepers; i++){
				/*
				 * Since we are avoid duplicates, there is a chance that we
				 * may run out of Individuals.
				 */
				if (i < genX.individuals.size()){
					hashTagFound = false;
					newHashCode = genX.individuals.get(i).HashCode();
					/*
					 * Search the array and see if we already have this individual in 
					 * the new population, if so, don't add it, we don't want to fill
					 * up the new population with duplicates.
					 */
					for(int j=0; j < hashCount; j++){
						
						if( newHashCode == theHashes[j]){
							hashTagFound = true;
							break;
						}
					}
					/*
					 * Copy the desired individuals from the last generation to the new
					 * generation, if it was not already in there...
					 */
					if(hashTagFound == false){
						individuals.add(genX.individuals.get(i));
						theHashes[hashCount] = newHashCode;
						hashCount++;
					}
				}else{
					break; /* We are already out of new individuals */
				}
			}
			
			if(hashCount < numKeepers){
				//System.out.println("We are screwed");
				
				for(int i=0; i < (numKeepers - hashCount); i++){
					randIndex = engine.NextInt(genX.individuals.size());
					//individuals.add(genX.individuals.get(randIndex));
					individuals.add(Mutator.Mutate(mutationCount, genX.individuals.get(randIndex)));
					mutationCount++;
					//newMutation = Mutator.Mutate(mutationCount, individuals.get(mutationList[i]));
				}
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
	  	
	  public long getIndividualCount(){
		  return individualCount;
	  }
	  
	  public long getCrossOVerCount(){
		  return crossoverCount;
	  }
	  
	  public long getMutationCount(){
		  return mutationCount;
	  }
	  
	  public int getPopulationSize(){
		  return individuals.size();
	  }
	  
	  public Individual getAnIndividual(int index){
	
		  if(index > individuals.size())
			  index = individuals.size() - 1;
		  
		  return individuals.get(index);
	  }
	 
}
	  
