package engine;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.thoughtworks.xstream.XStream;

public class Training extends Config
{
	private static final Logger logger = Logger.getLogger( Training.class.getName() );
	
	private static Training instance = null;
	
	public ArrayList<OrderedPair> OrderedPairs;
	
	protected Training() {
		// Exists only to defeat instantiation.
	}
	
	public static Training get() {
		return get("training");
	}
	
	public static Training get(String configName) {
		
		if(instance == null) {
			try
			{		
				XStream xstream = new XStream();
				xstream.alias("Training", Training.class);
				xstream.alias("OrderedPair", OrderedPair.class);
															
				instance = (Training)xstream.fromXML(read(configName));
				
				logTraining(instance);
			}
			catch (Exception ex)
			{		    
				logger.log( 
						Level.SEVERE, 
						MessageFormatter.exception(String.format("Failed to load training from %s.xml",configName), ex));
				return null;
			}
		}
		return instance;
	}
	
	private static void logTraining(Training training)
	{
	    StringBuilder sb = new StringBuilder("\nTraining Data: \n");
	    
		for(OrderedPair pair : training.OrderedPairs)  {
			   sb.append("(" + pair.X + "," + pair.Y + ")\n");
		}
		
		logger.log(Level.CONFIG, sb.toString());
	}
}
	
		

