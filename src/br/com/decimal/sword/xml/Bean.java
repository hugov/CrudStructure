/**
 * 
 */
package br.com.decimal.sword.xml;

/**
 * @author Vitor Hugo Oliveira
 * 
 */
public class Bean {

	private String id;
	private String clazz;
	private String name;
	private String local;

	public Bean(String id, String clazz, String name, String local) {
		super();
		this.id = id;
		this.clazz = clazz;
		this.name = name;
		this.local = local;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

}
