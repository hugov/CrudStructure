/**
 * 
 */
package br.com.decimal.sword.mapper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DMLTranslator {
	
	private boolean isOracle = true;

	public static void main(String[] args) {


		try {

			DMLTranslator dml = new DMLTranslator();
			
			for( String table : dml.getAllTables() ) {
				System.out.println( table );
			}
			
			String [] columns = dml.recoveryColumn( "TB_SVC_ACERTO_CONTA" );
			
			for(String column : columns) {
				System.out.println(column);
			}
			
			System.out.println("Conectado com sucesso !!!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public List<String> getAllTables()  {
		List<String> allTables = new ArrayList<>();
		
        try {

        	Connection connection = createConnection();
            DatabaseMetaData dbmd = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbmd.getTables(null, null, "%", types);
            
            //
            
            while (rs.next()) {
            	
            	if( isOracle && ("SYS".equals(rs.getString(2)) || "SYSTEM".equals(rs.getString(2)) || "XDB".equals(rs.getString(2)) || "APEX_040000".equals(rs.getString(2))
            				     || "CTXSYS".equals(rs.getString(2)) || "MDSYS".equals(rs.getString(2)) || "OUTLN".equals(rs.getString(2))
            				     || rs.getString(2).contains("APEX$") || rs.getString("TABLE_NAME").contains("APEX$"))) {
            		continue;
            	}
            	
            	allTables.add( rs.getString("TABLE_NAME").toUpperCase() );
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Collections.sort( allTables );
        
        allTables.add(0, "Selecione ...");
        
        return allTables;
    }

	private Connection createConnection() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		
		String url = "jdbc:oracle:thin:@10.104.0.108:1521:SBETESTE";
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		
		//String url = "jdbc:mysql://localhost/tac?user=tac&password=tac";
		//Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		
		Connection connection = DriverManager.getConnection(url, "sbe4", "sbe4");
		return connection;
	}
	
	public String [] recoveryColumn(String tableName) {
		String [] columns = null;
		
		try {
			
			Connection connection = createConnection();
			
			Statement stmt = connection.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			columns = new String[ numberOfColumns ];
			
			for(int numeroColuna = 1 ; numeroColuna <= numberOfColumns; numeroColuna++) {
				columns[ numeroColuna - 1 ] = rsmd.getColumnName(numeroColuna);
			}
			stmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return columns;
	}
	
}
