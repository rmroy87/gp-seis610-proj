package engine;

public class CrossoverOperator {

		BinaryNode crossSelect1 = null;
		BinaryNode crossSelect2 = null;
		Individual offspring1;
		Individual offspring2;{
		
		for (int i = 0; i < Population.populationSize - 1; i += 2){

		    offspring1 = individuals[population[i]].DeepCopyClone();
		    offspring2 = individuals[population[i+1]].DeepCopyClone();
		    
		    crossSelect1 = individuals[population[i]].GetBinaryNodeRandomly();
			crossSelect2 = individuals[population[i+1]].GetBinaryNodeRandomly();
		   
		    offspring1.InsertBinaryNodeRandomly(crossSelect2);
			offspring2.InsertBinaryNodeRandomly(crossSelect1);
		}


	}
}
