package engine;

import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;

public class Settings extends Config
{
	private static Settings instance = null;
	
	public ArrayList<Operator> Operators;
	public ArrayList<Operand> Operands;
	public int i;
	
	protected Settings() {
		// Exists only to defeat instantiation.
	}
	
	public static Settings get() {
		return get("settings.config");
	}
	
	public static Settings get(String filename) {
		
		if(instance == null) {
			try
			{
				XStream xstream = new XStream();
				xstream.alias("Settings", Settings.class);
				xstream.alias("Operator", Operator.class);
				xstream.alias("Operand", Operand.class);
							
				instance = (Settings)xstream.fromXML(read(filename));
			}
			catch (Exception ex)
			{
				return null;
			}
		}
		return instance;
			

	}
}
