import java.io.File ;
import java.io.FileNotFoundException;
import java.util.Scanner ;
 
 
public class is11114401 
{
        public static void main(String[] args) throws IllegalDimensionsException 
        {
             try {
				AdjacencyMatrix matrix = new AdjacencyMatrix(new File("adjacency matrix 65nodesalmost11clusters.txt")) ;
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (NotSymmetricException e) {
				System.out.println(e.getMessage()) ;
			}
        }
}