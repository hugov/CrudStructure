/**
 * 
 */
package br.com.decimal.sword.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.decimal.sword.entity.Field;
import br.com.decimal.sword.mapper.DMLTranslator;
import br.com.decimal.sword.mapper.EntityToMapper;
import br.com.decimal.sword.mapper.FileUtil;
import br.com.decimal.sword.mapper.PlaceHolder;

/**
 * @author Vitor Hugo Oliveira
 *
 */
public class SwordService {
	
	private DMLTranslator dmlTranslator;
	private PlaceHolder placeHolder;
	private EntityToMapper entityToMapper;
	
	public SwordService() {
		this.dmlTranslator = new DMLTranslator();
		this.placeHolder = new PlaceHolder();
		this.entityToMapper = new EntityToMapper();
	}
	
	private String uncapitalized( String value ) {
		String first = "" + value.charAt(0);
		String capitalize = first.toLowerCase();
		
		String aux = value.substring(1, value.length());
		aux = capitalize + aux;
		
		return aux;
	}
	
	private String capitalized( String value ) {
		String first = "" + value.charAt(0);
		String capitalize = first.toUpperCase();
		
		String aux = value.substring(1, value.length());
		aux = capitalize + aux;
		
		return aux;
	}
	
	public String [] listTableName() {
		List<String> tables = dmlTranslator.getAllTables();
		String [] resultTable = new String[ tables.size() ];

		for(int index = 0; index < tables.size(); index++) {
			resultTable[ index ] = tables.get( index );
		}
		
		return resultTable;
	}
	
	public String [] listColumnName(String tableName) {
		return dmlTranslator.recoveryColumn(tableName);
	}
	
	public void create( Field [] fields, String mapperPathResult ) {
		createVo( fields, mapperPathResult );
		createMapperIbatis( fields, mapperPathResult );
		createMapperDAO( fields, mapperPathResult );
		createMapperFacade( fields, mapperPathResult );
		createMapperAction( fields, mapperPathResult );
	}
	
	public static void main(String[] args) {
		
		String e = "EntidadeRelacional";
		
		String first = "" + e.charAt(0);
		
		String second = first.toLowerCase();
		
		String aux = e.substring(1, e.length());
		aux = second + aux;
		
	}
	
	private String removeLang(String value) {
		return value.replaceAll("java.lang.", "");
	}
	
	private Map<String, String> createMap( Field [] fields ) {
		Map<String, String> map = new HashMap<>();
		
		String entidade = fields[0].getClassName();
		String objeto = uncapitalized(entidade);
		
		map.put("@@Entidade@@", entidade);
		map.put("@@Objeto@@", objeto);
		
		for(Field field : fields) {
			if(field.isPK()) {
				map.put("@@PK@@", removeLang(field.getFieldType()));
			}
		}
		
		return map;
	}
	
	private void createVo( Field [] fields , String mapperPathResult ) {
		
		Map<String, String> map = createMap( fields );
		
		StringBuilder bufferField = new StringBuilder();
		StringBuilder bufferGetterAndSetter = new StringBuilder();
		for(Field field : fields) {
			
			bufferField.append("\t");
			bufferField.append("private ");
			bufferField.append( removeLang( field.getFieldType() ) );
			bufferField.append( " " +field.getFieldName() );
			bufferField.append(";");
			
			map.put("@@Field@@", bufferField.toString());
			
			bufferGetterAndSetter.append("\n\tpublic " + removeLang(field.getFieldType()) + " get" + capitalized(field.getFieldName()) + "() {");
			bufferGetterAndSetter.append("\n\t\treturn " + uncapitalized(field.getFieldName()) + ";");
			bufferGetterAndSetter.append("\n\t}");

			bufferGetterAndSetter.append("\n\n\tpublic void set" + capitalized(field.getFieldName()) + "(" + removeLang(field.getFieldType()) + " " + uncapitalized(field.getFieldName()) + ") {");
			bufferGetterAndSetter.append("\n\t\tthis." + uncapitalized(field.getFieldName()) + " = " + uncapitalized(field.getFieldName()) + ";");
			bufferGetterAndSetter.append("\n\t}");
		
			map.put("@@GetterAndSetter@@", bufferGetterAndSetter.toString());
		}
		
		String fileContent = placeHolder.replace(map, "resources/VoMapper.txt");
		FileUtil.write(fields[0].getClassName() + ".java", fileContent, false);
		
	}
	
	private void createMapperIbatis( Field [] fields , String mapperPathResult ) {
		String header = FileUtil.read("resources/IBatisHeader.txt");
		String fileContent = entityToMapper.getMapperIbatis(fields);
		FileUtil.write(fields[0].getClassName() + "Ibatis.xml", header, false);
		FileUtil.write(fields[0].getClassName() + "Ibatis.xml", fileContent, true);
	}
	
	private void createMapperDAO( Field [] fields, String mapperPathResult ) {
		
		Map<String, String> map = createMap( fields );
		
		String fileContent = placeHolder.replace(map, "resources/DaoMapper.txt");
		FileUtil.write(fields[0].getClassName() + "DAO.java", fileContent, false);
		
	}
	
	private void createMapperFacade( Field [] fields, String mapperPathResult ) {
		
		Map<String, String> map = createMap( fields );
		
		String fileContent = placeHolder.replace(map, "resources/FacadeMapper.txt");
		FileUtil.write(fields[0].getClassName() + "Facade.java", fileContent, false);
		
	}

	private void createMapperAction( Field [] fields, String mapperPathResult ) {
	
		Map<String, String> map = createMap( fields );

		String fileContent = placeHolder.replace(map, "resources/ActionMapper.txt");
		FileUtil.write(fields[0].getClassName() + "Action.java", fileContent, false);
	
	}
	

}
