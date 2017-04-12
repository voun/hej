package bbbbb;

import java.io.*;
import java.util.*;


public class projecteuler81 
{
	private static int[][] matrix = new int[80][80];
	private static final int size = 80;
	
	public static void main(String[] args) throws IOException
	{
		lasIn();
		for( int i = 0; i < 80; i++)
		{
			for( int j = 0; j < 80; j++)
				System.out.print(matrix[i][j] + " ");
			System.out.println();
		}
		System.out.println(canSum());
	}
	
	private static int canSum()
	{	
		for(int j = size-2; j >=0; j--)
		{
			matrix[size-1][j] += matrix[size-1][j+1];
			matrix[j][size-1] += matrix[j+1][size-1];		
		}
		for(int i = size-2; i >=0;i--)
			for(int j = size-2;j>=0;j--)
				matrix[i][j] += Math.min(matrix[i+1][j],matrix[i][j+1]);
		return matrix[0][0];
	
	}
	
	private static void lasIn() throws IOException
	{
		File file = new File("C:\\Users\\Andreas\\Desktop\\matrix.txt");
		Scanner scan = new Scanner(file);
		scan.useDelimiter(",");
		ArrayList<String> rows = new ArrayList<String>();
		while(scan.hasNextLine())
			rows.add(scan.nextLine());
		String str = "";
		for( String rad : rows) str += "," + rad;
		scan.close();
			
		int row = 0;
		int col = 0;
		Scanner sc = new Scanner(str);
		sc.useDelimiter(",");
		while(sc.hasNextInt())
		{
			int next = sc.nextInt();;
			matrix[row][col] = next;
			col++;
			if (col == 80)
			{
				col = 0;
				row++;		
			}	
		}
		sc.close();
			
	}



}
