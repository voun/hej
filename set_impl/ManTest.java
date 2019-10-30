import java.util.Arrays;

class ManTest {
    public static void main(String[] args) 
{
        IntSet set = new BinSearchIntSet();
		System.out.println(set);
        set.add(1);
		System.out.println(set);
        set.add(2);
		System.out.println(set);
        set.add(1);
		System.out.println(set);
        set.add(0);
		System.out.println(set);
        set.remove(1);
		System.out.println(set);
		set.remove(-5);
		System.out.println(set);
		set.add(50);
		set.add(-20);
		System.out.println(set);
		set.add(7);
		set.add(10);
		set.add(1);
		System.out.println(set);




    }


}
