//just has function for gf multiplication so far
public class AES<T>
{
	static int[][] sBox = 	{
								{0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76},
								{0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0},
								{0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15},
								{0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75},
								{0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84},
								{0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf},
								{0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8},
								{0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2},
								{0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73},
								{0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb},
								{0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79},
								{0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08},
								{0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a},
								{0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e},
								{0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf},
								{0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16},
							};

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
		JAlgos.rotWordLeft(eheh);
		System.out.println();
		for(int i = 0; i<4;i++)
			System.out.print((char)eheh[i]);
		int[][] twoDimArray = {{0,1},{2,3},{4,5}}; // using this for testing
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

	public static int subBytes(int byteIn)
	{
		String byteToHex = String.format("%02X", byteIn);
		int r = (Integer.decode("0x0" + byteToHex.charAt(0)));
		int c = (Integer.decode("0x0" + byteToHex.charAt(1)));
		return sBox[r][c];
	}

	public static int[] subWord(int[] wordIn)
	{
		int[] wordOut = wordIn;
		for(int i = 0; i < 4; i++)
		{
			wordOut[i] = subBytes(wordOut[i]);
		}
		return wordOut;
	}
}