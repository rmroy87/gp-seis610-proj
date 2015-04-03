package engine;

public class MutationOperator {
	
    // Mutate an individual
    private static void mutate(Individual indiv) {
        // Loop through nodes
        for (int i = 0; i < indiv.size(); i++) {
            if (java.util.Random() <= mutationRate) {
                // Create random node
                byte gene = (byte) java.util.Random());
				indiv.setNode(i, node);
            }
        }
    }

}
