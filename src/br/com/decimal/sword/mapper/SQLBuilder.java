/**
 * 
 */
package br.com.decimal.sword.mapper;

import org.apache.log4j.Logger;

import br.com.decimal.sword.entity.Field;


/**
 * @author Vitor Hugo Oliveira
 *
 */
public class SQLBuilder {
	
	private static final Logger LOGGER = Logger.getLogger(SQLBuilder.class);
	
	public static String createSelect(Field [] fields, boolean hasFilter) {
		StringBuilder buffer = new StringBuilder( "SELECT " );
		
		for(int index = 0 ; index < fields.length; index++) {
			buffer.append(fields[index].getColumnName());
			
			if(( index + 1) < fields.length) {
				buffer.append(", ");
			}
		}
		
		buffer.append(" FROM ");
		
		buffer.append(fields[0].getTableName());
		
		for(int index = 0 ; index < fields.length; index++) {
			
			if(hasFilter && fields[ index ].isPK()) {
				buffer.append(" WHERE ");
				buffer.append( fields[ index ].getColumnName() );
				buffer.append( " = #" + fields[ index ].getFieldName() + "# ");
			}
			
			if(fields[ index ].isPK()) {
				buffer.append(" ORDER BY ");
				buffer.append( fields[ index ].getColumnName() );
				break;
			}
		}
		
		return buffer.toString();
	}
	
	public static String createDelete(Field [] fields) {
		StringBuilder buffer = new StringBuilder( "DELETE FROM " );
		buffer.append(fields[0].getTableName());
		
		for(int index = 0 ; index < fields.length; index++) {
			if(fields[ index ].isPK()) {
				buffer.append(" WHERE ");
				buffer.append( fields[ index ].getColumnName() );
				buffer.append( " = #" + fields[ index ].getFieldName() + "#");
				break;
			}
			
			//FIXME: Falta considerar que pode existir mais de uma coluna como PK
		}
		
		return buffer.toString();
	}
	
	public static String createInsert(Field [] fields) {
		StringBuilder buffer = new StringBuilder( "INSERT INTO " );
		buffer.append(fields[0].getTableName());
		
		StringBuilder coluna = new StringBuilder();
		StringBuilder atributo = new StringBuilder();
		for(int index = 0 ; index < fields.length; index++) {
			coluna.append(fields[index].getColumnName());
			atributo.append("#" + fields[index].getFieldName() + "#");
			
			if(( index + 1) < fields.length) {
				coluna.append(", ");
				atributo.append(", ");
			}
		}
		
		buffer.append("(");
		buffer.append(coluna);
		buffer.append(")");
		
		buffer.append(" VALUES ");

		buffer.append("(");
		buffer.append(atributo);
		buffer.append(")");
		
		return buffer.toString();
		
	}
	
	public static String createUpdate(Field [] fields) {
		StringBuilder buffer = new StringBuilder( "UPDATE " );
		buffer.append(fields[0].getTableName());
		buffer.append(" SET ");
		
		String aux = "";
		
		for(int index = 0 ; index < fields.length; index++) {
			
			if(!fields[index].isPK()) {
				buffer.append(fields[index].getColumnName());
				buffer.append(" = #" + fields[index].getFieldName() + "#");
				
				if(( index + 1) < fields.length) {
					buffer.append(", ");
				}
				
			}
			
			if(fields[index].isPK()) {
				aux += " WHERE ";
				aux += fields[ index ].getColumnName();
				aux += " = #" + fields[ index ].getFieldName() + "#";
			}
			
		}
		
		buffer.append(aux);
		
		return buffer.toString();
		
	}
	
	public static void main(String[] args) {
		
		Field [] fields = new Field[3];
		
		Field f1 = new Field();
		f1.setTableName("TB_SVC_PRODUTOREDEVENDA");
		f1.setColumnName("NI_ID");
		f1.setPK(true);
		fields[0] = f1;
		
		Field f2 = new Field();
		f2.setTableName("TB_SVC_PRODUTOREDEVENDA");
		f2.setColumnName("VC_NOME");
		fields[1] = f2;
		
		Field f3 = new Field();
		f3.setTableName("TB_SVC_PRODUTOREDEVENDA");
		f3.setColumnName("DT_VIGENCIA");
		fields[2] = f3;
		
		
		LOGGER.info( createSelect( fields , false ) );
		
		LOGGER.info("\n");
		LOGGER.info( createSelect( fields , true ) );
		
		LOGGER.info("\n");
		LOGGER.info( createDelete( fields ) );
		
		LOGGER.info("\n");
		LOGGER.info( createInsert( fields ) );
		
		LOGGER.info("\n");
		LOGGER.info( createUpdate( fields ) );
		
	}

}
