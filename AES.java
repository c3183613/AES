//just has function for gf multiplication so far
public class AES<T>
{
	static int[][] rCon={	
							{1,0,0,0},
							{2,0,0,0},
							{4,0,0,0},
							{8,0,0,0},
							{16,0,0,0},
							{32,0,0,0},
							{64,0,0,0},
							{128,0,0,0},
							{27,0,0,0},
							{54,0,0,0},
						};

	static char[][] key = 	{
								{'S', ' ', ' ', ' '}, 
								{'O', '1', 'B', 'K'},
								{'M', '2', 'I', 'E'}, 
								{'E', '8', 'T', 'Y'}
							};

	static char[][] input = {
								{'A', 'C', 'T', 'W'}, 
								{'T', 'K', ' ', 'N'},
								{'T', ' ', 'D', '!'}, 
								{'A', 'A', 'A', '1'}
							};
	public static void main(String[] args)
	{
		int[] eheh= {'S', 'T', 'I', 'T'};
		for(int i = 0; i<4;i++)
			System.out.print((char)eheh[i]);
		System.out.println();
		rotWord(eheh);
		for(int i = 0; i<4;i++)
			System.out.print((char)eheh[i]);

	}	

	/*********************************************************************
	 *********************************************************************
	 This is the function for GF multiplication, not sure what we're 
	 supposed to use it for yet but apparantly it's useful... Includes 
	 rightMostBitSet and leftMostBitSet functions.
	 *********************************************************************
	 *********************************************************************/

	public static int peasantsAlgorithm(int aIn, int bIn)
	{
		int p = 0, a = aIn, b = bIn;
		for(int i = 0; i<8; i++)
		{
			if(rightMostBitSet(b))
			{
				p ^= a;
			}

			b = b >> 1;

			boolean carry = leftMostBitSet(a);

			a = a << 1;

			if(carry)
			{
				a ^= 0x1b;
			}

			if(a == 0 || b == 0)
			{
				break;
			}
		}
		return p;
	}

	public static boolean rightMostBitSet(int byteIn)
	{
		int bit = (byteIn & 1);
		return (bit == 1) ? true : false;
	}

	public static boolean leftMostBitSet(int byteIn)
	{
		int bit = (byteIn & 0x80);
		return (bit == 0x80) ? true : false;
	}

	/*********************************************************************
	 *********************************************************************
	 This is the xor fucntion, takes a 4x4 matrix of values, xors them
	 with a key and returns the result, it's the first step of each round.
	 There's one for first round and one for the rest.
	 *********************************************************************
	 *********************************************************************/

	public static int[][] xorFirstRound(char[][] currentRoundInputIn)
	{
		char[][] current = currentRoundInputIn;
		int[][] output = new int[4][4];
		for(int i = 0; i<4; i++)
		{
			for(int j = 0; j<4; j++)
			{
				output[i][j] = current[i][j]^key[i][j];
			}
		}
		return output;
	}

	public static int[][] xor(int[][] currentRoundInputIn)
	{
		int[][] current = currentRoundInputIn;
		int[][] output = new int[4][4];
		for(int i = 0; i<4; i++)
		{
			for(int j = 0; j<4; j++)
			{
				output[i][j] = current[i][j]^key[i][j];
			}
		}
		return output;
	}


	public static int[][] keyExpansion(int[][] keyIn)
	{
		int[][] boop = {{1}};
		return boop;
	}

	public static int[] rotWord(int[] wordIn)
	{
		int[] wordOut = wordIn;
		int temp = wordOut[0];
		for(int i = 0;i<3;i++)
		{
			wordOut[i] = wordOut[i+1];
		}
		wordOut[3] = temp;
		return wordOut;
	}
}