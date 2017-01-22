/**
 * 
 */
package br.com.souza.logger;

import org.jboss.logging.Logger;

/**
 * implementação da classe logger do sistema
 * @author vinicius
 *
 */
public final class BaseLogger{
	
	Logger log;
	
	/**
	 * Contrutor base
	 * @param name
	 * @param resourceBundleName
	 */
	public BaseLogger(Class<?> classe) {
		log = Logger.getLogger(classe);
	}
	
	/**
	 * Imprime info no servidor
	 * @param mensagem
	 */
	public void info(String mensagem){
		log.info(mensagem);
	}
	
	/**
	 * Imprime debug no servidor
	 * @param mensagem
	 */
	public void debug(String mensagem){
		log.debug(mensagem);
	}
	
	/**
	 * Imprime erro no servidor
	 * @param mensagem
	 */
	public void erro(String mensagem){
		log.error(mensagem);
	}
	
	/**
	 * Imprime erro no servidor
	 * @param mensagem
	 * @param err
	 */
	public void erro(String mensagem, Throwable err){
		log.error(mensagem, err);
	}
	
}
