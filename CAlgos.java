import java.util.Scanner;
import java.util.ArrayList;

public class CAlgos{

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		String binaryIn = sc.nextLine();
		String keyIn = sc.nextLine();
		int[][] input = new int[4][4];
		int[][] key = new int[4][4];
		int l = 0, m = 0;
		for (int i = 0; i<16; i++) {
			// System.out.println(l + " " + m);
			String in = "";
			for (int j = 0; j<8; j++) {
				in += binaryIn.charAt(0);
				binaryIn = binaryIn.substring(1);
			}
			String keyWordIn = "";
			for (int j = 0; j<8; j++) {
				keyWordIn += keyIn.charAt(0);
				keyIn = keyIn.substring(1);
			}
			input[l][m] = Integer.parseInt(in, 2);
			key[l][m] = Integer.parseInt(keyWordIn, 2);
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

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j<4; j++) {
				System.out.print(String.format("%02X", input[i][j]) + " ");
			}
			System.out.println();
		}
		System.out.println();

		ArrayList<int[][]> keyShedule = new ArrayList<int[][]>();
		keyShedule = AES.keyExpansion(key);
		int[][] output = AES.encrypt(input, keyShedule);

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j<4; j++) {
				System.out.print(String.format("%02X", output[i][j]) + " ");
			}
			System.out.println();
		}
		System.out.println();
		int[][] decrypted = AES.decrypt(output, keyShedule);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j<4; j++) {
				System.out.print(String.format("%02X", decrypted[i][j]) + " ");
			}
			System.out.println();
		}

	}
}