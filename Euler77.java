import java.util.*;

public class Euler77 
{
	public static int antal = 0;
	public static ArrayList<Integer> table = new ArrayList<Integer>();
	public static void main (String[] args)
	{
		table.add(2);

		int i = 2;
		while (true)
		{
			canSum(0,i,i);
			if (antal >= 5000)
			{
				System.out.println(i);
				break;				
			}			
			i++;
			antal = 0;
		}
	
	}
	
	public static void canSum(int j, int target, int sum)
	{
		if (table.get(table.size()-1) <= sum) updateTable();
		if (target < 0) return;
		if ( table.get(j) >= sum+1)
		{
			if (target == 0)
				antal++;
			return;
		}
		int prime = table.get(j);
		for(int i = 0; i <= sum; i+=prime)
			canSum(j+1,target-i,sum);
	
	}
	public static void updateTable()
	{
		int last = table.get(table.size()-1);
		while (true)
			if (isPrime(++last))
			{
				table.add(last);
				return;	
			}		
	}
	public static boolean isPrime( int p)
	{
		if (p <= 1) return false;
		if (p == 2) return true;
		if (p % 2 == 0) return false;
		int n = (int)Math.sqrt(p);
		for( int i = 3; i <= n; i+=2)
			if (p%i == 0) return false;
		return true;
	}

}
