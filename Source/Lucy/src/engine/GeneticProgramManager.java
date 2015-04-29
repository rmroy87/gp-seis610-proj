package engine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneticProgramManager {
	
	private static Logger logger = Logger.getLogger( GeneticProgramManager.class.getName() );
	public float FitnessValue;
	public int GenerationsValue;
	private long startTime;
	
	public String execute()
	{
		String result = "";
		
		try 
		{
			long testStop = initializeEngine();
			float lastFitnessValue = 0;
			int generation = 1;
			
			// Initial Population
			Population pop = new Population();
			
			GenerationsValue = 0;
			
			// only show when it changes
			FitnessValue = pop.getBestIndividual().getFitnessValue();
			lastFitnessValue = FitnessValue;

			logger.log(Level.FINE, 
					"BESTFIT FOR GENERATION " + generation +
					": " + pop.getBestIndividual().ToString() + 
					", FITNESS: " + FitnessValue +
					", TIME: " + ((System.currentTimeMillis() - startTime) / 1000)
					);
			
			StringBuffer sb = new StringBuffer("POPULATION FOR GENERATION: " + generation + "\n");
			for(int i=1; i <= pop.individuals.size(); i++)
			{
				sb.append("INDIVIDUAL " + (i-1) + ": " + 
						pop.individuals.get(i-1).ToString() + 
						", FITNESS: " + pop.individuals.get(i-1).getFitnessValue() + "\n");
			}
			logger.log(Level.FINER, sb.toString());
			
			while ((System.currentTimeMillis() < testStop) && 
					pop.getBestIndividual().getFitnessValue() != 0)
			{			
				
				if (Settings.get().MaxIterations != 0 && generation > Settings.get().MaxIterations)
					break;
				
				// next generation
				generation += 1;
				pop = new Population(pop);
				FitnessValue = pop.getBestIndividual().getFitnessValue();
				GenerationsValue = generation;
				
				// Debug Logging - only show if fitness changes
				FitnessValue = pop.getBestIndividual().getFitnessValue();
				if (FitnessValue != lastFitnessValue)
				{
					lastFitnessValue = FitnessValue;
					logger.log(Level.FINE, 
							"BESTFIT FOR GENERATION " + generation +
							": " + pop.getBestIndividual().ToString() + 
							", FITNESS: " + FitnessValue +
							", TIME: " + ((System.currentTimeMillis() - startTime) / 1000)
							);
				}
				
				sb = new StringBuffer("POPULATION FOR GENERATION: " + generation + "\n");
				for(int i=1; i <= pop.individuals.size(); i++)
				{
					sb.append("INDIVIDUAL " + (i-1) + ": " + 
							pop.individuals.get(i-1).ToString() + 
							", FITNESS: " + pop.individuals.get(i-1).getFitnessValue() + "\n");
				}
				logger.log(Level.FINER, sb.toString());
			}

			logger.log(Level.CONFIG, "ACTUAL STOP TIME: " + MessageFormatter.dateTime(System.currentTimeMillis()));
			
			result = "RESULT: " + pop.getBestIndividual().ToString() + "\n" +
					"FITNESS: " + pop.getBestIndividual().getFitnessValue() + "\n" +
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
	
	private long initializeEngine() throws SecurityException, FileNotFoundException, IOException
	{
		
		Settings.get();
		
		startTime = System.currentTimeMillis();
		logger.log(Level.CONFIG, "ACTUAL START TIME: " + MessageFormatter.dateTime(startTime));
		
		long stop = startTime + TimeUnit.SECONDS.toMillis(Settings.get().MaxDuration);
		logger.log(Level.CONFIG, "TARGET STOP TIME: " + MessageFormatter.dateTime(stop));

		return stop;
	}


}
