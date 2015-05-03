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
	public String Function;
	public int StaleInjectionTest;
	public int StaleInjectionPreserve;
	
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
	    StringBuilder sb = new StringBuilder("\nSETTINGS: \n");
	    sb.append("  INIT POPULATION SIZE: " + settings.InitPopulationSize + "\n");
	    sb.append("  POPULATION SIZE: " + settings.PopulationSize + "\n");
	    sb.append("  MUTATION PROBABILITY: " + settings.MutationProbability + "\n");
	    sb.append("  CROSSOVER PROBABILITY: " + settings.CrossoverProbability + "\n");
	    sb.append("  MAX DURATITION: " + settings.MaxDuration + "\n");
	    sb.append("  MAX ITERATIONS: " + settings.MaxIterations + "\n");
	    sb.append("  MAX TREE DEPTH: " + settings.MaxTreeDepth + "\n");
	    sb.append("  KEEPER THRESHOLD: " + settings.KeeperThreshhold + "\n");
	    sb.append("  TARGET FUNCTION: " + settings.Function + "\n");
	    sb.append("  STALE INJECTION TEST: " + settings.StaleInjectionTest + "\n");
	    sb.append("  STALE INJECTION PRESERVE: " + settings.StaleInjectionPreserve + "\n");

	    sb.append("  OPERATNDS: ");
		for(Operand operand : settings.Operands)  {
			   sb.append(operand.getValue() + ',');
		}
		sb.append("\n");
		
	    sb.append("  OPERATORS: ");
		for(Operator operator : settings.Operators)  {
			   sb.append(operator.getValue() + ',');
		}
		sb.append("\n");
		
	    sb.append("  TRAINING DATA: \n");
		for(OrderedPair pair : settings.Training)  {
			   sb.append("		(" + pair.X + ',' + pair.Y + ")\n");
		}
		sb.append("\n");
		
		logger.log(Level.CONFIG, sb.toString());
	}
}
	
		

