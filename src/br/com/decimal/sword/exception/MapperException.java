/**
 * 
 */
package br.com.decimal.sword.exception;

public class MapperException extends Exception {
	
	private static final long serialVersionUID = 127300530837215371L;

	public MapperException() {
		super();
	}

	public MapperException(String mensagem) {
		super(mensagem);
	}

	public MapperException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

	public MapperException(Throwable causa) {
		super(causa);
	}

}
