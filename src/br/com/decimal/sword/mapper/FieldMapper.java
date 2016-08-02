/**
 * 
 */
package br.com.decimal.sword.mapper;


/**
 * @author Vitor Hugo Oliveira
 *
 */
public class FieldMapper {
	
	public static String[] read(Class<?> target) {
		
		String [] result = new String[target.getDeclaredFields().length];
		
		for(int index = 0; index < target.getDeclaredFields().length; index++) {
			result[index] = target.getDeclaredFields()[index].getName();
		}
		
		return result;
		
	}
	
	public static Field [] readField(Class<?> target) {
		
		Field [] result = new Field[target.getDeclaredFields().length];
		
		for(int index = 0; index < target.getDeclaredFields().length; index++) {
			Field field = new Field();
			field.setFieldName(target.getDeclaredFields()[index].getName());
			field.setFieldType(target.getDeclaredFields()[index].getType().getName());
			
			result[index] = field;
		}
		
		return result;
		
	}
	
}
