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

/**
 * @author Vitor Hugo Oliveira
 *
 */
public class FileUtil {
	
	private static final String TEMP = "C:\\TEMP\\";
	
	private FileUtil() { }
	
	public static void write( String filename, String fileContent, boolean append ) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter( new File( TEMP + filename ) , append ));
			writer.append( fileContent );
			//writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return file;
	}

}
