import java.util.List;
public abstract class StaticMethods
{
	// Takes two dimension array and turns it into one dimensional array
	public static <T> T[] arrayMultiToOne(T[][] arrayParam)
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
		T[] returnArray =(T[]) new Object[size];
		for(int i=0; i<length; i++)
		{
			int innerlength = arrayParam[i].length;
			for(int j=0; j<innerlength;j++)
			{
				returnArray[size] = arrayParam[i][j];
			}
		}
		return returnArray;
	}

	// 
	public static <T> T[][] arrayOneToMulti(T[] arrayParam, int size1, int size2)
	{
		@SuppressWarnings("unchecked")
		T[][] returnArray = (T[][]) new Object[size1][size2];
		return returnArray;
	}
}
