import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AdjacencyMatrix 
{
	private int[][] matrix ;
	private int[] ordering ;
	private int row, col ;
	private int degree ;

	public AdjacencyMatrix(File file) throws FileNotFoundException, 
											 IllegalDimensionsException,
											 NotSymmetricException, 
											 NonZeroDiagonalException
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
                        //If at any point the row length observed is not equal to the length
                	    //of the first row then the matrix is invalid.
                        throw new IllegalDimensionsException("Unequal row lengths") ;
                }
                rowCount++ ;
        }
        scanner.close() ;
        
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
        scanner.close() ;
        ordering = new int[matrix.length] ;
        for(int i = 0 ; i < ordering.length ; i++) {
        	ordering[i] = i ;
        }
        //If an exception has not been thrown and the program has got to this point, the matrix
        //is a valid square matrix and has been stored in memory. But is it symmetric?
        if(this.isSymmetricAndHasZeroDiagonal() == false) {
        	throw new NotSymmetricException("Matrix is not symmetric") ;
        }
        
        degree = computeDegree() ;
	}
	
	public AdjacencyMatrix(AdjacencyMatrix original, int[] newOrdering)
	{
		matrix = original.matrix ;
		ordering = newOrdering ;
		
		for(int i = 0 ; i < original.ordering.length ; i++) {
			if(original.ordering[i] != ordering[i]) {
				int val1 = original.ordering[i], 
					val2 = ordering[i] ;
				swapRowsAndCols(val1, val2);
			}
		}
		degree = computeDegree() ;
	}
	
	public int getDegree() 
	{
		return degree ;
	}
	
	private void swapRowsAndCols(int val1, int val2)
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
	
	private boolean isSymmetricAndHasZeroDiagonal() throws NonZeroDiagonalException 
	{
		boolean execute = true, result = true ;
		
		for(int i = 0 ; i < this.row ; i++) {
        	for(int j = 0 ; j < this.col ; j++) {
        		if(i != j && matrix[i][j] != matrix[j][i]) {
        			//If the current entry is not on the main diagonal, and given that current entry
        			//is matrix[i][j] and it is not equal to matrix[j][i] then the matrix is not
        			//symmetric, so it must be fixed.
        			System.out.print("matrix[" + i + "][" + j + "] = " + matrix[i][j] + " ") ;
        			System.out.println("matrix[" + j + "][" + i + "] = " + matrix[j][i] + "\n Fixing matrix to be "
        					+ "symmetric...") ;
        			
        			if(matrix[i][j] == 0) {
        				matrix[i][j] = 1 ;
        			} else {
        				matrix[j][i] = 1 ;
        			}

        			if(execute == true) {
        				result = false ;
        				//If any one entry comparison for symmetry returns false then there is
        				//no point in setting result false again and again, so set execute to
        				//to false to prevent this line from executing.
        				execute = false ;
        			}
        		
        		//If an entry on the main diagonal is not 0 then an exception is thrown.
        		} else if(i == j && matrix[i][j] != 0) {
        			throw new NonZeroDiagonalException("Diagonal entries are not all zero.") ;
        		}
        	}
        }
		return result ;
	}
	
	private int computeDegree()
	{
		int output = 0 ;
		
		for(int i = 0 ; i < matrix.length ; i++) {
			for(int j = 0 ; j < matrix.length ; j++) {
				if(matrix[i][j] == 1) {
					// + 1 adjustments within Math.abs() call to allow for the fact that Java
					//array indexes start at 0.
					output += Math.abs( (i + 1) - (j + 1) ) ;
				}
			}
		}
		
		return output ;
	}
}