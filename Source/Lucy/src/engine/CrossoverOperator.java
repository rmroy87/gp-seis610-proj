package engine;

public class CrossoverOperator {

		BinaryNode crossSelect1 = null;
		BinaryNode crossSelect2 = null;
		Individual parent1 = new Individual();
		Individual parent2 = new Individual();
		Individual offspring1 = parent1.DeepCopyClone();
		Individual offspring2 = parent2.DeepCopyClone();{
	
		crossSelect1 = parent1.GetBinaryNodeRandomly();
		crossSelect2 = parent2.GetBinaryNodeRandomly();
		
		offspring1.InsertBinaryNodeRandomly(crossSelect2);
		offspring2.InsertBinaryNodeRandomly(crossSelect1);
	}
}


