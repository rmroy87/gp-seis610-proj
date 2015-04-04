package unittest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.LogManager;

import engine.*;

import org.junit.Test;

public class TestTraining {
	
	@Test
	public void TestSettingsGet() throws SecurityException, FileNotFoundException, IOException {
		
		LogManager.getLogManager().readConfiguration(new FileInputStream("./logging.properties"));
				
		Training training = Training.get();
		
		if (training == null)
		{
			org.junit.Assert.fail("This file did not load successfully, so the test failed");
			return;
		}
		
	}
	
}
