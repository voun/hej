
public class BinSearchIntSet implements IntSet
{
	private int[] nums;
	private int length;

	public BinSearchIntSet()
	{
		nums = new int[1];
		length = 0;
		
	}
	public boolean contains (int element)
	{
		if(length == 0)
			return false;
		return (nums[find(element)] == element);
	}
	public void add(int element)
	{
		if(length == 0)
			nums[length] = element;
		else
		{
			int index =find(element);
			if(nums[index] == element)
				return;
			if (length == nums.length)
				extend();
			if(element > nums[index])
				nums[length] = element;
			else
			{
				for(int i = length; i > index;i--)
					nums[i] = nums[i-1];
				nums[index] = element;	
			}
		}
		length++;
	}
	public void remove(int element)
	{
		if(length == 0)
			return;
		int index = find(element);
		if(nums[index] != element)
			return;	
		for(int i = index; i < length-1;i++)
			nums[i] = nums[i+1];	
		length--;	
	}

	private int find (int element)
	{
		int low = 0, high = length-1, mid = 0;
		while( low < high)
		{
			mid = (low+high)/2;
			if(nums[mid] == element)
				return mid;
			else if (nums[mid] > element)
				high = mid;
			else
				low = mid+1;
		}
		return low;
	}

	private void extend()
	{
		int[] temp = new int[2*nums.length];
		for(int i = 0; i < nums.length;i++)
			temp[i] = nums[i];
		nums = temp;
		
	}

	public String toString()
	{
		String s = "";
		for(int i = 0; i < length;i++)
			s = s+","+nums[i];
		return s;
		
	}
}

