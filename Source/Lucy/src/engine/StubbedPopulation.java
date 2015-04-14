package engine;

import java.util.ArrayList;

public class StubbedPopulation extends ArrayList<Individual> {
	
	private int bestIndividual;
	
	// to handle initial population
	public StubbedPopulation()
	{
		// made up code to produce 3 individuals
		this.add(new Individual());
		this.add(new Individual());
		this.add(new Individual());
	}
	
	// To handle next generation
	public StubbedPopulation(StubbedPopulation population)
	{
		// from selection
		this.add(population.getBestIndividual());
		
		// from mutation and crossover
		this.add(new Individual());
		this.add(new Individual());
	}
	
	/*
	public void evaluatePopulationFitness()
	{
		for(Individual i : this)  {
			   i.
		}
		// for stub purposes, I am assuming bestIndividual is the first
		bestIndividual = 1;
	}
	*/
	
	public Individual getBestIndividual()
	{
		return this.get(bestIndividual);
	}


}
