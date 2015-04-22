package engine;

public class MutationOperator {

	Individual newMutation;{
	for (int i = 0; i < populationSize; i++){
		newMutation = individuals[population[i]].DeepCopyClone(); 
		newMutation.ModifyIndividualRandomly();
	}
}
}
