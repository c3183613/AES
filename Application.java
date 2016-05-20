/******************************************************************************************************************************
// Application for COMP3260 assignment 2
// Programmers: Chris O'Donnell (c3165328) and Jeremy Law(c3183613)
// Date Completed: 20/5/2016
// Purpose of class: Main Class which takes input, encrypts or decrypts, and outputs to a file called "output.txt"
*******************************************************************************************************************************/

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Application
{
	// recieves two lines of binary input, prints the input 
	// encrypts and prints encrypted plaintext, decrypts and prints plaintext
	public static void main(String[] args) throws FileNotFoundException
	{
		String eOrD = args[0];														// store argument 0 
		Scanner fileScanner = null;													// initialize a new Scanner
		try
		{
			fileScanner = new Scanner(new File(args[1]));							// instantiate a new scanner with input file

		}
		catch(FileNotFoundException fnfe)											// throw file not found exception
		{
			System.out.println("File not found!");									// print "File not found!"
		}
		finally
		{
			// take input
			String binaryIn = fileScanner.nextLine();								// Store first line of input file
			String binaryInCopy = binaryIn;											// Create a copy of binaryIn
			// take key
			String keyIn = fileScanner.nextLine();									// Store second line of input file
			String keyInCopy = keyIn;												// Create a copy of keyIn
			// initialization 
			int[][] input = new int[4][4];											// Initialize a 4x4 array for input
			int[][] key = new int[4][4];											// Initialize a 4x4 array for key
			int l = 0, m = 0;														// used to keep track of x and y values of 2d array
			// do this for 16 bytes
			for (int i = 0; i<16; i++) 
			{
				String in = "";
				for (int j = 0; j<8; j++)											// 8 times for 8 bits = 1 byte
				{
					in += binaryIn.charAt(0);										// Add the first character of binaryIn to 'in'
					binaryIn = binaryIn.substring(1);								// Remove first character from original string
				}
				String keyWordIn = "";												// Instantiate new string for key
				for (int j = 0; j<8; j++) 
				{
					keyWordIn += keyIn.charAt(0);									// Add the first character of keyIn to 'keyWordIn'
					keyIn = keyIn.substring(1);										// Remove first character of original string
				}
				input[l][m] = Integer.parseInt(in, 2);								// turn decimal representation of in into a binary number
				key[l][m] = Integer.parseInt(keyWordIn, 2);							// turn decimal representation of keyWordIn into a binary number
				if((i+1)%4 == 0 && i != 0)											// keep track of which index we are at in the 2d array
				{
					m++;															// go to next column
					l = 0;															// reset row count
				}
				else
				{
					l++;															// go to next row
				}
			}
			ArrayList<int[][]> keyShedule = new ArrayList<int[][]>();				// Instantiate a new ArrayList of type int[][]
			keyShedule = AES.keyExpansion(key);										// perform keyExpansion on key
			PrintWriter writeToFile = null;											// Initialize PrintWriter type to null
			if(eOrD.equals("E") || eOrD.equals("e"))								// if args[0] is equal to upper or lower case for of e, enter scope
			{
				ArrayList<int[][]> whatDoInput	 = AES.avFlipBit(binaryInCopy);		// perform avalanche flip bit for binaryInCopy and create ArrayList to store it
				ArrayList<int[][]> whatDoKey	 = AES.avFlipBit(keyInCopy);		// perform avalanche flip bit for keyInCopy and create ArrayList to store it
				// Print out the hexadecimal representation of each array element in a cube
				long startTime = System.currentTimeMillis();						// Store the current time as a long to use late
				// encrypt the input using the key given
				int[][] output = AES.encrypt(input, keyShedule);					// encrypt input using keyShedule
				long duration = (System.currentTimeMillis() - startTime);			// subtract current time from startTime to get running time of encryption
				ArrayList<ArrayList<String>> data = AES.generateAvalancheData(whatDoInput, whatDoKey);			// generate avalanche data using whatDoInput and whatDoKey
																												// which returns type ArrayList<ArrayList<String>> 
				float firstCount = 0;												// Instantiate new float
				float secondCount = 0;												// Instantiate new float
				ArrayList<Integer> aES0List = new ArrayList<Integer>();				// Create new ArrayLists for Avalanche data
				ArrayList<Integer> aES1List = new ArrayList<Integer>();
				ArrayList<Integer> aES2List = new ArrayList<Integer>();
				ArrayList<Integer> aES3List = new ArrayList<Integer>();
				ArrayList<Integer> aES4List = new ArrayList<Integer>();
				ArrayList<Integer> aES0ListSecond = new ArrayList<Integer>();
				ArrayList<Integer> aES1ListSecond = new ArrayList<Integer>();
				ArrayList<Integer> aES2ListSecond = new ArrayList<Integer>();
				ArrayList<Integer> aES3ListSecond = new ArrayList<Integer>();
				ArrayList<Integer> aES4ListSecond = new ArrayList<Integer>();
				for (int j = 0; j<55; j++) 											// run for 55 times ()
				{
					firstCount = secondCount = 0;									// Reset counts 
					for (int i = 1; i<129; i++) 									// get summation of different bits
					{
						firstCount += AES.bitsDifferent(data.get(j).get(0), data.get(j).get(i));
					}
					if(j < 11)														// take average 
						aES0List.add(new Integer(Math.round(firstCount/128)));
					else if(11 <= j && j < 22)
						aES1List.add(new Integer(Math.round(firstCount/128)));
					else if(22 <= j && j < 33)
						aES2List.add(new Integer(Math.round(firstCount/128)));
					else if(33 <= j && j < 44)
						aES3List.add(new Integer(Math.round(firstCount/128)));
					else if(j < 55)
						aES4List.add(new Integer(Math.round(firstCount/128)));
					for (int i = 130; i<258; i++) 									// add all different bits
					{
						secondCount += AES.bitsDifferent(data.get(j).get(0), data.get(j).get(i));
					}																// take average
					if(j < 11)
						aES0ListSecond.add(new Integer(Math.round(secondCount/128)));
					else if(11 <= j && j < 22)
						aES1ListSecond.add(new Integer(Math.round(secondCount/128)));
					else if(22 <= j && j < 33)
						aES2ListSecond.add(new Integer(Math.round(secondCount/128)));
					else if(33 <= j && j < 44)
						aES3ListSecond.add(new Integer(Math.round(secondCount/128)));
					else if(j < 55)
						aES4ListSecond.add(new Integer(Math.round(secondCount/128)));
				}
				try
				{
					writeToFile = new PrintWriter("output.txt", "UTF-8");
					writeToFile.println("ENCRYPTION");
					writeToFile.println("Plaintext P: \t" + AES.matrixToString(input));
					writeToFile.println("Key K: \t\t" + AES.matrixToString(key));
					writeToFile.println("Ciphertext C: \t" + AES.matrixToString(output));
					writeToFile.println("Running Time: \t"+duration+"ms");
					writeToFile.println("Avalanche:\nP and Pi under K");
					writeToFile.println("Round\t\tAES0\tAES1\tAES2\tAES3\tAES4");
					for(int i = 0; i<aES0List.size();i++)
					{
						writeToFile.println("  " + i + "\t\t" + aES0List.get(i) + "\t" + aES1List.get(i) + "\t" + aES2List.get(i) + "\t" + aES3List.get(i) + "\t" + aES4List.get(i));
					}
					writeToFile.println("P under K and Ki");
					writeToFile.println("Round\t\tAES0\tAES1\tAES2\tAES3\tAES4");
					for(int i = 0; i<aES0List.size()-1;i++)
					{
						writeToFile.println("  " + i + "\t\t" + aES0ListSecond.get(i) + "\t" + aES1ListSecond.get(i) + "\t" + aES2ListSecond.get(i) + "\t" + aES3ListSecond.get(i) + "\t" + aES4ListSecond.get(i));
					}
					writeToFile.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else if(eOrD.equals("d") || eOrD.equals("D"))
			{
				try
				{
					writeToFile = new PrintWriter("output.txt", "UTF-8");
					int[][] decrypted = AES.decrypt(input, keyShedule);
					writeToFile.println("DECRYPTION");
					writeToFile.println("Ciphertext C: \t"+binaryInCopy);
					writeToFile.println("Key K: \t\t"+keyInCopy);
					writeToFile.println("Plaintext P: \t"+AES.matrixToString(decrypted));
					writeToFile.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println("Invalid input. Program is now exitting.");
			}

		}
	}

	
}