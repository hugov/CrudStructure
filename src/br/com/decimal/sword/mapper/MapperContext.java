/**
 * 
 */
package br.com.decimal.sword.mapper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Vitor Hugo Oliveira
 *
 */
public class MapperContext {

	private static Map<String, String> repository = new HashMap<String, String>();

	public static void loadMap() {
		XStream stream = new XStream(new DomDriver());
		stream.alias("Mappers", List.class);
		stream.alias("Mapper", Mapper.class);

		List<Mapper> listMapper = (List<Mapper>) stream.fromXML(new File(
				"./ClassToJdbcType.xml"));

		for (Mapper mapper : listMapper) {
			repository.put(mapper.getFrom(), mapper.getTo());
		}

	}

}
