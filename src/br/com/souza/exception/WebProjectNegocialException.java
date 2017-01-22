/**
 * 
 */
package br.com.souza.exception;

import javax.ejb.ApplicationException;

/**
 * Excecao negocial base do sistema
 * @author vinicius
 *
 */
@ApplicationException(rollback = true)
public class WebProjectNegocialException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3820964719730003622L;

	/**
	 * 
	 */
	public WebProjectNegocialException() {
		super();
	}

	/**
	 * @param message
	 */
	public WebProjectNegocialException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public WebProjectNegocialException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public WebProjectNegocialException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public WebProjectNegocialException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
