package engine;

public class MutationOperator {
	
		Individual parent1 = new Individual();
		Individual parent2 = new Individual();
		Individual mutatedIndividual1 = parent1.DeepCopyClone();
		Individual mutatedIndividual2 = parent2.DeepCopyClone();
		
		public MutationOperator( )
		{
		mutatedIndividual1.ModifyIndividualRandomly();
		mutatedIndividual2.ModifyIndividualRandomly();
		}		
	}
