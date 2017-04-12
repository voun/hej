package bbbbb;

import java.util.*;
import java.io.*;

public class Sudoku 
{
	private static int[][] board = new int[9][9];
	private static final int size = 9;
	private static boolean[][] squareFilled = new boolean[9][10];
	private static long start;
	private static long stop;
	
	public static void main (String[] args) throws IOException
	{
		start = System.currentTimeMillis();
		lasIn();
		canSolve(0,0);
		stop = System.currentTimeMillis();
		System.out.println(stop-start);
		for(int i = 0; i < size; i++)
		{
			for( int j = 0; j < size;j++)	
				System.out.print(board[i][j] + " ");
			System.out.println(); 	
		}		
	}
	
	public static void lasIn() throws IOException
	{
		File file = new File("C:\\Users\\Andreas\\Desktop\\Sudoku.txt");
		Scanner scan = new Scanner(file);
		int row = 0;
		int col = 0;
		while (scan.hasNextInt())
		{
			int next = scan.nextInt();
			board[row][col] = next;
			squareFilled[3*(row/3) + (col/3)][next] = true;
			col++;
			if (col % 9 == 0)
			{
				col = 0;
				row++;
				if (scan.hasNextLine()) scan.nextLine();
			}	
		}
		scan.close();
	}
	
	private static boolean canSolve(int row, int col)
	{
		if (row >= size) return true;
		int nextCol = (col == size-1 ? 0 : col+1);
		int nextRow = (col == size-1 ? row + 1 : row);
		if (board[row][col] != 0) return canSolve(nextRow,nextCol);
		else 
		{
			for( int i = 1; i <= 9 ; i++)
			{
				if (isSafe(row,col,i))
				{
					placeNum(row,col,i);
					
					if (canSolve(nextRow,nextCol))
						return true;
					removeNum(row,col,i);		
				}
			}	
		}	
		return false;	
	}
	
	private static void placeNum(int row, int col, int i)
	{
		board[row][col] = i;
		squareFilled[3*(row/3) + (col/3)][i] = true;
	}
	
	private static void removeNum(int row, int col, int i)
	{
		board[row][col] = 0;
		squareFilled[3*(row/3) + (col/3)][i] = false;	
	}
	
	private static boolean rowClear(int row, int i)
	{
		for(int j : board[row])
			if (j == i) return false;
		return true;
	}
	
	private static boolean colClear(int col, int i)
	{
		for(int row = 0; row < size; row++)
			if (board[row][col] == i) return false;
		return true;
	}
	
	private static boolean squareClear(int row, int col, int i) 
	{
		
		return !squareFilled[3*(row/3) + (col/3)][i];
	}
	
	private static boolean isSafe(int row, int col, int i)
	{
		return (rowClear(row,i) && colClear(col,i) && squareClear(row,col, i));
				
	}
}
