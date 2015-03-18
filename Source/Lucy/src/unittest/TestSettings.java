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


}
