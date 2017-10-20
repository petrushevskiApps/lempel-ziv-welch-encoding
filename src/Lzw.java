/*
 * Created by : Aleksandar Petrushevski
 * Date : 01.09.2017
 * Project : Multimedia Systems
 * 
	 * Test Word : abababbabaabbabbaabba
	 * Test Result : 57.14286 %
	 * 
	 * Test Word : /THIS/IS/HIS/IS/
	 * Test Result : 31.25 %
*/

import java.util.ArrayList;
import java.util.Scanner;

public class Lzw {

	static ArrayList<String> table = new ArrayList<>();
	static ArrayList<Integer> code = new ArrayList<>();
	
	public static void main(String[] args) 
	{
		/* Input Sequence for coding */
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter sequence for encoding : ");
		String input = scanner.nextLine();
		char[] sequence = input.toCharArray();
		scanner.close(); 
		/* END OF INPUT */
		
		encoding(sequence);
				
		System.out.println("Table: ");
		for(String s : table) System.out.println(table.indexOf(s)+1 + ": " + s);
		
		System.out.print("Encoded sequnce: ");
		for(Integer c : code) System.out.print(c+" ");

		/* Calculating compression percent */
		double ratio = ((double)sequence.length - (double)code.size())/(double)sequence.length;
		System.err.println("\n" + "Compression : " + (float)ratio*100 + " %");
	}
	

	private static void encoding(char[] s) 
	{
		/* Initialize Starting Table */
		table.add(Character.toString(s[0]));
		
		for(int i=1; i<s.length; i++)
		{
			String toBeCompared = Character.toString(s[i]);
			if( !table.contains(toBeCompared)) table.add(toBeCompared); 
		}
		
		/* ENCODING */
		int i=0;
		while(i<s.length)
		{
			StringBuilder sb = new StringBuilder();
			if(i+1<s.length)
			{
				// Add new letter to the current letter
				sb.append(charToStr(s[i])).append(charToStr(s[++i]));
				
				/*
				 *	If the new combination is not in the table:
				 * 	1) Code the new combination of letters
				 * 	2) Add the new combination of letters to the table
				*/
				if( !table.contains(sb.toString())) 
				{
					code.add(table.indexOf(charToStr(s[i-1]))+1);
					table.add(sb.toString());
				}
				/*
				 * If the combination ( sb ) exists in the table:
				 * 	- Add new letter to the combination
				 * 	- Continue checking if the combination exists
				 * 	until you find new combination or you get to the end
				 */
				else
				{
					String temp = "";
					while(table.contains(sb.toString()))
					{
						temp = sb.toString();
						i++;
						if(i<s.length)sb.append(charToStr(s[i]));
						else break;
					}
					
					code.add(table.indexOf(temp)+1);
					if( !table.contains(sb.toString()))
					{
						table.add(sb.toString());
					}
							
				}
				
			}
			else break;
			
		}
		/* ENCODING END */
	}

	/* Convert char to String */
	private static String charToStr(char c)
	{
		return Character.toString(c);
	}
}
