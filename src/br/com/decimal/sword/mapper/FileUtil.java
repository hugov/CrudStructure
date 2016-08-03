/**
 * 
 */
package br.com.decimal.sword.mapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * @author Vitor Hugo Oliveira
 *
 */
public class FileUtil {
	
	private static final Logger LOGGER = Logger.getLogger(MySQL.class);
	private static final String TEMP = "C:\\TEMP\\";
	
	private FileUtil() { }
	
	public static void write( String filename, String fileContent, boolean append ) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter( new File( TEMP + filename ) , append ));
			writer.append( fileContent );
		} catch (IOException e) {
			LOGGER.error("Falha ao escrever o arquivo", e);
		} finally {
			try {
				if(writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				LOGGER.error("Falha ao fechar o stream", e);
			}
		}
		
	}
	
	public static String read(String path) {
		
		String file = "";
		String aux = "";
		
		BufferedReader reader = null;

		try {
			reader = new BufferedReader( new FileReader(new File( path ) ) );
			
			while((aux = reader.readLine()) != null) {
				file += "\n";
				file += aux;
			}
			
		} catch (FileNotFoundException e) {
			LOGGER.error("Arquivo não encontrado", e);
		} catch (IOException e) {
			LOGGER.error("Falha ao fechar o stream", e);
		} finally {
			try {
				if(reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				LOGGER.error("Falha ao fechar o stream", e);
			}
		}
		
		return file;
	}

}
