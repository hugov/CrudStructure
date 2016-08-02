/**
 * 
 */
package br.com.decimal.sword.mapper;

/**
 * @author Vitor Hugo Oliveira
 * 
 */
public class Mapper {

	private String from;
	private String to;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "Mapper [from=" + from + ", to=" + to + "]";
	}

}
