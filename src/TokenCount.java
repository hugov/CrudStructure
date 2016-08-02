import br.com.decimal.sword.mapper.FileUtil;

/**
 * 
 */

/**
 * @author Vitor Hugo Oliveira
 *
 */
public class TokenCount {
	
	public static void main(String[] args) {
		
		String tokens = FileUtil.read("./file.txt");
//		String tokens = "{{{}}";
		char [] values = tokens.toCharArray();
		
		int token_1 = 0, token_2 = 0, token_3 = 0, token_4 = 0, token_5 = 0, token_6 = 0;
		
		for(int i = 0; i < values.length; i++) {
			
			if( values[i] == '{' ) {
				token_1++;
			}
			
			if( values[i] == '}' ) {
				token_2++;
			}
			
			if( values[i] == '[' ) {
				token_3++;
			}
			
			if( values[i] == ']' ) {
				token_4++;
			}
			
			if( values[i] == '(' ) {
				token_5++;
			}
			
			if( values[i] == ')' ) {
				token_6++;
			}
		}
		
		System.out.println("{ = Count " + token_1);
		System.out.println("} = Count " + token_2);
		System.out.println("[ = Count " + token_3);
		System.out.println("] = Count " + token_4);
		System.out.println("( = Count " + token_5);
		System.out.println(") = Count " + token_6);
		
	}

}
