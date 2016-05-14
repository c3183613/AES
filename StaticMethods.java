import java.util.List;
public abstract class StaticMethods
{
	// intakes two dimension array and turns it into one dimensional array
	public static int[] arrayMultiToOne(int[][] arrayParam)
	{
		int atIndex = 0;
		int length = arrayParam.length;
		int size = 0;
		for(int i=0; i< length; i++)
		{
			int innerlength = arrayParam[i].length;
			for(int j=0; j<innerlength;j++)
			{
				size++;
			}
		}
		@SuppressWarnings("unchecked")
		int[] returnArray = new int[size];
		for(int i=0; i<length; i++)
		{
			int innerlength = arrayParam[i].length;
			for(int j=0; j<innerlength;j++)
			{
				returnArray[atIndex] =  arrayParam[i][j];
				atIndex++;
			}
		}
		return returnArray;
	}

	// 
	public static  int[][] arrayOneToMulti(int[] arrayParam, int size1, int size2)
	{
		@SuppressWarnings("unchecked")
		int[][] returnArray = new int[size1][size2];
		return returnArray;
	}
}
