/**
 * 
 */
package br.com.decimal.sword.mapper.vo;

import java.lang.reflect.Field;

/**
 * @author Vitor Hugo Oliveira
 * 
 */
public class Element {

	private Integer id;
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static void main(String[] args) {

		Class<Element> target = Element.class;

		for (Field f : target.getDeclaredFields()) {

			System.out.println(f.getName());
			System.out.println(f.getType().getName());
		}

	}

}
