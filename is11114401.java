import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList ;
import java.util.Arrays ;
 
 
public class is11114401 
{
	public static final int populationSize = 100,
							generations = 60,
							tournamentSize = 4,
							matrixSize = 65 ;
	
	public static final double crossoverRate = 0.7,
							   mutationRate = 0.2,
							   sameCopyRate = 0.1 ;
        public static void main(String[] args)
        {
        	
        	AdjacencyMatrix[] tournament = new AdjacencyMatrix[tournamentSize] ;
        	AdjacencyMatrix originalMatrix = null ;
        	Integer[][][] generationArray = new Integer [generations][populationSize][matrixSize] ;
        	ArrayList<Integer[]> matingPool = new ArrayList<Integer[]>(populationSize) ;
        	int size = generationArray[0][0].length ;
        	
        	//initialise the mating pool
        	setupMatingPool(matingPool) ;
        	
        	for(int i = 0 ; i < generationArray[0].length ; i++) {
        		generationArray[0][i] = generateOrdering(size) ;
        	}
        	
             try {
				originalMatrix = new AdjacencyMatrix(new File("matrix65.txt")) ;
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (NotSymmetricException e) {
				System.out.println(e.getMessage()) ;
			} catch (IllegalDimensionsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NonZeroDiagonalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             //only iterate to one less than number of generations because of course final 
             //generation will not have algorithm applied to it and attempting to do so would cause
             //an IndexOutOfBoundsException as there would not be space in the generationArray
             //to store "one after the final generation".
             for(int genCount = 0 ; genCount < generations - 1 ; genCount++) {
            	//begin filling mating pool
            	 for(int i = 0 ; i < matingPool.size() ; i++) {
                 	int[] degrees = new int[tournamentSize] ;
                 	int bestDegree = Integer.MAX_VALUE , chosenOrder = 0 ;
                 	//run the tournament
                  	for(int j = 0 ; j < tournament.length; j++) {
                  		int rand = (int) (Math.random() * 100) ;
                  		tournament[j] = new AdjacencyMatrix(originalMatrix,generationArray[genCount][rand]) ;
                  		degrees[j] = tournament[j].getDegree() ;
                  		if(degrees[j] < bestDegree) {
                  			bestDegree = degrees[j] ;
                  			chosenOrder = rand ;
                  		}
                  	}
                  	matingPool.set(i, generationArray[genCount][chosenOrder]) ;
                  }
            	 
            	 //mating pool has been filled. begin producing new generation
            	 for(int i = 0 ; i < generationArray[0].length ; i++) {
            		 int probability = (int) (Math.random() * 100) + 1 ;
            		 int rand1, rand2 ;
            		 Integer[] parent1, parent2 ;
            		 
            		 //generate 1 random value always, then a second IFF doing crossover
            		 rand1 = (int) (Math.random() * matingPool.size()) ;
            		 
            		 if(probability <= 70) {
            			 //crossover
            			 rand2 = (int) (Math.random() * matingPool.size()) ;
            			 parent1 = matingPool.remove(rand1) ;
            			 parent2 = matingPool.remove(rand2) ;
            			 Integer[][] temp = crossover(parent1, parent2) ;
            			 //add the 2 children produced to the new generation
            			 generationArray[genCount + 1][i] = temp[0] ;
            			 generationArray[genCount + 1][i + 1] = temp[1] ;
            			//The counter i must be incremented an extra time when crossover is
      			 	    //is performed because it produces 2 children, not a single child.
            			 i++ ;
            			 
            		 } else if(probability <= 90) {
            			 //mutation
            			 parent1 = matingPool.remove(rand1) ;
            			 generationArray[genCount + 1][i] = mutation(parent1) ;
            		 } else {
            			 //same copy
            			 parent1 = matingPool.remove(rand1) ;
            			 generationArray[genCount + 1][i] = parent1 ;
            		 }
            	 }
             }
        }
        
        public static Integer[] generateOrdering(int arraySize)
        {
        	Integer[] ordering = new Integer[arraySize] ;
        	ArrayList<Integer> chosenVals = new ArrayList<Integer>(arraySize) ;
        	int randInt ;
        	//starting from 1 and adjusting statements as need to avoid having to worry about how to deal with position 0
        	//when a new array would be filled with zeros anyway.
        	for(int i = 1 ; i <= arraySize ; i++) {
        		randInt = (int) (Math.random() * arraySize) ;
        		if(chosenVals.contains(randInt) == false) {
        			chosenVals.add(randInt) ;
        			ordering[i - 1] = randInt ;
        		} else { //value that was already inserted into ordering array was generated again
        			i-- ; //decrement i so the loop will try once again to put a valid value in the current position
        		}
        	}
        	
        	return ordering ;
        }
        
        /**
         * This method returns a 2D array comprised of the 2 children produced from the given parents
         * It should be noted that since the 2 children are returned in this one 2D array the 
         * calling function must access them appropriately.
         * @param parent1 the first parent 
         * @param parent2 the second parent
         * @return a 2D array containing the 2 children of the given parents.
         */
        public static Integer[][] crossover(Integer[] parent1, Integer[] parent2)
        {
        	Integer[] child1 = new Integer[matrixSize] ;
        	Integer[] child2 = new Integer[matrixSize] ;
        	Integer[][] result = new Integer[2][matrixSize] ;
        	int i = 0, parent1Pos = 0, parent2Pos = 0 ;
        	
        	setupArray(child1) ;
        	setupArray(child2) ;
        	
        	while(i < matrixSize) {
        		if(Arrays.binarySearch(child1, parent1[parent1Pos]) < 0) {
        			child1[i] = parent1[parent1Pos] ;
        			i++ ;
        		}
        		parent1Pos++ ;
        		
        		if(Arrays.binarySearch(child1, parent2[i]) < 0) {
        			child1[i] = parent2[parent2Pos] ;
        			i++ ;
        		}
        		parent2Pos++ ;
        	}
        	
        	result[0] = child1 ;
        	//reset counter variables for reuse
        	i = 0 ; parent1Pos = 0 ; parent2Pos = 0 ;
        	
        	while(i < matrixSize) {
        		if(Arrays.binarySearch(child2, parent2[parent2Pos]) < 0) {
        			child2[i] = parent2[parent2Pos] ;
        			i++ ;
        		}
        		parent2Pos++ ;
        		
        		if(Arrays.binarySearch(child2, parent1[i]) < 0) {
        			child2[i] = parent1[parent1Pos] ;
        			i++ ;
        		}
        		parent1Pos++ ;
        	}
        	result[1] = child2 ;
        	return result ;
        }
        
        public static Integer[] mutation(Integer[] parent)
        {
        	int rand = (int) (Math.random() * (matrixSize - 1)) ;
        	int temp = parent[rand] ;
        	
        	parent[rand] = parent[rand + 1] ;
        	parent[rand + 1] = temp ; 
        	
        	return parent ;
        }
        
        /**
         * This is a convenience method which sets all values in an integer array to -1. It is used
         * to prevent other code from wrongly thinking that 0 was inserted into an array because of
         * the fact that Java initialises all positions in an integer array to 0
         * @param child1
         */
        private static void setupArray(Integer[] child1)
        {
        	for(int i = 0 ; i < child1.length ; i++) {
        		child1[i] = -1 ;
        	}
        }
        
        private static void setupMatingPool(ArrayList<Integer[]> matingPool)
        {
        	for(int i = 0 ; i < matingPool.size() ; i++) {
        		matingPool.set(i, new Integer[matrixSize]) ;
        	}
        }
}