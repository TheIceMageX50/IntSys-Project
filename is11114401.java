import java.io.File;
import java.io.FileNotFoundException;
 
 
public class is11114401 
{
        public static void main(String[] args)
        {
        	int[][][] generationArray = new int [60][100][65] ;
        	
             try {
				AdjacencyMatrix matrix = new AdjacencyMatrix(new File("adjacency matrix 65nodesalmost11clusters.txt")) ;
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
        }
}