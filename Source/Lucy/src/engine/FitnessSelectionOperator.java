package engine;
import java.util.Random;

public class FitnessSelectionOperator {
static int order=1;
	
static public void selectBest(double[] values, int valuesSize,  
			 int[] population, int populationSize)
{
 int[] sort = new int[valuesSize];
 
 for (int i = 0; i < valuesSize; i++)
   sort[i] = i;
 
 qsort(sort, 0, valuesSize - 1, values, order);
 
 for (int i = 0; i < populationSize; i++)
   population[i] = sort[i];
}

// sort the array in ascending order
static private void qsort(int[] v, int left, int right, 
		    double[] values, int order) 
{ 
  if (left >= right) return;

  int p = left;
  swap(v, left, (left + right)/2);
  for (int i = left + 1; i <= right; i++)
    {
	double diff = values[v[i]] - values[v[left]];
	if ((order == 1 ? diff < 0 : diff > 0))
	  swap(v, ++p, i);
    }
  swap(v, left, p);
  qsort(v, left, p - 1, values, order);
  qsort(v, p + 1, right, values, order);
}

//
static private void swap(int[]  v, int i, int j)
{
  int t = v[i];
  v[i] = v[j];
  v[j] = t;
}


//selects integers between 0 and N-1
//based on their probabilities; places the selected integers in population

static public void selectProb(int N, double[] probabilities, 
				int[] population, int n, Random rand)
{
  for (int j = 0; j < n; j++)
    { 
	double val = rand.nextDouble();
    

	// probabilities[i - 1] < val <= probabilities[i]
	int i = 0;
	int left = 0;
	int right = N - 1;
	while (right >= left)
	  {
	    int middle = (left + right) / 2;
	    if (val - probabilities[middle] < 0.0)
	      right = middle - 1;
	    else if (val - probabilities[middle] > 0.0)
	      left = middle + 1;
	    else
	      {
		i = middle;
		break;
	      }
	  }
	
	if (right < left)
	  i = left;
	
	population[j] = i;
    }
}
}