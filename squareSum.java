
public class squareSum 
{
	
	public static void main (String[] args)
	{
		int n = 100;
		int[] min = new int[n+1];
		for( int j = 0; j <= n; j++) min[j] = Integer.MAX_VALUE;
		
		min[0] = 0;
		for( int i = 1 ; i <= n; i++)
			for( int x = 1; x*x <= i; x++)
				if (min[i-x*x] + 1 < min[i]) min[i] = min[i-x*x]+1;	
		for(int i = 0; i <= n; i++)
			System.out.println(i + ": " + min[i]);
	}
}
