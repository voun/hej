
public class KnightsTour 
{
	private static int[][] board = new int[8][8];
	

	public static void main(String[] args) 
	{
		board[0][0] = 0;
		canSolve(0,0,1);
		
			
				
		
	}
	
	public static boolean canSolve(int row, int col, int move)
	{
		
		for( int i = 0; i < 8; i++)
		{
			for( int j = 0; j < 8; j++)
				System.out.print(board[i][j] + " ");
			System.out.println();	
		}
		
		System.out.println("***************************");
		if (move == 64) return true;
		
		int[] y = {2, 1, -1, -2, -2, -1, 1, 2};
		int[] x = {1, 2, 2, 1, -1, -2, -2, -1};

		for(int k = 0; k < 8;k++)
		{
			int newRow = row + y[k];
			int newCol = col + x[k];
			
			if (isSafe(newRow,newCol))
			{
				placeKnight(newRow,newCol,move);
				if (canSolve(newRow,newCol,move+1))
					return true;
				removeKnight(newRow,newCol);		
			}			
		}
		return false;	
	}
	
	private static boolean isSafe(int row, int col) {return row <= 7 && row >= 0 && col <= 7 && col >=0 && board[row][col] == 0;}

	private static void placeKnight(int row, int col, int move) {board[row][col] = move;}

	private static void removeKnight(int row, int col){board[row][col] = 0;}


}
