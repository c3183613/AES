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

	// Takes a size dimension array and turns it into a two dimensional array
	// must specify is required of the two dimensional array
	// Neither size1 or size2 may be 0
	public static  int[][] arrayOneToMulti(int[] arrayParam, int size1, int size2) throws Exception
	{
		if(size1==0 || size2==0)
		{
			throw new Exception("Array cannot be of size 0");
		}
		@SuppressWarnings("unchecked")
		int[][] returnArray = new int[size1][size2];
		int atIndex =0;
		int atIndexMulti1, atIndexMulti2; 
		atIndexMulti1 = atIndexMulti2 = 0;
		while(atIndex!=arrayParam.length)
		{
			returnArray[atIndexMulti1][atIndexMulti2] = arrayParam[atIndex];
			if(++atIndexMulti2 == size2)
			{
				atIndexMulti1++;
				atIndexMulti2 = 0;
			}
			atIndex++;
		}
		return returnArray;
	}
}
