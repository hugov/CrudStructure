/**
 * 
 */
package br.com.decimal.sword.mapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vitor Hugo Oliveira
 *
 */
public class PlaceHolder {
	
	public String replace(Map<String, String> map , String mapperPath) {
		
		String mock = FileUtil.read( mapperPath );
		String[] wildCards = loadWildCard( mock );
		
		for(String wildCard : wildCards) {
			
			if(wildCard == null) {
				continue;
			}
			
			String wildCardReplace = map.get(wildCard);
			mock = mock.replaceAll(wildCard, wildCardReplace);
		}
		
		return mock;
	}

	public static void main(String[] args) {
		
		long start , end;
		start = System.currentTimeMillis();
		
		PlaceHolder placeHolder = new PlaceHolder();

		Map<String, String> map = new HashMap<>();
		map.put("@@Entidade@@", "RedeVenda");
		map.put("@@PK@@", "Long");
		map.put("@@Objeto@@", "redeVenda");
		map.put("@@Field@@", "");
		map.put("@@GetterAndSetter@@", "");
		
		String fileContent = placeHolder.replace(map, "resources/VoMapper.txt");
		System.out.println( fileContent );
		FileUtil.write("RedeVenda.java", fileContent, false);
		
		/*
		
		System.out.println("\n DAO \n");
		String fileContent = placeHolder.replace(map, "./DaoMapper.txt");
		System.out.println( fileContent );
		FileUtil.write("RedeVendaDAO.java", fileContent, false);
		
		System.out.println("\n Facade \n");
		
		fileContent = placeHolder.replace(map, "./FacadeMapper.txt");
		System.out.println( fileContent );
		FileUtil.write("RedeVendaFacade.java", fileContent, false);
		
		System.out.println("\n Action \n");
		
		fileContent = placeHolder.replace(map, "./ActionMapper.txt");
		System.out.println( fileContent );
		FileUtil.write("RedeVendaAction.java", fileContent, false);
		
		System.out.println("\n Application Context \n");
		
		fileContent = placeHolder.replace(map, "./ApplicationContextMapper.txt");
		System.out.println( fileContent );
		FileUtil.write("ApplicationContextMapper.txt", fileContent, false);

		*/
		end = System.currentTimeMillis();
		
		System.out.println("Tempo utilizado " + (end - start) + "(ms)");
		
	}

	private static String[] loadWildCard(String fileContent) {
		char[] array = fileContent.toCharArray();
		
		String [] compute = new String[array.length / 4];
		
		String aux = "";
		boolean continuaCapturandoTexto = false;
		int contaCoringa = 0;
		
		int index = 0; 
		for(char c : array) {
			if( c == '@') {
				continuaCapturandoTexto = true;
				contaCoringa++;
			}
			
			if(continuaCapturandoTexto) {
				aux += c;
			}
			
			if(aux.length() == 2 && !aux.equals("@@")) {
				continuaCapturandoTexto = false;
				contaCoringa = 0;
				aux = "";
			}
			
			if(continuaCapturandoTexto && contaCoringa == 4) {
				continuaCapturandoTexto = false;
				compute[index] = aux;
				aux = "";
				contaCoringa = 0;
				index++;
			}
			
		}
		
		return compute;
	}

}
