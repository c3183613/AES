import java.util.Scanner;
import java.util.ArrayList;

public class CAlgos
{
	// recieves two lines of binary input, prints the input 
	// encrypts and prints encrypted plaintext, decrypts and prints plaintext
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		// take input
		String binaryIn = sc.nextLine();
		String binaryInCopy = binaryIn;
		// take key
		String keyIn = sc.nextLine();
		String keyInCopy = keyIn;
		// initialization 
		int[][] input = new int[4][4];
		int[][] key = new int[4][4];
		// used to keep track of x and y values of 2d array
		int l = 0, m = 0;
		// do this for 16 bytes
		for (int i = 0; i<16; i++) 
		{
			// System.out.println(l + " " + m);
			String in = "";
			// add the first character of binaryIn to 'in' and 
			// remove it from original string
			// 8 times for 8 bits = 1 byte
			for (int j = 0; j<8; j++) 
			{
				in += binaryIn.charAt(0);
				binaryIn = binaryIn.substring(1);
			}
			String keyWordIn = "";
			// do the same for the key
			for (int j = 0; j<8; j++) 
			{
				keyWordIn += keyIn.charAt(0);
				keyIn = keyIn.substring(1);
			}
			// in is a binary number, turn it into a decimal number
			input[l][m] = Integer.parseInt(in, 2);
			key[l][m] = Integer.parseInt(keyWordIn, 2);
			// keep track of which index we are at in the 2d array
			if((i+1)%4 == 0 && i != 0)
			{
				m++;
				l = 0;
			}
			else
			{
				l++;
			}
		}
		ArrayList<int[][]> whatDoInput	 = AES.avFlipBit(binaryInCopy);
		ArrayList<int[][]> whatDoKey	 = AES.avFlipBit(keyInCopy)

		// Print out the hexadecimal representation of each array element in a cube
		for (int i = 0; i < 4; i++) 
		{
			for (int j = 0; j<4; j++) 
			{
				System.out.print(String.format("%02X", input[i][j]) + " ");
			}
			System.out.println();
		}
		System.out.println();

		ArrayList<int[][]> keyShedule = new ArrayList<int[][]>();
		keyShedule = AES.keyExpansion(key);
		// encrypt the input using the key given
		int[][] output = AES.encrypt(input, keyShedule);
		for (int i = 0; i < 4; i++) 
		{
			for (int j = 0; j<4; j++) 
			{
				System.out.print(String.format("%02X", output[i][j]) + " ");
			}
			System.out.println();
		}
		System.out.println();
		// decrypt the input using the key given
		int[][] decrypted = AES.decrypt(output, keyShedule);
		for (int i = 0; i < 4; i++) 
		{
			for (int j = 0; j<4; j++) 
			{
				System.out.print(String.format("%02X", decrypted[i][j]) + " ");
			}
			System.out.println();
		}

		// Jeremy's testing
		// ArrayList<int[][]> whatDo = AES.avFlipBit(testBinaryIn);
		// System.out.println();
		// System.out.println(testBinaryIn+"original");
		// for(int[][] test: whatDo)
		// {
		// 	for(int i=0;i<4;i++)
		// 	{
		// 		for(int j=0;j<4;j++)
		// 		{
		// 			System.out.print((test[i][j]) + " ");
		// 		}
		// 		System.out.println();
		// 	}
		// 	System.out.println();
		// }
	}
}