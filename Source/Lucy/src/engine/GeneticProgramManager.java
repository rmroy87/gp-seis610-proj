package engine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneticProgramManager {
	
	private static Logger logger = Logger.getLogger( GeneticProgramManager.class.getName() );
	
	public String execute()
	{
		String result = "";
		
		try 
		{
			long testStop = initializeEngine();
			
			int generation = 1;
			
			// Initial Population
			Population pop = new Population();
			
			// Debug Logging
			for(Individual i : pop)  {
				logger.log(Level.FINER, "Generation: " + generation + ", Individual: " + i.ToString() + ", Fitness = " + i.getFitnessValue() + "\n");
			}
			
			while (System.currentTimeMillis() < testStop && pop.getBestIndividual().getFitnessValue() != 0)
			{			
				
				// next generation
				generation += 1;
				pop = new Population(pop);
				
				// Debug Logging
				for(Individual i : pop)  {
					logger.log(Level.FINER, "Generation: " + generation + ", Individual: " + i.ToString() + ", Fitness = " + i.getFitnessValue() + "\n");
				}
				
			}

			logger.log(Level.FINE, "Actual stop time: " + MessageFormatter.dateTime(System.currentTimeMillis()));
			
			result = "Best Individual: " + pop.getBestIndividual().ToString()+ ", Fitness = " + pop.getBestIndividual().getFitnessValue();
			logger.log(Level.FINE, result);

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
		
		long start = System.currentTimeMillis();
		logger.log(Level.FINE, "Actual start time: " + MessageFormatter.dateTime(start));
		
		long stop = start + TimeUnit.SECONDS.toMillis(Settings.get().MaxDuration);
		logger.log(Level.FINE, "Target stop time: " + MessageFormatter.dateTime(stop));

		return stop;
	}


}
