public class AES{
	public static void main(String[] args){
		int a = 9;
		int b = 9;
		int p = 0;
		System.out.println(peasantsAlgorithm(a, b));

	}	
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

	public static boolean leftMostBitSet(int byteIn){
		int bit = (byteIn & 0x80);
		return (bit == 0x80) ? true : false;
	}
}