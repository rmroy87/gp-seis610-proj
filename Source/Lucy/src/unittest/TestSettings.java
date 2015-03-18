package unittest;

import engine.*;
import org.junit.Test;

public class TestSettings {

	@Test
	public void LoadOperands() {
		
		Settings settings = Settings.get();
		
		if (settings == null)
		{
			org.junit.Assert.fail("Application failed to load settings");
			return;
		}
		
		else
		{
			
			if (settings.Operands == null || settings.Operands.size() == 0)
			{
				org.junit.Assert.fail("No operands in settings");
				return;
			}
			
			System.out.print("Setting operands: ");
			
			for(Operand operand : settings.Operands)  {
				   System.out.print(operand.getValue() + ',');
			}
			
			System.out.println();
		}
		
	}
	
	@Test
	public void LoadOperators() {
		
		Settings settings = Settings.get();
		
		if (settings == null)
		{
			org.junit.Assert.fail("Application failed to load settings");
			return;
		}
		
		else
		{
			
			if (settings.Operators == null || settings.Operators.size() == 0)
			{
				org.junit.Assert.fail("No Operators in settings");
				return;
			}
			
			System.out.print("Setting operators: ");
			
			for(Operator operator : settings.Operators)  {
				   System.out.print(operator.getValue() + ',');
			}
			
			System.out.println();
		}
		
	}

	@Test
	public void LoadSettings() {
		
		Settings settings = Settings.get();
		
		if (settings == null)
		{
			org.junit.Assert.fail("Application failed to load settings");
			return;
		}
		
		else
		{
			// InitPopulationSize
			if (settings.InitPopulationSize == 0)
			{
				org.junit.Assert.fail("No value for InitPopulationSize");
			}
			System.out.println("InitPopulationSize: " + settings.InitPopulationSize);
			
			// PopulationSize
			if (settings.PopulationSize == 0)
			{
				org.junit.Assert.fail("No value for PopulationSize");
			}
			System.out.println("PopulationSize: " + settings.PopulationSize);
			
			// MutationProbability
			if (settings.MutationProbability == 0)
			{
				org.junit.Assert.fail("No value for MutationProbability");
			}
			System.out.println("MutationProbability: " + settings.MutationProbability);
			
			// CrossoverProbability
			if (settings.CrossoverProbability == 0)
			{
				org.junit.Assert.fail("No value for CrossoverProbability");
			}
			System.out.println("CrossoverProbability: " + settings.CrossoverProbability);
			
			// MaxDuration
			if (settings.MaxDuration == 0)
			{
				org.junit.Assert.fail("No value for MaxDuration");
			}
			System.out.println("MaxDuration: " + settings.MaxDuration);
			
			// MaxIterations
			if (settings.MaxIterations == 0)
			{
				org.junit.Assert.fail("No value for MaxIterations");
			}
			System.out.println("MaxIterations: " + settings.MaxIterations);
			
			// MaxTreeDepth
			if (settings.MaxTreeDepth == 0)
			{
				org.junit.Assert.fail("No value for MaxTreeDepth");
			}
			System.out.println("MaxTreeDepth: " + settings.MaxTreeDepth);
			
			// KeeperThreshhold
			if (settings.KeeperThreshhold == 0)
			{
				org.junit.Assert.fail("No value for KeeperThreshhold");
			}
			System.out.println("KeeperThreshhold: " + settings.KeeperThreshhold);

		}
		
	}

}
