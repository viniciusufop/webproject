/**
 * 
 */
package br.com.souza.bean;

import java.io.Serializable;

import br.com.souza.logger.BaseLogger;

/**
 * Classe base do BEAN
 * deve ser pai dos outros bean's
 * @author vinicius
 *
 */
/**
 * @author vinicius
 *
 */
public abstract class BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4453890303475405837L;
	
	private BaseLogger logger;
	
	/**
	 * construtor
	 */
	public BaseBean() {
		super();
		this.logger = new BaseLogger(this.getClass());
	}

	/**
	 * Retornar object logger gravar informação no servidor
	 * @return the logger
	 */
	protected BaseLogger getLogger() {
		return logger;
	}
}
