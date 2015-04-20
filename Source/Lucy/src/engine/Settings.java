package engine;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.thoughtworks.xstream.XStream;

public class Settings extends Config
{
	private static final Logger logger = Logger.getLogger( Settings.class.getName() );
	
	private static Settings instance = null;
	
	public ArrayList<Operator> Operators;
	public ArrayList<Operand> Operands;
	public int InitPopulationSize;
	public int PopulationSize;
	public float MutationProbability;
	public float CrossoverProbability;
	public int MaxDuration;
	public int MaxIterations;
	public int MaxTreeDepth;
	public int KeeperThreshhold;
	public ArrayList<OrderedPair> Training;
	public static String lastFile;
	
	protected Settings() {
		// Exists only to defeat instantiation.
	}
	
	public static Settings get() {
		return get(".\\settings.xml");
	}
	
	public static Settings get(String configName) {
		
		if(instance == null) {
			try
			{		
				XStream xstream = new XStream();
				xstream.alias("Settings", Settings.class);
				xstream.alias("Operator", Operator.class);
				xstream.alias("Operand", Operand.class);
				xstream.alias("OrderedPair", OrderedPair.class);
															
				instance = (Settings)xstream.fromXML(read(configName));
				File file = new File(configName);
				lastFile = file.getAbsolutePath();
				
				logSettings(instance);
			}
			catch (Exception ex)
			{		    
				logger.log( 
						Level.SEVERE, 
						MessageFormatter.exception(String.format("Failed to load settings from %s",configName), ex));
				throw ex;
			}
		}
		return instance;
	}
	
	public static Settings reget(String fileName) {
		instance = null;
		return get(fileName);
	}
	
	private static void logSettings(Settings settings)
	{
	    StringBuilder sb = new StringBuilder("\nSettings: \n");
	    sb.append("  InitPopulationSize: " + settings.InitPopulationSize + "\n");
	    sb.append("  PopulationSize: " + settings.PopulationSize + "\n");
	    sb.append("  MutationProbability: " + settings.MutationProbability + "\n");
	    sb.append("  CrossoverProbability: " + settings.CrossoverProbability + "\n");
	    sb.append("  MaxDuration: " + settings.MaxDuration + "\n");
	    sb.append("  MaxIterations: " + settings.MaxIterations + "\n");
	    sb.append("  MaxTreeDepth: " + settings.MaxTreeDepth + "\n");
	    sb.append("  KeeperThreshhold: " + settings.KeeperThreshhold + "\n");

	    sb.append("  Operands: ");
		for(Operand operand : settings.Operands)  {
			   sb.append(operand.getValue() + ',');
		}
		sb.append("\n");
		
	    sb.append("  Operators: ");
		for(Operator operator : settings.Operators)  {
			   sb.append(operator.getValue() + ',');
		}
		sb.append("\n");
		
		logger.log(Level.CONFIG, sb.toString());
	}
}
	
		

