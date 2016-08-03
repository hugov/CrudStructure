/**
 * 
 */
package br.com.decimal.sword.mapper;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import br.com.decimal.sword.entity.Field;
import br.com.decimal.sword.entity.Mapper;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Vitor Hugo Oliveira
 * 
 */
public class EntityToMapper {
	
	public static void main(String[] args) {

		EntityToMapper leitor = new EntityToMapper();
		System.out.println(leitor.getMapper());

	}
	
	public String getMapper() {
		
		StringBuilder buffer = new StringBuilder();
		
		List<Mapper> listMapper = carrega();
		
		HashMap<String, String> map = new HashMap<>();
		
		for(Mapper mapper : listMapper) {
			map.put(mapper.getFrom(), mapper.getTo());
		}
		
		buffer.append(createHeader("RedeVenda"));
		buffer.append("\n");
		buffer.append("\n");
		
		buffer.append(createResultMapStart("redeVendaResult" , "br.com.digicon.sbe.vo.RedeVenda"));
		
		Field[] fields = FieldMapper.readField( br.com.digicon.vo.RedeVenda.class );
		for(Field f : fields) {
			if(f == null) {
				continue;
			}
			
			buffer.append(createResultLine("", f.getFieldName(), map.get(f.getFieldType())));
		}

		buffer.append("\n");
		buffer.append(createResultMapEnd());
		
		buffer.append("\n");
		buffer.append("\n");
		String content = SQLBuilder.createSelect(fields, true);
		buffer.append(createSelect("selectById", "redeVendaResult", "java.lang.Longg", content));
		
		buffer.append("\n");
		buffer.append("\n");
		content = SQLBuilder.createSelect(fields, false);
		buffer.append(createSelect("selectAll", "redeVendaResult", "java.lang.Longg", content));
		
		buffer.append("\n");
		buffer.append("\n");
		content = SQLBuilder.createDelete(fields);
		buffer.append(createDelete("java.lang.Longg", content));
		
		buffer.append("\n");
		buffer.append("\n");
		content = SQLBuilder.createInsert(fields);
		buffer.append(createInsert("java.lang.Longg", content));
		
		buffer.append("\n");
		buffer.append("\n");
		content = SQLBuilder.createUpdate(fields);
		buffer.append(createUpdate("java.lang.Longg", content));
		
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(createFooter());
		
		return buffer.toString();
		
	}
	
	private String uncapitalized( String value ) {
		String first = "" + value.charAt(0);
		String capitalize = first.toLowerCase();
		
		String aux = value.substring(1, value.length());
		aux = capitalize + aux;
		
		return aux;
	}
	
	public String getMapperIbatis(Field [] fields) {
		
		StringBuilder buffer = new StringBuilder();
		
		List<Mapper> listMapper = carrega();
		
		HashMap<String, String> map = new HashMap<>();
		
		for(Mapper mapper : listMapper) {
			map.put(mapper.getFrom(), mapper.getTo());
		}
		
		String entidade = fields[0].getClassName();
		String objeto = uncapitalized(entidade);
		String resultMap = objeto + "Result";
		String classTypePK = "";
		
		buffer.append(createHeader( entidade ));
		buffer.append("\n");
		buffer.append("\n");
		
		buffer.append(createResultMapStart( resultMap , "br.com.digicon.sbe.vo." + entidade));
		
		for(Field f : fields) {
			if(f == null) {
				continue;
			}
			
			if(f.isPK()) {
				classTypePK = f.getFieldType();
			}
			
			buffer.append(createResultLine(f.getColumnName(), f.getFieldName(), map.get(f.getFieldType())));
		}

		buffer.append("\n");
		buffer.append(createResultMapEnd());
		
		buffer.append("\n");
		buffer.append("\n");
		String content = SQLBuilder.createSelect(fields, true);
		buffer.append(createSelect("selectById", resultMap, classTypePK, content));
		
		buffer.append("\n");
		buffer.append("\n");
		content = SQLBuilder.createSelect(fields, false);
		buffer.append(createSelect("selectAll", resultMap, classTypePK, content));
		
		buffer.append("\n");
		buffer.append("\n");
		content = SQLBuilder.createDelete(fields);
		buffer.append(createDelete(classTypePK, content));
		
		buffer.append("\n");
		buffer.append("\n");
		content = SQLBuilder.createInsert(fields);
		buffer.append(createInsert(entidade, content));
		
		buffer.append("\n");
		buffer.append("\n");
		content = SQLBuilder.createUpdate(fields);
		buffer.append(createUpdate(entidade, content));
		
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(createFooter());
		
		return buffer.toString();
		
	}
	
	private String createHeader(String nameSpace) {
		return "\n\n<sqlMap namespace=\"" + nameSpace + "\" >";
	}
	
	private String createResultLine(String columnName, String propertyName, String propertyType) {
		if(propertyName.equals("serialVersionUID"))
			return "";
		
		return "\n\t<result column=\"" + columnName +  "\" property=\"" + propertyName + "\" jdbcType=\"" + propertyType + "\" />";
	}
	
	private String createResultMapStart(String resultName, String className) {
		return "<resultMap id=\"" + resultName + "\" class=\"" + className + "\" >";
	}
	
	private String createResultMapEnd() {
		return "</resultMap>";
	}
	
	private String createFooter() {
		return "</sqlMap>";
	}
	
	private String createSelect(String id, String result, String parameterClass, String content) {
		String select = "\t<select id=\"" + id + "\" resultMap=\"" + result + "\" parameterClass=\"" + parameterClass + "\">";
		
		select += "\n\t\t" + content;
		select += "\n\t</select>";
		
		return select;
	}
	
	private String createDelete(String parameterClass, String content) {
		String delete = "\t<delete id=\"deleteById\" parameterClass=\"" + parameterClass + "\">";
		
		delete += "\n\t\t" + content;
		delete += "\n\t</delete>";
		
		return delete;
	}
	
	private String createInsert(String parameterClass, String content) {
		String delete = "\t<insert id=\"insert\" parameterClass=\"" + parameterClass + "\">";
		
		delete += "\n\t\t" + content;
		delete += "\n\t</insert>";
		
		return delete;
	}

	private String createUpdate(String parameterClass, String content) {
		String delete = "\t<update id=\"update\" parameterClass=\"" + parameterClass + "\">";
		
		delete += "\n\t\t" + content;
		delete += "\n\t</update>";
		
		return delete;
	}

	private List<Mapper> carrega() {
		XStream stream = new XStream(new DomDriver());
		stream.alias("Mappers", List.class);
		stream.alias("Mapper", Mapper.class);
		
		return (List<Mapper>) stream.fromXML(new File("resources/ClassToJdbcType.xml"));
	}

}
