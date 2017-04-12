package mammamia;

public class Scania 
{
	private static boolean [][] board =  new boolean[16][16];
	private static final int LENGTH = board.length;
	private static boolean [] upDiagEmpty = new boolean[2*LENGTH-1];
	private static boolean [] downDiagEmpty = new boolean[2*LENGTH-1];
	private static boolean []  colEmpty = new boolean[2*LENGTH-1];
	
	public static void main (String[] args)
	{
		for(int i = 0; i < 2*LENGTH-1; i++)
		{
			upDiagEmpty[i] = true;
			downDiagEmpty[i] = true;
		}
		
		for(int i = 0; i < LENGTH; i++)
			colEmpty[i] = true;	
		
		damer(0);
		
		for( int i = 0; i < LENGTH; i++)
		{
			for( int j = 0; j < LENGTH; j++)
				System.out.print( board[i][j] == true ? "Q    " : "E    ");
			System.out.println();		
		}	
	}
	
	public static boolean damer(int row)	
	{

		if (row == LENGTH) return true;
		for(int col = 0; col < LENGTH; col++)
		{
			if (isSafe(row,col)) 
			{
				placeQueen(row,col);
				if (damer(row+1))
					return true;
				removeQueen(row,col);		
			}	
		}
		return false;
	}
	
	public static void placeQueen(int row, int col) 
	{
		board[row][col] = true;
		colEmpty[col] = false;
		downDiagEmpty[LENGTH + row - col - 1] = false;
		upDiagEmpty [row + col] = false;
		
		}
	public static void removeQueen(int row, int col) 
	{
		board[row][col] = false;
		colEmpty[col] = true;
		upDiagEmpty[row + col] = true;
		downDiagEmpty[LENGTH + row  - col -1] = true;	
	}
	
	public static boolean isSafe(int row, int col)
	{
		return (colEmpty[col] &&
				upDiagEmpty[row + col] &&
				downDiagEmpty[LENGTH + row - col - 1]);	
	}
}
