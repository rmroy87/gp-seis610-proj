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
	float bestFitness = (float)1000.0;
	int bestIndex = 0;
	int bestJ = 0;
	String bestEquation;
	float calcFitness;
	
	@Test
	public void test() throws SecurityException, FileNotFoundException, IOException {
		LogManager.getLogManager().readConfiguration(new FileInputStream("./logging.properties"));
		
		//Settings settings = Settings.get();
		
		TestMutation();
		
		//TestCreateEntirePopulation(500);
		
		TestCrossOver();
		
		//TestPopulationInLoopUntilZeroFitness(500, 10000);		
		
	}
	
	/*
	 * Unit test to test the basic Mutation function.
	 */
	public void TestMutation(){
	
		Individual parent1 = new Individual();
		Individual parent2 = new Individual();
		Individual mutatedIndividual1 = parent1.DeepCopyClone();
		Individual mutatedIndividual2 = parent2.DeepCopyClone();
		
		System.out.println(String.format("\nIndividual - Unit Test: (Mutate Individuals)\n"));
		
		System.out.print(String.format("Individual 1 ------> f(x) = %s [Fitness = %f]\n", parent1.ToString(), parent1.getFitnessValue() ));
		System.out.print(String.format("Individual 2 ------> f(x) = %s [Fitness = %f]\n", parent2.ToString(), parent2.getFitnessValue() ));
		System.out.print(String.format("Cloned 1 ----------> f(x) = %s [Fitness = %f]\n", mutatedIndividual1.ToString(), mutatedIndividual1.getFitnessValue() ));
		System.out.print(String.format("Cloned 2 ----------> f(x) = %s [Fitness = %f]\n\n", mutatedIndividual2.ToString(), mutatedIndividual2.getFitnessValue() ));
		
		mutatedIndividual1.ModifyIndividualRandomly();
		mutatedIndividual2.ModifyIndividualRandomly();
		
				
		System.out.print(String.format("Mutated Clone 1 ---> f(x) = %s [Fitness = %f]\n", 
				mutatedIndividual1.ToString(), mutatedIndividual1.getFitnessValue() ));
		System.out.print(String.format("Mutated Clone 2 ---> f(x) = %s [Fitness = %f]\n\n",
				mutatedIndividual2.ToString(), mutatedIndividual2.getFitnessValue() ));
				
	}
	
	/*
	 *  Unit Test to test the Cross-Over function
	 */
	public void TestCrossOver(){
		BinaryNode crossSelect1 = null;
		BinaryNode crossSelect2 = null;
		Individual parent1 = new Individual();
		Individual parent2 = new Individual();
		Individual offspring1 = parent1.DeepCopyClone();
		Individual offspring2 = parent2.DeepCopyClone();
		
		System.out.println(String.format("\nIndividual - Unit Test: (Cross-Over Individuals)\n"));
		
		System.out.print(String.format("Parent 1 -----> f(x) = %s [Fitness = %f]\n", parent1.ToString(), parent1.getFitnessValue() ));
		System.out.print(String.format("Parent 2 -----> f(x) = %s [Fitness = %f]\n", parent2.ToString(), parent2.getFitnessValue() ));
		System.out.print(String.format("Cloned 1 -----> f(x) = %s [Fitness = %f]\n", offspring1.ToString(), offspring1.getFitnessValue() ));
		System.out.print(String.format("Cloned 2 -----> f(x) = %s [Fitness = %f]\n\n", offspring2.ToString(), offspring2.getFitnessValue() ));
		
		crossSelect1 = parent1.GetBinaryNodeRandomly();
		crossSelect2 = parent2.GetBinaryNodeRandomly();
		
		System.out.print(String.format("Cross Select 1 --> f(x) = %s\n", crossSelect1.ResolveNodeString()) );
		System.out.print(String.format("Cross Select 2 --> f(x) = %s\n\n", crossSelect2.ResolveNodeString() ));
		
		offspring1.InsertBinaryNodeRandomly(crossSelect2);
		offspring2.InsertBinaryNodeRandomly(crossSelect1);
		
		System.out.print(String.format("Offspring 1 --> f(x) = %s [Fitness = %f]\n", offspring1.ToString(), offspring1.getFitnessValue() ));
		System.out.print(String.format("Offspring 2 --> f(x) = %s [Fitness = %f]\n\n", offspring2.ToString(), offspring2.getFitnessValue() ));
	
	}
	/*
	 * Test to create an entire Population of the size passed in, max of 500
	 */
	public void TestCreateEntirePopulation( int populationSize){
			
		if( populationSize > 500){
			populationSize = 500;
		}
		
		System.out.println(String.format("\nIndividual - Unit Test: (Create The Population) - Size = %d\n", populationSize));
		
		
		for(int i = 0; i < populationSize; i = i+1){					
			population[i] = new Individual();
			System.out.print(String.format("Individual[%d]--> f(x) = ", i));			
			System.out.print(population[i].ToString());
			System.out.println();
			calcFitness = population[i].getFitnessValue();
			System.out.print(String.format("Individual[%d]--> Fitness = %4.2f ",i, calcFitness ));
			System.out.println();
			System.out.println();
			
			if(calcFitness < bestFitness){
				bestFitness = calcFitness;
				bestIndex = i;			
				bestEquation = population[i].ToString();
			}		 
		}
		System.out.println();
		System.out.print(String.format("\nBest Fitness Found = %4.2f, Index = %d ", bestFitness, bestIndex ));
		System.out.println();		
	}
	
	
	
	/*
	 * Create Populations over and over until a zero Fitness is found
	 */
	public void TestPopulationInLoopUntilZeroFitness(int populationSize, int maxLoopCount){
		
		if( populationSize > 500){
			populationSize = 500;
		}
		System.out.println(String.format("Individual - Unit Test: (Loop Until Fitness == ZERO) - Size = %d, Loop = %d\n", 
				populationSize, maxLoopCount));
		
		for(int j=0; j < maxLoopCount; j = j+1){
			
			for(int i = 0; i < populationSize; i = i+1){			
						
				population[i] = new Individual();
				//System.out.print(String.format("Individual[%d]--> f(x) = ", i));			
				//System.out.print(population[i].ToString());
				//System.out.println();
				calcFitness = population[i].getFitnessValue();
				//System.out.print(String.format("Individual[%d]--> Fitness = %4.2f ",i, calcFitness ));
				//System.out.println();
				//System.out.println();
				
				if(calcFitness < bestFitness){
					bestFitness = calcFitness;
					bestIndex = i;
					bestJ = j;
					bestEquation = population[i].ToString();
					if(bestFitness == 0){
						System.out.println("FITNESS == ZERO found!!");
						break;
					}
				}			 
			}
			
			System.out.print(String.format("Loop - Best Fitness Found = %f, Index = %d, Loop = %d\n ", bestFitness, bestIndex, bestJ ));
			
			
			for(int i = 0; i < populationSize; i = i+1){
				population[i] = null;
			}
			
			if(bestFitness == 0.00){
				break;
			}
		}
		
		System.out.println();
		System.out.print(String.format("\nComplete Test - Best Fitness Found = %f, Index = %d, Loop = %d - String = %s", 
				bestFitness, bestIndex, bestJ, bestEquation ));
		System.out.println();
	}
	
}
