import java.util.*;
import java.io.*;
public class Euler82 
{
	private static int[][] matrix = new int[80][80];

	public static void main(String[] args) throws IOException
	{
		
		lasIn();
		long start = System.currentTimeMillis();
		
		int[][] down = new int[80][80];
		int[][] up = new int[80][80];
		
		for(int i = 0; i < 80; i++)
		{
			down[i][79] = matrix[i][79];
			up[i][79] = matrix[i][79];
		}
		
		for(int k = 78; k >= 0; k--)
		{
			down[79][k] = matrix[79][k] + matrix[79][k+1];
			up[0][k] = matrix[0][k] + matrix[0][k+1];
			for(int i = 78, j = 1; i >=0 && j <= 79; i--,j++)
			{
				down[i][k] = Math.min(matrix[i][k] + matrix[i][k+1],matrix[i][k] + down[i+1][k]);
				up[j][k] = Math.min(matrix[j][k] + matrix[j][k+1],matrix[j][k] + up[j-1][k]);
			}
			for(int l = 0; l < 80; l++)
				matrix[l][k] = Math.min(down[l][k], up[l][k]);		
		}
		
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < 80; i++)
			min = matrix[i][0] < min ? matrix[i][0] : min;
		long stop = System.currentTimeMillis();
		
		System.out.println(min);
		System.out.println(stop-start);
		
	}

	public static void lasIn() throws IOException
	{
		File file  = new File("C:\\Users\\Andreas\\Desktop\\Euler82.txt");
		Scanner scan = new Scanner(file);
		int row = 0;
		int col = 0;
		while(scan.hasNextLine())
		{
			String str = scan.nextLine();
			Scanner sc = new Scanner(str);
			sc.useDelimiter(",");
			while(sc.hasNextInt())
				matrix[row][col++] = sc.nextInt();
			row++;
			col = 0;
			sc.close();
		}
		scan.close();
		
	}

}
