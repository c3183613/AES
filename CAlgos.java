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
		ArrayList<int[][]> whatDoKey	 = AES.avFlipBit(keyInCopy);

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
		ArrayList<ArrayList<String>> data = AES.generateAvalancheData(whatDoInput, whatDoKey);
		int firstCount = 0;
		int secondCount = 0;
		ArrayList<Integer> aES0List = new ArrayList<Integer>();
		ArrayList<Integer> aES1List = new ArrayList<Integer>();
		ArrayList<Integer> aES2List = new ArrayList<Integer>();
		ArrayList<Integer> aES3List = new ArrayList<Integer>();
		ArrayList<Integer> aES4List = new ArrayList<Integer>();
		for (int j = 0; j<55; j++) {
			firstCount = secondCount = 0;
			for (int i = 1; i<129; i++) {
				firstCount += bitsDifferent(data.get(j).get(0), data.get(j).get(i));
			}
			if(j < 11){
				System.out.println(j);
				aES0List.add(new Integer(Math.round(firstCount/128)));
			}
			else if(11 <= j && j < 22)
				aES1List.add(new Integer(Math.round(firstCount/128)));
			else if(22 <= j && j < 33)
				aES2List.add(new Integer(Math.round(firstCount/128)));
			else if(33 <= j && j < 44)
				aES3List.add(new Integer(Math.round(firstCount/128)));
			else if(j < 55)
				aES4List.add(new Integer(Math.round(firstCount/128)));
			for (int i = 130; i<258; i++) {
				secondCount += bitsDifferent(data.get(j).get(0), data.get(j).get(i));
			}
			//System.out.println(secondCount/128);
		}
		System.out.println("Round\t\tAES0\tAES1\tAES2\tAES3\tAES4");
		for(int i = 0; i<aES0List.size();i++){
			System.out.println("  " + i + "\t\t" + aES0List.get(i) + "\t" + aES1List.get(i) + "\t" + aES2List.get(i) + "\t" + aES3List.get(i) + "\t" + aES4List.get(i));
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

	public static int bitsDifferent(String s1, String s2)
	{
		int count = 0;
		for (int i = 0; i<128; i++) {
			if(s1.charAt(i) != s2.charAt(i))
			{
				count++;
			}
		}
		return count;
	}
}