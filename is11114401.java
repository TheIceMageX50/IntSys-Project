import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList ;
import java.util.Arrays ;
 
 
public class is11114401 
{
	public static final int populationSize = 100,
							generations = 60,
							tournamentSize = 4 ;
	
	public static final double crossoverRate = 0.7,
							   mutationRate = 0.2,
							   sameCopyRate = 0.1 ;
        public static void main(String[] args)
        {
        	
        	AdjacencyMatrix[] tournament = new AdjacencyMatrix[tournamentSize] ;
        	AdjacencyMatrix originalMatrix = null ;
        	int[][][] generationArray = new int [generations][populationSize][65] ;
        	int[][] matingPool = new int [populationSize][65] ;
        	int size = generationArray[0][0].length ;
        	
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
             
             //begin filling mating pool
             for(int i = 0 ; i < matingPool.length ; i++) {
            	int[] degrees = new int[tournamentSize] ;
            	int bestDegree = Integer.MAX_VALUE , chosenOrder = 0 ;
             	for(int j = 0 ; j < tournament.length; j++) {
             		int rand = (int) (Math.random() * 100) ;
             		tournament[j] = new AdjacencyMatrix(originalMatrix,generationArray[0][rand]) ;
             		degrees[j] = tournament[j].getDegree() ;
             		if(degrees[j] < bestDegree) {
             			bestDegree = degrees[j] ;
             			chosenOrder = rand ;
             		}
             	}
             	matingPool[i] = generationArray[0][chosenOrder] ;
             }
        }
        
        public static int[] generateOrdering(int arraySize)
        {
        	int[] ordering = new int[arraySize] ;
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
}