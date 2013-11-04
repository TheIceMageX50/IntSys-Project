import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AdjacencyMatrix 
{
	private int[][] matrix ;
	
	public AdjacencyMatrix(File file) throws FileNotFoundException, 
											 IllegalDimensionsException,
											 NotSymmetricException
	{
		Scanner scanner = new Scanner(file) ;
		String temp ;
		String[] valStrings ;
		int rowCount = 0, colCount, row = 0, col = 0 ;
		
		temp = scanner.nextLine() ;
		rowCount++ ;
        colCount = (temp.length() + 1) / 2 ;
        matrix = new int[colCount][colCount] ;
        
        while(scanner.hasNext() == true) {
                temp = scanner.nextLine() ;
                if((temp.length() + 1) / 2 != colCount) {
                        //If any any point the row length observed is not equal to the length
                	    //of the first row then the matrix is invalid.
                        throw new IllegalDimensionsException("Unequal row lengths") ;
                }
                rowCount++ ;
        }
        
        if(rowCount != colCount) {
        	throw new IllegalDimensionsException("Matrix must have equal amount of rows and columns") ;
        }
        
        scanner = new Scanner(file) ;
        while(scanner.hasNext() == true) {
        	temp = scanner.nextLine() ;
        	valStrings = temp.split(" ") ;
        	for(col = 0 ; col < colCount ; col++) {
        		matrix[row][col] = Integer.parseInt(valStrings[col]) ;
        	}
        	row++ ;
        }
        //If an exception has not been thrown and the program has got to this point, the matrix
        //is a valid square matrix and has been stored in memory. But is it symmetric?
        
        for(int i = 0 ; i < rowCount ; i++) {
        	for(int j = 0 ; j < colCount ; j++) {
        		if(i != j && matrix[i][j] != matrix[j][i]) {
        			//If the current entry is not on the main diagonal, and given that current entry
        			// is matrix[i][j] and it is not equal to matrix[j][i] then the matrix is not
        			//symmetric.
        			System.out.println("matrix[" + i + "][" + j + "] = " + matrix[i][j]) ;
        			System.out.println("matrix[" + j + "][" + i + "] = " + matrix[j][i]) ;
        			throw new NotSymmetricException("Matrix is not symmetric") ;
        		}
        	}
        }
	}
	
	public void swapRowsAndCols(int val1, int val2)
	{
		int temp = 0 ;
		for(int i = 0 ; i < matrix.length ; i++) {
			temp = matrix[i][val2] ;
			matrix[i][val2] = matrix[i][val1] ;
			matrix[i][val1] = temp ;
		}
		
		for(int i = 0 ; i < matrix.length ; i++) {
			temp = matrix[val2][i] ;
			matrix[val2][i] = matrix[val1][i] ;
			matrix[val1][i] = temp ;
		}
	}
}