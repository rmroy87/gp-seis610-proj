package engine;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;



public class GP_Random {
	private static final Logger logger = Logger.getLogger( Settings.class.getName() );
	private static GP_Random instance = null;
	private static Random engine;
	
	protected GP_Random() {
		// Exists only to defeat instantiation.
	}
	
	
	public static GP_Random get() {
		
		if(instance == null) {
			try
			{	
				instance = new GP_Random();
				engine =  new Random();				
			}
			catch (Exception ex)
			{		    
				logger.log( 
						Level.SEVERE, 
						MessageFormatter.exception(String.format("Failed to Initialize Random"), ex));
				throw ex;
			}
		}
		return instance;
	}
	
	
	public int NextInt(int range){
		int nextInteger;
		
		if(range > 0){
			nextInteger = engine.nextInt(range);
			//nextInteger = engine.nextInt(100);
			//nextInteger = range - (nextInteger % range) - 1;
		}else{
			nextInteger = 0;
		}	
		
		//System.out.print(String.format("GP_R = %d - Range = %d\n", nextInteger, range));
		
		return nextInteger;
	}
}
