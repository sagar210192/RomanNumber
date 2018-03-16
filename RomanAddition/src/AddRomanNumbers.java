import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Siddhesh
 * 
 *  I would like to first define a few terms :
 *  A. Grouping equivalence: combining the same symbols to form a larger one e.g.: VV = X, XXXXX = L, etc
 *  B. Subtractive Forms: Subtract a symbol representing a 1, 10, or 100 from the next two higher symbols, 
 *     respectively, by writing the smaller to the left. e.g.: IV, IX, XL, CM,etc.
 */
//hello World

//hello world 2.0
public class AddRomanNumbers {

	public static void main(String[] args) {
		if(args.length == 0){
			System.out.println("Please supply the 2 roman numbers as arguements to command line call");
		}else if(args.length == 1){
			System.out.println("Assuming the second roman number is zero");
			System.out.println("The answer is " + args[0]);
		}else if(args.length == 2){
			if(!validateNumber(args[0])){
				System.out.println("The first number is not a valid roman number");
			}else if(!validateNumber(args[1])){
				System.out.println("The second number is not a valid roman number");
			}else{
				/**
				 * If the execution reaches here, it means the input is valid and we can add the two numbers.
				 * Addition of two Roman numerals can be described in a 5 step process.
				 * Step 1: Un-compact both the Roman Values, i.e substituting the subtractive form mentioned above.
				 * 		   For example, substitute IX with VIIII. 
				 * Step 2: Combining the two numbers i.e. catenating them.
				 * Step 3: Sort from left-to-right with the largest symbol on the left
				 * Step 4: Combine similar symbols to obtain grouping equivalence.
				 * Step 5: Compact the sum i.e. putting subtractive form wherever possible.
				 */
				//Step 1 - Substituting the subtractive form
				String firstNumber = uncompact(args[0]);
				String secondNumber = uncompact(args[1]);
				System.out.println(firstNumber + " " + secondNumber);
				//Step 2 - Cancatenating the two flattened numbers to obtain the sum
				String sum = firstNumber.concat(secondNumber);
				System.out.println(sum);
				//Step 3 - Sorting the sum by putting the largest symbol first
				sum = sort(sum);
				System.out.println(sum);
				//Step 4 - Grouping equivalence to obtain the higher value symbol
				//         We do this from right to left of the sum obtained in the previous step.
				sum = group(sum);
				System.out.println(sum);
				//Step 5 - Compacting i.e. putting back the subtractive forms wherever applicable.
				sum = compact(sum);
				System.out.println(sum);
			}
		}else{
			System.out.println("You can add only two roman numbers at a time");
		}
	}
	
	/**
	 * Function call to check whether a given number is a valid Roman number
	 * @param number - Roman number passed for validation
	 * @return whether it is a valid Roman numeral(true) or not(false)
	 * I have used the regex package for matching the pattern
	 */
	public static boolean validateNumber(String number){
		/**
		 * The best way to match a string to a common pattern is using regular expressions. 
		 * Derivation of the regular expression pattern for a Roman numerals (1-3999) is as follows:
		 * M{0,3} - specifies the thousands section and basically restrains it to between 0 and 4000
		 *    0: <empty>  matched by M{0}
		 * 1000: M        matched by M{1}
		 * 2000: MM       matched by M{2} 
		 * 3000: MMM      matched by M{3}
		 * (CM|CD|D?C{0,3}) - specifies the hundreds section and covers all the possibilities
		 *   0: <empty>  matched by D?C{0} (with D not there)
		 * 100: C        matched by D?C{1} (with D not there) 
		 * 200: CC       matched by D?C{2} (with D not there) 
		 * 300: CCC      matched by D?C{3} (with D not there)
		 * 400: CD       matched by CD
		 * 500: D        matched by D?C{0} (with D there)
		 * 600: DC       matched by D?C{1} (with D there)
		 * 700: DCC      matched by D?C{2} (with D there)
		 * 800: DCCC     matched by D?C{3} (with D there)
		 * 900: CM       matched by CM
		 * (XC|XL|L?X{0,3}) and (IX|IV|V?I{0,3}) follow the same rules as mentioned above
		 * for tens (10-90) and units (1-9) places
		 */
		String romanLiteralsRegex = "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
		Pattern pattern = Pattern.compile(romanLiteralsRegex);
		Matcher matcher = pattern.matcher(number);
		return matcher.matches();
	}
	
	public static String uncompact(String number){
		String answer = number.replace("CM", "DCCCC");
		answer = answer.replace("CD", "CCCC");
		answer = answer.replace("XC", "LXXXX");
		answer = answer.replace("XL", "XXXX");
		answer = answer.replace("IX", "VIIII");
		answer = answer.replace("IV", "IIII");
		return answer;
	}
	
	
	public static String sort(String number){
		char[] symbols = {'M', 'D', 'C', 'L', 'X', 'V', 'I'};
		HashMap<Character, Integer> symbolCount = new HashMap<Character, Integer>();
		StringBuilder sb = new StringBuilder("");
		for(int i = 0; i < number.length(); i++){
			if(symbolCount.containsKey(number.charAt(i))){
				symbolCount.put(number.charAt(i), symbolCount.get(number.charAt(i))+1);
			}else{
				symbolCount.put(number.charAt(i), 1);
			}
		}
		for(int i = 0; i < symbols.length; i++){
			if(symbolCount.containsKey(symbols[i])){
				int count = symbolCount.get(symbols[i]);
				while(count>0){
					sb.append(symbols[i]);
					count--;
				}
			}
		}
		return sb.toString();
	}
	
	public static String group(String number){
		String answer = number.replace("IIIII", "V");
		System.out.println(answer);
		answer = answer.replace("VV", "X");
		System.out.println(answer);
		answer = answer.replace("XXXXX", "L");
		System.out.println(answer);
		answer = answer.replace("LL", "C");
		System.out.println(answer);
		answer = answer.replace("CCCCC", "D");
		System.out.println(answer);
		answer = answer.replace("DD", "M");
		System.out.println(answer);
		return answer;
	}
	
	public static String compact(String number){
		String answer = number.replace("VIIII", "IX");
		answer = answer.replace("IIII", "IV");
		answer = answer.replace("LXXXX", "XC");
		answer = answer.replace("XXXX", "XL");
		answer = answer.replace("DCCCC", "CM");
		answer = answer.replace("CCCC", "CD");
		return answer;
	}

}
