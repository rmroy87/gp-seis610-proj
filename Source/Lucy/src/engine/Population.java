package engine;

public class Population {
    	
	    int UseInitPopulation= 1;
	    
		Individual[] individuals;
	    
	    // Build the population
	    public Population() {
	    	
	    	
	    	if(UseInitPopulation != 0) // Checking to see if the initial population has been built yet
	    		{individuals = new Individual[Settings.InitPopulationSize];
	    		// Loop and create individuals
	            for (int i = 0; i < Settings.InitPopulationSize; i++) {
	                Individual NewIndividual = new Individual();
	                NewIndividual.CreateIndividual();
	                SetIndividual(i, NewIndividual);
	                UseInitPopulation=0;}
	    		} else    //Initial population has already been created so use Population size
	    		{individuals = new Individual[Settings.PopulationSize];
	    		// Loop and create individuals
	            for (int i = 0; i < Settings.PopulationSize; i++) {
	                Individual NewIndividual = new Individual();
	                NewIndividual.CreateIndividual();
	                SetIndividual(i, NewIndividual);
	            }
	    	}    
	    }

 	    
		// Get individual and fitness
	    public Individual GetIndividual(int index) {
	        return individuals[index];
	    }

	  //sort individuals by fitness in array
        public Individual getFittest() {
            java.util.Arrays.sort(individuals, new java.util.Comparator<Individual>() {
                @Override
                public int compare(Individual i1, Individual i2) {
                    // return -1 if i1 < i2, 1 if i1 > i2, or 0 if equal
                    if (i1.GetFitness() > i2.GetFitness())
                        return 1;
                    if (i1.GetFitness() < i2.GetFitness())
                        return -1;
                    else
                        return 0;
                }});
            return individuals[0];
        }
        


	    // Save individual
	    public void SetIndividual(int index, Individual indiv) {
	        individuals[index] = indiv;
	    }
}