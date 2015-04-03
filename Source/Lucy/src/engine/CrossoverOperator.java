package engine;

import java.util;

public class CrossoverOperator {
	
	
	private static Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual();
        // Loop through nodes 
        for (int i = 0; i < indiv1.size(); i++) {
            // Crossover
            if (java.util.Random() <= Individual.size()) {
                newSol.setNode(i, indiv1.getNode(i));
            } else {
                newSol.setNode(i, indiv2.getNode(i));
            }
        }
        return newSol;
	}
	

}
