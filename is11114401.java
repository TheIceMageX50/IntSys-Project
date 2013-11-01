import java.io.File ;
import java.io.FileNotFoundException;
import java.util.Scanner ;
 
 
public class is11114401 
{
	public static void main(String[] args) throws Exception 
	{
		Scanner scanner ;
		int[][] matrix ;
		int row, colCount ;
		String temp = "" ;
		
		try {
			scanner = new Scanner(new File(args[0])) ;
			
			temp = scanner.nextLine() ;
			colCount = (temp.length() + 1) / 2 ;
			while(scanner.hasNext() == true) {
				temp = scanner.nextLine() ;
				if((temp.length() + 1) / 2 != colCount) {
					//TODO make custom Exception
					throw new Exception("Unequal row lengths") ;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}