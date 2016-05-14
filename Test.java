public class Test
{
	public static void main(String[] args)
	{
		int[][] doubleArray = {{0,1},{2,3},{4,5}};
		int[] oneArray = StaticMethods.arrayMultiToOne(doubleArray);
		System.out.println("double array");
		for(int i=0; i<doubleArray.length;i++)
		{
			for(int j=0;j<doubleArray[i].length;j++)
			{
				System.out.println(doubleArray[i][j]);
			}
		}
		System.out.println("one array");
		for(int i=0;i<oneArray.length;i++)
		{
			System.out.println(oneArray[i]);
		}
	}
}