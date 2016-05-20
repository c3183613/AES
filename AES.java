/*********************************************************************************************************************************************
//AES class for COMP3260 Assignment 2
//Programmers: Chris O'Donnell (c3165328) and Jeremy Law (c3183613)
//Date Completed: 20/5/2016
//Purpose of class: AES class has all static methods and contains all the functionality to encrypt/decrypt using AES and explore the Avalanche 
//effect.
 *********************************************************************************************************************************************/
import java.util.ArrayList;
public class AES
{
	private static int[][] sBox = 				//This is the substitution box array. Bytes are substituted based on their values for values in this table for encryption
								{
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

	private static int[][] invSBox = 			//This is the inverse substitution box array. Bytes are substituted based on their values for values in this table for decryption
								{
									{0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb},
									{0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb},
									{0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e},
									{0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25},
									{0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92},
									{0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84},
									{0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06},
									{0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b},
									{0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73},
									{0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e},
									{0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b},
									{0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4},
									{0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f},
									{0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef},
									{0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61},
									{0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d}
								};

	private static int[][] rCon=
						{						//the round constant array, each row is used once for each of the 10 generated keys in keyExpansion
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

	static ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();				//this is an arraylist of arralists of strings to store all of the data collected for avalanche analysis

	/*********************************************************************
	 *********************************************************************
	 Encrypt performs the encryption of a given input based on a given key
	 and the 10 round expansion of that key.
	 *********************************************************************
	 *********************************************************************/

	public static int[][] encrypt(int[][] in, ArrayList<int[][]> keyShedule)
	{
		int[][] preFirstRountState = xor(in, keyShedule.get(0));						//creating the new int[][] array to store the output by adding the initial key to the input
		for (int i = 0; i<9; i++) 														//the first nine rounds do:
		{
			preFirstRountState = subBytes(preFirstRountState);							//sub bytes using the sbox
			preFirstRountState = shiftRows(preFirstRountState);							//shiftrows takes changes the 2nd to 4th rows of the current state
			preFirstRountState = mixColumns(preFirstRountState);						//mix columns takes each column of the current state and mixes up the bits
			preFirstRountState = xor(preFirstRountState, keyShedule.get(i+1));			//add the round key
		}
		preFirstRountState = subBytes(preFirstRountState);								//10th round has no mix columns
		preFirstRountState = shiftRows(preFirstRountState);
		preFirstRountState = xor(preFirstRountState, keyShedule.get(10));

		return preFirstRountState;
	}

	/*********************************************************************
	 *********************************************************************
	 Decrypt performs the decryption of a given input based on a given key
	 and the 10 round expansion of that key.
	 *********************************************************************
	 *********************************************************************/

	public static int[][] decrypt(int[][] in, ArrayList<int[][]> keyShedule)
	{
		int[][] encryptedData = xor(in, keyShedule.get(10));							//creating the new int[][] array to store the output by adding the final key to the input
		for (int i = 9; i>0; i--) 														//the last nine rounds do counting down from 9:
		{																				
			encryptedData = invShiftRows(encryptedData);								//invshiftrows takes changes the 2nd to 4th rows of the current state
			encryptedData = invSubBytes(encryptedData);									//invsub bytes using the invsbox
			encryptedData = xor(encryptedData, keyShedule.get(i));						//add the round key
			encryptedData = invMixColumns(encryptedData);								//mix columns takes each column of the current state and mixes up the bits
		}																				
		encryptedData = invShiftRows(encryptedData);									//10th round has no mix columns
		encryptedData = invSubBytes(encryptedData);										
		encryptedData = xor(encryptedData, keyShedule.get(0));								

		return encryptedData;
	}

	// ************************** AES FUNCTIONS **************************

	/*********************************************************************
	 *********************************************************************
	 generateAvalancheData takes 2 arraylists of int[][]s that contain all
	 129 different Ps and 129 different Ks and generates the strings that
	 are outputted by each step of AES0 through AES4.
	 *********************************************************************
	 *********************************************************************/

	public static ArrayList<ArrayList<String>> generateAvalancheData(ArrayList<int[][]> pTextIn, ArrayList<int[][]> kIn)
	{
		for (int i=0; i<55; i++) {										//creating 55 new String arraylists and adding them to the avalanche data arraylist
			ArrayList<String> newList = new ArrayList<String>();		//it's 55 as the number of different average values for each table is 55
			data.add(newList);
		}
		ArrayList<int[][]> keyShedule = keyExpansion(kIn.get(0));		//the keyexpansion for P and Pi under K
		for (int i = 0; i<129; i++) {									//it is run 129 times, 1 for P under K and the 128 Ki's after
			int[][] pGoingIn0 = new int[4][4];							//each time new arrays are initialised to avoiding changing the stored data.
			int[][] pGoingIn1 = new int[4][4];							//theres one for each AES0 - AES4
			int[][] pGoingIn2 = new int[4][4];
			int[][] pGoingIn3 = new int[4][4];
			int[][] pGoingIn4 = new int[4][4];
			for (int j = 0; j<4; j++) 
			{
				for (int k = 0; k<4; k++) 
				{
					pGoingIn0[j][k] = pTextIn.get(i)[j][k];				//the current p text values are stored in all the arrays
					pGoingIn1[j][k] = pTextIn.get(i)[j][k];
					pGoingIn2[j][k] = pTextIn.get(i)[j][k];
					pGoingIn3[j][k] = pTextIn.get(i)[j][k];
					pGoingIn4[j][k] = pTextIn.get(i)[j][k];
				}
			}
			AES0(pGoingIn0, keyShedule);								//And AES0 - AES 4 are run on them
			AES1(pGoingIn1, keyShedule);
			AES2(pGoingIn2, keyShedule);
			AES3(pGoingIn3, keyShedule);
			AES4(pGoingIn4, keyShedule);
		}

		for (int i = 0; i<129; i++) {
			keyShedule = keyExpansion(kIn.get(i));						//for P under K and Ki the key expansion needs to be done every time
			int[][] pGoingIn0 = new int[4][4];							//new arrays for every AES
			int[][] pGoingIn1 = new int[4][4];
			int[][] pGoingIn2 = new int[4][4];
			int[][] pGoingIn3 = new int[4][4];
			int[][] pGoingIn4 = new int[4][4];
			for (int j = 0; j<4; j++) {									//setting their data again
				for (int k = 0; k<4; k++) {
					pGoingIn0[j][k] = pTextIn.get(0)[j][k];
					pGoingIn1[j][k] = pTextIn.get(0)[j][k];
					pGoingIn2[j][k] = pTextIn.get(0)[j][k];
					pGoingIn3[j][k] = pTextIn.get(0)[j][k];
					pGoingIn4[j][k] = pTextIn.get(0)[j][k];
				}
			}
			AES0SecondRun(pGoingIn0, keyShedule);						//running the AES's on them
			AES1SecondRun(pGoingIn1, keyShedule);
			AES2SecondRun(pGoingIn2, keyShedule);
			AES3SecondRun(pGoingIn3, keyShedule);
			AES4SecondRun(pGoingIn4, keyShedule);
		}
		return data;													//finally returning all ~15000 strings to be processed
	}

	public static int[][] AES0(int[][] in, ArrayList<int[][]> keyShedule)				//AES0 does exactly the same as encrypt except adds to the avalanche data at every round
	{
		data.get(0).add(matrixToString(in));											//adding original P to avalanche data for comparison
		int[][] preFirstRountState = xor(in, keyShedule.get(0));
		preFirstRountState = subBytes(preFirstRountState);
		preFirstRountState = shiftRows(preFirstRountState);
		preFirstRountState = mixColumns(preFirstRountState);
		preFirstRountState = xor(preFirstRountState, keyShedule.get(1));
		data.get(1).add(matrixToString(preFirstRountState));							//adding state after first round to avalanche data
		int[][] firstIn = new int[4][4];												//this variable will store the state after each round to make sure states don't get changed after being stored
		for (int i = 0; i<4; i++) {														//for loops to copy all of the elements of the previous state into the new one
			for (int j =0; j<4; j++) {
				firstIn[i][j] = preFirstRountState[i][j];
			}
		}
		for (int i = 1; i<9; i++) {														//perform the encryption steps 8 times
			int[][] input = subBytes(firstIn);
			input = shiftRows(input);
			input = mixColumns(input);
			input = xor(input, keyShedule.get(i+1));
			data.get(i+1).add(matrixToString(input));									//add the new state to the avalanche data
			firstIn = input;															//reset the temp variable to the new state to start again
		}
		int[][] lastIn = new int[4][4];													//the last piece of data for avalanche for this run of AES0
		for (int i = 0; i<4; i++) {														//for loops to copy the values of  temp variable containing the previous state in to the last state
			for (int j =0; j<4; j++) {
				lastIn[i][j] = firstIn[i][j];
			}
		}
		lastIn = subBytes(lastIn);														//last round operations on the last state
		lastIn = shiftRows(lastIn);
		lastIn = xor(lastIn, keyShedule.get(10));
		data.get(10).add(matrixToString(lastIn));										//add it to the avalanche data

		return lastIn;
	}

	public static int[][] AES1(int[][] in, ArrayList<int[][]> keyShedule)				//AES1 does exactly the same as AES0 except has no subBytes
	{
		data.get(11).add(matrixToString(in));
		int[][] preFirstRountState = xor(in, keyShedule.get(0));
		preFirstRountState = shiftRows(preFirstRountState);
		preFirstRountState = mixColumns(preFirstRountState);
		preFirstRountState = xor(preFirstRountState, keyShedule.get(1));
		data.get(12).add(matrixToString(preFirstRountState));
		int[][] firstIn = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j =0; j<4; j++) {
				firstIn[i][j] = preFirstRountState[i][j];
			}
		}
		for (int i = 1; i<9; i++) {
			int[][] input = shiftRows(firstIn);
			input = mixColumns(input);
			input = xor(input, keyShedule.get(i+1));
			data.get(i+12).add(matrixToString(input));
			firstIn = input;
		}
		int[][] lastIn = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j =0; j<4; j++) {
				lastIn[i][j] = firstIn[i][j];
			}
		}
		lastIn = shiftRows(lastIn);
		lastIn = xor(lastIn, keyShedule.get(10));
		data.get(21).add(matrixToString(lastIn));

		return lastIn;
	}

	public static int[][] AES2(int[][] in, ArrayList<int[][]> keyShedule)				//AES2 does exactly the same as AES0 except has no shiftrows
	{
		data.get(22).add(matrixToString(in));
		int[][] preFirstRountState = xor(in, keyShedule.get(0));
		preFirstRountState = subBytes(preFirstRountState);
		preFirstRountState = mixColumns(preFirstRountState);
		preFirstRountState = xor(preFirstRountState, keyShedule.get(1));
		data.get(23).add(matrixToString(preFirstRountState));
		int[][] firstIn = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j =0; j<4; j++) {
				firstIn[i][j] = preFirstRountState[i][j];
			}
		}
		for (int i = 1; i<9; i++) {
			int[][] input = subBytes(firstIn);
			input = mixColumns(input);
			input = xor(input, keyShedule.get(i+1));
			data.get(i+23).add(matrixToString(input));
			firstIn = input;
		}
		int[][] lastIn = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j =0; j<4; j++) {
				lastIn[i][j] = firstIn[i][j];
			}
		}
		lastIn = subBytes(lastIn);
		lastIn = xor(lastIn, keyShedule.get(10));
		data.get(32).add(matrixToString(lastIn));

		return lastIn;
	}

	public static int[][] AES3(int[][] in, ArrayList<int[][]> keyShedule)				//AES3 does exactly the same as AES0 except has no mixColumns
	{
		data.get(33).add(matrixToString(in));
		int[][] preFirstRountState = xor(in, keyShedule.get(0));
		preFirstRountState = subBytes(preFirstRountState);
		preFirstRountState = shiftRows(preFirstRountState);
		preFirstRountState = xor(preFirstRountState, keyShedule.get(1));
		data.get(34).add(matrixToString(preFirstRountState));
		int[][] firstIn = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j =0; j<4; j++) {
				firstIn[i][j] = preFirstRountState[i][j];
			}
		}
		for (int i = 1; i<9; i++) {
			int[][] input = subBytes(firstIn);
			input = shiftRows(input);
			input = xor(input, keyShedule.get(i+1));
			data.get(i+34).add(matrixToString(input));
			firstIn = input;
		}
		int[][] lastIn = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j =0; j<4; j++) {
				lastIn[i][j] = firstIn[i][j];
			}
		}
		lastIn = subBytes(lastIn);
		lastIn = shiftRows(lastIn);
		lastIn = xor(lastIn, keyShedule.get(10));
		data.get(43).add(matrixToString(lastIn));

		return lastIn;
	}

	public static int[][] AES4(int[][] in, ArrayList<int[][]> keyShedule)				//AES4 does exactly the same as AES0 except has no add round key (xor)
	{
		data.get(44).add(matrixToString(in));
		int[][] preFirstRountState = xor(in, keyShedule.get(0));
		preFirstRountState = subBytes(preFirstRountState);
		preFirstRountState = shiftRows(preFirstRountState);
		preFirstRountState = mixColumns(preFirstRountState);
		data.get(45).add(matrixToString(preFirstRountState));
		int[][] firstIn = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j =0; j<4; j++) {
				firstIn[i][j] = preFirstRountState[i][j];
			}
		}
		for (int i = 1; i<9; i++) {
			int[][] input = subBytes(firstIn);
			input = shiftRows(input);
			input = mixColumns(input);
			data.get(i+45).add(matrixToString(input));
			firstIn = input;
		}
		int[][] lastIn = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j =0; j<4; j++) {
				lastIn[i][j] = firstIn[i][j];
			}
		}
		lastIn = subBytes(lastIn);
		lastIn = shiftRows(lastIn);
		data.get(54).add(matrixToString(lastIn));

		return lastIn;
	}

	public static int[][] AES0SecondRun(int[][] in, ArrayList<int[][]> keyShedule)		//AES0SecondRun does exactly the same as AES0 except is for P under K and Ki
	{
		data.get(0).add(matrixToString(in));
		int[][] preFirstRountState = xor(in, keyShedule.get(0));
		preFirstRountState = subBytes(preFirstRountState);
		preFirstRountState = shiftRows(preFirstRountState);
		preFirstRountState = mixColumns(preFirstRountState);
		preFirstRountState = xor(preFirstRountState, keyShedule.get(1));
		data.get(1).add(matrixToString(preFirstRountState));
		int[][] firstIn = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j =0; j<4; j++) {
				firstIn[i][j] = preFirstRountState[i][j];
			}
		}
		for (int i = 1; i<9; i++) {
			int[][] input = subBytes(firstIn);
			input = shiftRows(input);
			input = mixColumns(input);
			input = xor(input, keyShedule.get(i+1));
			data.get(i+1).add(matrixToString(input));
			firstIn = input;
		}
		int[][] lastIn = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j =0; j<4; j++) {
				lastIn[i][j] = firstIn[i][j];
			}
		}
		lastIn = subBytes(lastIn);
		lastIn = shiftRows(lastIn);
		lastIn = xor(lastIn, keyShedule.get(10));
		data.get(10).add(matrixToString(lastIn));

		return lastIn;
	}

	public static int[][] AES1SecondRun(int[][] in, ArrayList<int[][]> keyShedule)		//AES1SecondRun does exactly the same as AES0SecondRun except has no subBytes
	{
		data.get(11).add(matrixToString(in));
		int[][] preFirstRountState = xor(in, keyShedule.get(0));
		preFirstRountState = shiftRows(preFirstRountState);
		preFirstRountState = mixColumns(preFirstRountState);
		preFirstRountState = xor(preFirstRountState, keyShedule.get(1));
		data.get(12).add(matrixToString(preFirstRountState));
		int[][] firstIn = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j =0; j<4; j++) {
				firstIn[i][j] = preFirstRountState[i][j];
			}
		}
		for (int i = 1; i<9; i++) {
			int[][] input = shiftRows(firstIn);
			input = mixColumns(input);
			input = xor(input, keyShedule.get(i+1));
			data.get(i+12).add(matrixToString(input));
			firstIn = input;
		}
		int[][] lastIn = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j =0; j<4; j++) {
				lastIn[i][j] = firstIn[i][j];
			}
		}
		lastIn = shiftRows(lastIn);
		lastIn = xor(lastIn, keyShedule.get(10));
		data.get(21).add(matrixToString(lastIn));

		return lastIn;
	}

	public static int[][] AES2SecondRun(int[][] in, ArrayList<int[][]> keyShedule)		//AES2SecondRun does exactly the same as AES0SecondRun except has no shiftRows
	{
		data.get(22).add(matrixToString(in));
		int[][] preFirstRountState = xor(in, keyShedule.get(0));
		preFirstRountState = subBytes(preFirstRountState);
		preFirstRountState = mixColumns(preFirstRountState);
		preFirstRountState = xor(preFirstRountState, keyShedule.get(1));
		data.get(23).add(matrixToString(preFirstRountState));
		int[][] firstIn = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j =0; j<4; j++) {
				firstIn[i][j] = preFirstRountState[i][j];
			}
		}
		for (int i = 1; i<9; i++) {
			int[][] input = subBytes(firstIn);
			input = mixColumns(input);
			input = xor(input, keyShedule.get(i+1));
			data.get(i+23).add(matrixToString(input));
			firstIn = input;
		}
		int[][] lastIn = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j =0; j<4; j++) {
				lastIn[i][j] = firstIn[i][j];
			}
		}
		lastIn = subBytes(lastIn);
		lastIn = xor(lastIn, keyShedule.get(10));
		data.get(32).add(matrixToString(lastIn));

		return lastIn;
	}

	public static int[][] AES3SecondRun(int[][] in, ArrayList<int[][]> keyShedule)		//AES3SecondRun does exactly the same as AES0SecondRun except has no mixColumns
	{
		data.get(33).add(matrixToString(in));
		int[][] preFirstRountState = xor(in, keyShedule.get(0));
		preFirstRountState = subBytes(preFirstRountState);
		preFirstRountState = shiftRows(preFirstRountState);
		preFirstRountState = xor(preFirstRountState, keyShedule.get(1));
		data.get(34).add(matrixToString(preFirstRountState));
		int[][] firstIn = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j =0; j<4; j++) {
				firstIn[i][j] = preFirstRountState[i][j];
			}
		}
		for (int i = 1; i<9; i++) {
			int[][] input = subBytes(firstIn);
			input = shiftRows(input);
			input = xor(input, keyShedule.get(i+1));
			data.get(i+34).add(matrixToString(input));
			firstIn = input;
		}
		int[][] lastIn = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j =0; j<4; j++) {
				lastIn[i][j] = firstIn[i][j];
			}
		}
		lastIn = subBytes(lastIn);
		lastIn = shiftRows(lastIn);
		lastIn = xor(lastIn, keyShedule.get(10));
		data.get(43).add(matrixToString(lastIn));

		return lastIn;
	}

	public static int[][] AES4SecondRun(int[][] in, ArrayList<int[][]> keyShedule)		//AES4SecondRun does exactly the same as AES0SecondRun except has no add round key (xor)
	{
		data.get(44).add(matrixToString(in));
		int[][] preFirstRountState = xor(in, keyShedule.get(0));
		preFirstRountState = subBytes(preFirstRountState);
		preFirstRountState = shiftRows(preFirstRountState);
		preFirstRountState = mixColumns(preFirstRountState);
		data.get(45).add(matrixToString(preFirstRountState));
		int[][] firstIn = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j =0; j<4; j++) {
				firstIn[i][j] = preFirstRountState[i][j];
			}
		}
		for (int i = 1; i<9; i++) {
			int[][] input = subBytes(firstIn);
			input = shiftRows(input);
			input = mixColumns(input);
			data.get(i+45).add(matrixToString(input));
			firstIn = input;
		}
		int[][] lastIn = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j =0; j<4; j++) {
				lastIn[i][j] = firstIn[i][j];
			}
		}
		lastIn = subBytes(lastIn);
		lastIn = shiftRows(lastIn);
		data.get(54).add(matrixToString(lastIn));

		return lastIn;
	}

	/*********************************************************************
	 *********************************************************************
	 mixColumns performs a matrix multiplication on a 2-d 16 byte array
	 to perform the mix columns step. invMixColumns undoes mixColumns
	 *********************************************************************
	 *********************************************************************/

	public static int[][] mixColumns(int[][] stateIn)
	{
		int[][] actualOutput = new int[4][4];						//creating a new array to store the values
		int[][] output = new int[4][4];
		for(int i = 0; i<4; i++) {									//storing the values from the input into a temp array
			for (int j = 0; j<4; j++) {
				output[i][j] = stateIn[i][j];
			}
		}

		for(int i = 0; i<4; i++) {																											//does this for each column
				actualOutput[0][i] = (peasantsAlgorithm(2, output[0][i])^peasantsAlgorithm(3, output[1][i])^output[2][i]^output[3][i]);		//this is the matrix multiplication
				actualOutput[1][i] = (output[0][i]^peasantsAlgorithm(2, output[1][i])^peasantsAlgorithm(3, output[2][i])^output[3][i]);		//peasants Algorithm performs Galois Field
				actualOutput[2][i] = (output[0][i]^output[1][i]^peasantsAlgorithm(2, output[2][i])^peasantsAlgorithm(3, output[3][i]));		//multiplication on the given inputs
				actualOutput[3][i] = (peasantsAlgorithm(3, output[0][i])^output[1][i]^output[2][i]^peasantsAlgorithm(2, output[3][i]));
		}
		return actualOutput;
	}

	public static int[][] invMixColumns(int[][] stateIn)			//identical to mixColumns except matrix values have changed
	{
		int[][] actualOutput = new int[4][4];
		int[][] output = new int[4][4];
		for(int i = 0; i<4; i++) 
		{
			for (int j = 0; j<4; j++) 
			{
				output[i][j] = stateIn[i][j];
			}
		}

		for(int i = 0; i<4; i++) {
				actualOutput[0][i] = (peasantsAlgorithm(0x0e, output[0][i])^peasantsAlgorithm(0x0b, output[1][i])^peasantsAlgorithm(0x0d, output[2][i])^peasantsAlgorithm(0x09, output[3][i]));
				actualOutput[1][i] = (peasantsAlgorithm(0x09, output[0][i])^peasantsAlgorithm(0x0e, output[1][i])^peasantsAlgorithm(0x0b, output[2][i])^peasantsAlgorithm(0x0d, output[3][i]));
				actualOutput[2][i] = (peasantsAlgorithm(0x0d, output[0][i])^peasantsAlgorithm(0x09, output[1][i])^peasantsAlgorithm(0x0e, output[2][i])^peasantsAlgorithm(0x0b, output[3][i]));
				actualOutput[3][i] = (peasantsAlgorithm(0x0b, output[0][i])^peasantsAlgorithm(0x0d, output[1][i])^peasantsAlgorithm(0x09, output[2][i])^peasantsAlgorithm(0x0e, output[3][i]));
		}
		return actualOutput;
	}

	/*********************************************************************
	 *********************************************************************
	 This is the function for GF(2^8) multiplication, used for mixColumns.
	 Includes rightMostBitSet and leftMostBitSet functions.
	 *********************************************************************
	 *********************************************************************/

	public static int peasantsAlgorithm(int aIn, int bIn)
	{
		int p = 0, a = aIn, b = bIn;				//intialises a and b to the inputs and p to zero
		for(int i = 0; i<8; i++)
		{
			if(rightMostBitSet(b))					//if the right most bit of b is set i.e. 1, p is xored with a
			{
				p ^= a;
			}

			b = b >> 1;								//the bits of b are shifted to the right 1 place

			boolean carry = leftMostBitSet(a);		//store whether or not the left most bit of a is set i.e. 1

			a = a << 1;								//shift the bits of a to the left 1 place

			if(carry)								//if the left bit of a was set a gets xored with 27
			{
				a ^= 0x1b;
			}

			if(a == 0 || b == 0)					//if either of a or b are 0 then nothing more will change so can stop
			{
				break;
			}
		}
		return p & 0xFF;							//& to remove carry bit
	}

	// Takes a byte as input
	// returns true if right-most bit is 1
	// returns false otherwise
	public static boolean rightMostBitSet(int byteIn)
	{
		int bit = (byteIn & 1);
		return (bit == 1) ? true : false;
	}

	// Takes a byte as input
	// returns true if left-most bit is 1
	// returns false otherwise
	public static boolean leftMostBitSet(int byteIn)
	{
		int bit = (byteIn & 0x80);
		return (bit == 0x80) ? true : false;
	}

	/*********************************************************************
	 *********************************************************************
	 This is the xor function, takes a 4x4 matrix of values, xors them
	 with a given key and returns the result, it's the first step of each
	 round. The one for first round uses the plaintext key. xorWord xors
	 2 4 byte arrays.
	 *********************************************************************
	 *********************************************************************/

	public static int[][] xor(int[][] currentRoundInputIn, int[][] keyIn)
	{
		int[][] current = currentRoundInputIn;							
		int[][] output = new int[4][4];									//new array to store the new values
		for(int i = 0; i<4; i++)										//store the values of the old array xored with the values of the key
		{
			for(int j = 0; j<4; j++)
			{
				output[i][j] = current[i][j]^keyIn[i][j];
			}
		}
		return output;
	}

	public static int[] wordXor(int[] wordIn, int[]xoredWith)
	{
		int[] output = new int[4];
		for(int j = 0; j<4; j++)
		{
			output[j] = wordIn[j]^xoredWith[j];							//same as above but with 1-d array
		}
		return output;
	}

	/*********************************************************************
	 *********************************************************************
	 Key expansion method creates 10 keys based on the input key and
	 stores them in the keyShedule arraylist.
	 *********************************************************************
	 *********************************************************************/

	public static ArrayList<int[][]> keyExpansion(int[][] key)
	{
		ArrayList<int[][]> keyShedule = new ArrayList<int[][]>();			//where the keys will be stored
		keyShedule.add(key);												//adding the initial key
		for(int j = 0; j < 10; j++){										//new array for each word of the new key and the old key to avoid changing any values after storage
			int[] newFirstWord = new int[4];
			int[] newSecondWord = new int[4];
			int[] newThirdWord = new int[4];
			int[] newFourthWord = new int[4];
			int[] oldFourthWord = new int[4];
			int[] oldFirstWord = new int[4];
			int[] oldSecondWord = new int[4];
			int[] oldThirdWord = new int[4];
			for(int i = 0; i<4; i++)										//copying all the values of the previous key into the old key arrays for processing
			{
				oldFirstWord[i] = keyShedule.get(j)[i][0];
				oldSecondWord[i] = keyShedule.get(j)[i][1];
				oldThirdWord[i] = keyShedule.get(j)[i][2];
				oldFourthWord[i] = keyShedule.get(j)[i][3];
			}
			newFirstWord = rotWord(oldFourthWord);							//new first is rot word on old fourth
			newFirstWord = subWord(newFirstWord);							//then substituted
			newFirstWord = wordXor(newFirstWord, rCon[j]);					//then xored with the round constant
			newFirstWord = wordXor(newFirstWord, oldFirstWord);				//then xored with the old first word
			newSecondWord = wordXor(oldSecondWord, newFirstWord);			//new second word is old second word xored with new first word
			newThirdWord = wordXor(oldThirdWord, newSecondWord);			//new third word is old third word xored with new second word
			newFourthWord = wordXor(oldFourthWord, newThirdWord);			//new fourth word is old fourth word xored with new third word
			int[][] output = new int[4][4];									//new array to store all of the new key
			for (int k = 0; k<4; k++) {										//setting data from all new words to output array
				output[k][0] = newFirstWord[k];
				output[k][1] = newSecondWord[k];
				output[k][2] = newThirdWord[k];
				output[k][3] = newFourthWord[k];
			}

			keyShedule.add(output);											//adding that to the key schedule
		}
		return keyShedule;
	}

	/*********************************************************************
	 *********************************************************************
	 subByte takes a byte of data and substitutes it for a value in the 
	 sBox, subWord uses this function for a 4 byte array and subBytes uses
	 this function for 2-D 16 byte array.
	 *********************************************************************
	 *********************************************************************/
	public static int subByte(int byteIn)
	{
		String byteToHex = String.format("%02X", byteIn);			// Turns byte into hexadecimal
		int r = (Integer.decode("0x0" + byteToHex.charAt(0)));		// Stores row value
		int c = (Integer.decode("0x0" + byteToHex.charAt(1)));		// Stores column value
		return sBox[r][c];											// Returns value in sBox at row r, column c 
	}

	/*********************************************************************
	 *********************************************************************
	 subWord uses subBytes as 4 bytes = 1 word
	 *********************************************************************
	 *********************************************************************/
	public static int[] subWord(int[] wordIn)
	{
		int[] wordOut = new int[4];									// Create new int array
		for(int i = 0; i < 4; i++)									// For each byte
		{
			wordOut[i] = wordIn[i];									// Copy into wordOut
			wordOut[i] = subByte(wordOut[i]);						// Perform subBytes on wordOut[i]
		}
		return wordOut;												// Return wordOut
	}
	/*********************************************************************
	 *********************************************************************
	 Performs subSyte on a 4x4 matrix, returns result in a new 4x4 array
	 *********************************************************************
	 *********************************************************************/
	public static int[][] subBytes(int[][] bytesIn)
	{
		int[][] bytesOut = new int[4][4];							// Create a new 4x4 int array
		for(int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				bytesOut[i][j] = subByte(bytesIn[i][j]);			// Perform subByte on element inbytesIn and store in bytesOut
			}
		}
		return bytesOut;											// Return bytesOut
	}
	/*********************************************************************
	 *********************************************************************
	 invSubByte takes the byte and uses the lookup table to find the original
	 value
	 *********************************************************************
	 *********************************************************************/
	public static int invSubByte(int byteIn)
	{
		String byteToHex = String.format("%02X", byteIn);			// Turn byte into hexadecimal representation
		int r = (Integer.decode("0x0" + byteToHex.charAt(0)));		// Turns hexadecimal into decimal (row we need)
		int c = (Integer.decode("0x0" + byteToHex.charAt(1)));		// Turns hexadecimal into decimal (column we need)
		return invSBox[r][c];										// Returns value at row r, column c in invSBox
	}
	/*********************************************************************
	 *********************************************************************
	 invSubWord uses invSubByte to decode each of the bytes in the word given
	 *********************************************************************
	 *********************************************************************/
	public static int[] invSubWord(int[] wordIn)
	{
		int[] wordOut = new int[4];									// Create a new int array
		for(int i = 0; i < 4; i++)
		{
			wordOut[i] = wordIn[i];									// Copy the input
			wordOut[i] = invSubByte(wordOut[i]);					// Perform invSubByte on wordOut's copy
		}
		return wordOut;
	}
	/*********************************************************************
	 *********************************************************************
	 Performs invSubBytes on a 4x4 array, returns a new 4x4 array
	 *********************************************************************
	 *********************************************************************/
	public static int[][] invSubBytes(int[][] bytesIn){
		int[][] bytesOut = new int[4][4];							// Create a new 4x4 int array
		for(int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				bytesOut[i][j] = invSubByte(bytesIn[i][j]);			// invSubByte on each element
			}
		}
		return bytesOut;
	}

	/*********************************************************************
	 *********************************************************************
	 Rotword takes a 4 byte array and puts the first byte on the end and
	 returns a new array
	 Rotwordright takes a 4 byte array and puts the last byte on the front
	 returns a new array
	 *********************************************************************
	 *********************************************************************/

	public static int[] rotWord(int[] wordIn)
	{
		int[] wordOut = new int[4];									// Create a new array which will be returned
		int temp = wordIn[0];										// save element that will be written over
		for(int i = 0; i < 4; i++)
		{
			wordOut[i] = wordIn[i];									// copy each element that in to new array
		}
		for(int i = 0;i<3;i++)										// move each element to its left
		{
			wordOut[i] = wordOut[i+1];
		}
		wordOut[3] = temp;											// place the saved element at the end (bar the first element)
		return wordOut;
	}

	public static int[] rotWordRight(int[] wordIn)
	{
		int[] wordOut = new int[4];									// create a new array of size 4
		int temp = wordIn[3];										// temporarily hold element which will be written over
		for(int i = 0; i < 4; i++)									// copy elements in wordIn to a new array
		{
			wordOut[i] = wordIn[i];
		}
		for(int i = 3;i>0;i--)										// move each element to its left, (bar the last element)
		{
			wordOut[i] = wordOut[i-1];
		}
		wordOut[0] = temp;											// place last element's value in the first index
		return wordOut;
	}

	/*********************************************************************
	 *********************************************************************
	 Shift rows performs a single rot word on the second row of a 2-d
	 matrix, 2 rotwords on the third row of the matrix and 3 rotWords on 
	 the fourth row of the matrix. Inverse shift rows does the same thing
	 but using rotWordRight instead.
	 *********************************************************************
	 *********************************************************************/

	public static int[][] shiftRows(int[][] stateIn)
	{
		int[][] output = new int[4][4];								// Crate new 4x4 in array
		for (int i = 0; i<4; i++) 
		{
			output[0][i] = stateIn[0][i];							// Copy first row of stateIn into output
		}
		// row 2 shift left by 1
		output[1] = rotWord(stateIn[1]);
		// row 3 shift left by 2
		output[2] = rotWord(stateIn[2]);
		output[2] = rotWord(output[2]);
		//row 4 shift left by 3
		output[3] = rotWord(stateIn[3]);
		output[3] = rotWord(output[3]);
		output[3] = rotWord(output[3]);
		return output;
	}

	public static int[][] invShiftRows(int[][] stateIn)
	{
		int[][] output = new int[4][4];									// Create a new 4x4 int array
		for (int i = 0; i<4; i++) 
		{
			output[0][i] = stateIn[0][i];								// Copy first row of stateIn into output
		}
		// Row 2 shift right by 1
		output[1] = rotWordRight(stateIn[1]);
		// Row 3 shift right by 2
		output[2] = rotWordRight(stateIn[2]);
		output[2] = rotWordRight(output[2]);
		// Row 4 shift right by 3
		output[3] = rotWordRight(stateIn[3]);
		output[3] = rotWordRight(output[3]);
		output[3] = rotWordRight(output[3]);
		return output;
	}

	/*********************************************************************
	 *********************************************************************
	 Takes a binary string and alters 1 bit each time, until all bits have 
	 been altered by 1. Returned ArrayList's first element is the original
	 String, with each consecutive elemtent having the next bit altered 
	 from the left
	 *********************************************************************
	 *********************************************************************/
	public static ArrayList<int[][]> avFlipBit(String input)
	{
		ArrayList<int[][]> returnList = new ArrayList<int[][]>();		// Create an ArrayList of type int[][] which will be populated with the altered strings
		// for 128 bits
		String original = input;										// Create a copy of the original input
		returnList.add(stringToArray(original));						// Add original input to returnList
		String copyString = "";											// Instantiate a String that the new strings will be put into
		if(input.charAt(0) == '0')										// Change first bit
		{
			copyString = "1";
			copyString+=input.substring(1);
		}
		else
		{
			copyString="0";
			copyString+=input.substring(1);	
		}
		returnList.add(stringToArray(copyString));						// Add to returnList after it is turned in to 4x4 array
		for(int i=1;i<127;i++)											// For all of the other remaining bits other than the last one
		{
			if(input.charAt(i) == '0')									// Change the bit at i
			{
				copyString = input.substring(0,i);
				copyString+="1";
				copyString+= input.substring(i+1);
			}
			else
			{
				copyString = input.substring(0,i);
				copyString+="0";
				copyString+= input.substring(i+1);
			}
			returnList.add(stringToArray(copyString));					// Add to returnList
		}
		if(input.charAt(127) == '0')									// Alter the last bit
		{
			copyString = input.substring(0,127);
			copyString+="1";
		}
		else
		{
			copyString = input.substring(0,127);
			copyString+="0";
		}
		returnList.add(stringToArray(copyString));						// Add to returnList
		return returnList;
	}

	public static int[][] stringToArray(String input)					// Takes a 128 bit string, turns it into a 4x4 array of each element decimal representation of 8 bits
	{
		int[][] returnMe = new int[4][4];								// Instantiate new 4x4 array
		String origInput = input;										// Copy input
		String word = "";												// Instantiate new string
		int l = 0; int m = 0;											// Used to keep track of rows and columns
		for (int i = 0; i<16; i++) 
		{
			word = "";													// Reset word as a new string
			for (int j = 0; j<8; j++) 
			{
				word += origInput.charAt(0);							// Add charAt(0) to word
				origInput = origInput.substring(1);						// Remove charAt(0) from origInput
			}
			returnMe[l][m] = Integer.parseInt(word, 2);					// Turn binary to decimal representation
			if((i+1)%4 == 0 && i != 0)									// Keep track of column count
			{
				m++;													// Go to new column
				l = 0;													// Reset row count
			}
			else
			{
				l++;													// Go to new row
			}
		}
		return returnMe;
	}

	public static String matrixToString(int[][] input)					// Turns a 4x4 matrixx into a String, by columns
	{
		String s ="";													// Instantiate new String
		for(int i=0;i<4;i++)
		{
			for(int j=0; j<4;j++)
			{
				s+=String.format("%8s", Integer.toBinaryString(input[j][i])).replace(' ', '0'); // turns integer to 8 bit binary number, add it on to the end of the string
			}
		}
		return s; 														// Return string
	}

	public static int bitsDifferent(String s1, String s2)				// Counts how many bits are different in two 128 bit strings
	{
		int count = 0;													// Instanitate a counter
		for (int i = 0; i<128; i++) {									// For each bit of the strings
			if(s1.charAt(i) != s2.charAt(i))							// Compare them, if they are not equal
			{
				count++;												// Increment counter
			}
		}
		return count;													// Return counter
	}
}