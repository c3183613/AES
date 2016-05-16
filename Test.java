public class Test
{
	public static void main(String[] args) throws Exception
	{
		int[][] doubleArray = {	{0,1,2,3},
								{4,5,6,7},
								{8,9,10,11},
								{12,13,14,15}};
		for(int i=0;i<doubleArray.length;i++)
		{
			String s="{";
			for(int j=0;j<doubleArray[i].length-1;j++)
			{
				s+=doubleArray[i][j] +", ";
			}
			s+=doubleArray[i][3];
			s+="}";
			System.out.println(s);
		}
		System.out.println();
		System.out.println();
		JAlgos.shiftRows(doubleArray);
		for(int i=0;i<doubleArray.length;i++)
		{
			String s="{";
			for(int j=0;j<doubleArray[i].length-1;j++)
			{
				s+=doubleArray[i][j] +", ";
			}
			s+=doubleArray[i][3] + "}";
			System.out.println(s);
		}
		System.out.println();
		System.out.println();
		JAlgos.shiftRowsInv(doubleArray);
		for(int i=0;i<doubleArray.length;i++)
		{
			String s="{";
			for(int j=0;j<doubleArray[i].length-1;j++)
			{
				s+=doubleArray[i][j] +", ";
			}
			s+=doubleArray[i][3] + "}";
			System.out.println(s);
		}
	}
}

// 8 9 10 11
// 11 8 9 10