package engine;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneticProgramManager {
	
	private static Logger logger = Logger.getLogger( GeneticProgramManager.class.getName() );
	public float FitnessValue;
	public int GenerationsValue;
	private long startTime;
	public Population currentPopulation;
	
	public String execute()
	{
		String result = "";
		
		try 
		{
			Settings settings = Settings.get();
			
			startTime = System.currentTimeMillis();
			logger.log(Level.CONFIG, "ACTUAL START TIME: " + MessageFormatter.dateTime(startTime));
			
			long testStop = startTime + TimeUnit.SECONDS.toMillis(Settings.get().MaxDuration);
			logger.log(Level.CONFIG, "TARGET STOP TIME: " + MessageFormatter.dateTime(testStop));
			
			float lastFitnessValue = 0;
			int generation = 1;
			int staleiterations = 1;
			
			// Initial Population
			currentPopulation = new Population();
			
			GenerationsValue = 1;
			
			// only show when it changes
			FitnessValue = currentPopulation.getBestIndividual().getFitnessValue();
			lastFitnessValue = FitnessValue;

			logger.log(Level.FINE, 
					"BESTFIT FOR GENERATION " + generation +
					": " + currentPopulation.getBestIndividual().ToString() + 
					", FITNESS: " + FitnessValue +
					", TIME: " + ((System.currentTimeMillis() - startTime) / 1000)
					);
			
			StringBuffer sb = new StringBuffer("POPULATION FOR GENERATION: " + generation + "\n");
			for(int i=1; i <= currentPopulation.individuals.size(); i++)
			{
				sb.append("INDIVIDUAL " + (i) + ": " + 
						currentPopulation.individuals.get(i-1).ToString() + 
						", FITNESS: " + currentPopulation.individuals.get(i-1).getFitnessValue() + "\n");
			}
			logger.log(Level.FINER, sb.toString());
			
			while ((System.currentTimeMillis() < testStop) && 
					currentPopulation.getBestIndividual().getFitnessValue() != 0)
			{			
				
				if (settings.MaxIterations != 0 && generation > settings.MaxIterations)
					break;
				
				// next generation
				currentPopulation = new Population(currentPopulation);
				generation += 1;	
				GenerationsValue = generation;
				
				// Log current population - FINER
				sb = new StringBuffer("POPULATION FOR GENERATION: " + generation + "\n");
				for(int i=1; i <= currentPopulation.individuals.size(); i++)
				{
					sb.append("INDIVIDUAL " + (i) + ": " + 
							currentPopulation.individuals.get(i-1).ToString() + 
							", FITNESS: " + currentPopulation.individuals.get(i-1).getFitnessValue() + "\n");
				}
				logger.log(Level.FINER, sb.toString());
				
				// Check Fitness
				FitnessValue = currentPopulation.getBestIndividual().getFitnessValue();
				
				
				// Fitness Changed - good
				if (FitnessValue != lastFitnessValue)
				{
					staleiterations = 1;
					lastFitnessValue = FitnessValue;
					logger.log(Level.FINE, 
							"BESTFIT FOR GENERATION " + generation +
							": " + currentPopulation.getBestIndividual().ToString() + 
							", FITNESS: " + FitnessValue +
							", TIME: " + ((System.currentTimeMillis() - startTime) / 1000)
							);
				}
				// Otherwise check to see if population is stale, if so, regenerate population
				else
				{
					staleiterations++;
					if (staleiterations > settings.StaleInjectionTest)
					{
						// Log event - FINE
						logger.log(Level.FINE, 
								"STALE POPULATION ENCOUNTERED AT GENERATION " + generation +
								", REGENERATING POPULATION" +
								", TIME: " + ((System.currentTimeMillis() - startTime) / 1000)
								);
						
						currentPopulation = new Population(settings.StaleInjectionPreserve, currentPopulation);
						staleiterations = 1;
						generation += 1;
						GenerationsValue = generation;
						
						// Log stale population - FINER
						sb = new StringBuffer("REGENERATED POPULATION: " + generation + "\n");
						for(int i=1; i <= currentPopulation.individuals.size(); i++)
						{
							sb.append("INDIVIDUAL " + (i) + ": " + 
									currentPopulation.individuals.get(i-1).ToString() + 
									", FITNESS: " + currentPopulation.individuals.get(i-1).getFitnessValue() + "\n");
						}
						logger.log(Level.FINER, sb.toString());
						
					}
				}

			}

			logger.log(Level.CONFIG, "ACTUAL STOP TIME: " + MessageFormatter.dateTime(System.currentTimeMillis()));
			
			result = "RESULT: " + currentPopulation.getBestIndividual().ToString() + "\n" +
					"FITNESS: " + currentPopulation.getBestIndividual().getFitnessValue() + "\n" +
					"NUMBER OF GENERATIONS: " + generation + "\n" +
					"NUMBER OF SECONDS: " + ((System.currentTimeMillis() - startTime) / 1000);
			logger.log(Level.CONFIG, result);

		} 
		catch (Exception e) 
		{
			logger.log(Level.SEVERE,MessageFormatter.exception(e));
		}
		
		return result;
	}
	
}
