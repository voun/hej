
package mammamia;
import java.math.BigDecimal;
import java.util.*;

public class asdasdas {

	public static void main(String[] args) 
	{
		System.out.println(fac(5));
		

	
		
	

	}
	
	
	static String[] Trap_Game(int[] K) 
	{
	    String[] str = new String[K.length];
	    
	    for( int i = 0; i < K.length;i++)
	    {
	        int gynnsamma = 0;
	        for( int j = 1; j <= K[i]; j++) 
	            gynnsamma += totientFunction(j);
	        gynnsamma = 2*gynnsamma-1;
	       double sannolikhet = 1-(double)gynnsamma/(K[i]*K[i]);
	       String s = "" + sannolikhet;
	       BigDecimal a = new BigDecimal(s);
	       BigDecimal roundOff = a.setScale(4, BigDecimal.ROUND_HALF_EVEN);
	       str[i] = roundOff.toString();
	       
	    }
	    return str;
	}

	static boolean  isPrime(int p)
	{
	    if (p<=1) return false;
	    if(p==2) return true;
	    if(p%2 == 0) return false;
	    int n = (int)Math.sqrt(p);
	    for(int i=3; i <=n;i+=2)
	        if ( p%i == 0) return false;
	    return true;      
	}
	
	static int fac(int n){ return  n == 1 ? 1 : n*fac(n-1);}

	static int totientFunction(int n)
	{
	    int product = n;
	    for( int i=2; i <= n; i++)
	        if(isPrime(i) && n%i == 0)  product*=(1-(double)1/i);
	    return product;
	}
	public int absVal( int x) { return x > 0 ? x : -x;}
	
	static public boolean isPalindrom(String str)
	{
		str = str.trim().toLowerCase();
		return str.equals(reverse(str));
	}
	
	static public String reverse(String str)
	{
		if (str.length() == 1) return str;
		else
			return str.charAt(str.length()-1) + reverse(str.substring(0,str.length()-1));
	}
	
	static public boolean isAnagram(String str1, String str2)
	{
		ArrayList<Character> l1 = new ArrayList<Character>();
		ArrayList<Character> l2 = new ArrayList<Character>();
		
		for (int i = 0; i < str1.length();i++)
			if (!Character.isWhitespace(str1.charAt(i)))
				l1.add(str1.charAt(i));
		
		for (int i = 0; i < str2.length();i++)
			if (!Character.isWhitespace(str2.charAt(i)))
				l2.add(str2.charAt(i));
		
		Collections.sort(l1);
		Collections.sort(l2);
		
		System.out.println(l1);
		System.out.println(l2);
		return l1.equals(l2);
			
		
	}
	static boolean beautiful_binary_strings(String BinaryString) 
	{
	    String Bin = BinaryString;
	    if (Bin == "A" || Bin == "B" || Bin.length()%2 != 0 || Bin == "AB" || Bin == "BA") return false;
	    if (Bin == "AA" || Bin == "BB" || Bin.isEmpty()) return true;
	    
	    int index = 0;
	    while (true)
	    {
	        index = Bin.indexOf(Bin.charAt(0),index+1);
	        if (index == -1) return false;
	        if ((beautiful_binary_strings(Bin.substring(0,index-1))) && beautiful_binary_strings(Bin.substring(index+1)))
	            return true;        
	    }     
	    
	}
}

class snopp {
	
}