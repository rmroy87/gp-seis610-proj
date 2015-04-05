package engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class GeneticProgramManager {
	
	private static final Logger logger = Logger.getLogger( GeneticProgramManager.class.getName() );
	
	public String execute()
	{
		String result = "";
		
		try 
		{
			long testStop = initializeEngine();
			
			// Initial Population
			StubbedPopulation pop = new StubbedPopulation();
			pop.evaluatePopulationFitness();
			for(Individual i : pop)  {
				logger.log(Level.FINER, "Individual: " + i.ToString() + ", Fitness = " + i.getFitnessValue() + "\n");
			}
			
			while (System.currentTimeMillis() < testStop && pop.getBestIndividual().getFitnessValue() != 0)
			{			
				
				// next generation
				pop = new StubbedPopulation(pop);
				pop.evaluatePopulationFitness();
				for(Individual i : pop)  {
					logger.log(Level.FINER, "Individual: " + i.ToString() + ", Fitness = " + i.getFitnessValue() + "\n");
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
		LogManager.getLogManager().readConfiguration(new FileInputStream("./logging.properties"));
		Settings.get();
		Training.get();
		
		DateFormat dateFormat = new SimpleDateFormat(" yyyy/MM/dd HH:mm:ss");
		
		long start = System.currentTimeMillis();
		logger.log(Level.FINE, "Actual start time: " + MessageFormatter.dateTime(start));
		
		long stop = start + TimeUnit.SECONDS.toMillis(Settings.get().MaxDuration);
		logger.log(Level.FINE, "Target stop time: " + MessageFormatter.dateTime(stop));

		return stop;
	}


}
