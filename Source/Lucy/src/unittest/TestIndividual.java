package unittest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.LogManager;

import engine.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestIndividual {
	Individual[] population = new Individual[500];
	float calcFitness;
	@Test
	public void test() throws SecurityException, FileNotFoundException, IOException {
		LogManager.getLogManager().readConfiguration(new FileInputStream("./logging.properties"));
		
		Settings settings = Settings.get();
		
		System.out.println("Individual Unit Test: (The Population)\n");
		
		for(int i = 0; i < settings.InitPopulationSize; i = i+1){
		//for(int i = 0; i < 10; i = i+1){
					
			population[i] = new Individual();
			System.out.print(String.format("Individual[%d]--> f(x) = ", i));			
			System.out.print(population[i].ToString());
			System.out.println();
			calcFitness = population[i].getFitnessValue();
			System.out.print(String.format("Individual[%d]--> Fitness = %4.2f ",i, calcFitness ));
			System.out.println();
			System.out.println();
		}		
	}
}
