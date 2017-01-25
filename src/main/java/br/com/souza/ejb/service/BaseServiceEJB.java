/**
 * 
 */
package br.com.souza.ejb.service;

import javax.annotation.PostConstruct;

import br.com.souza.ejb.BaseService;
import br.com.souza.logger.BaseLogger;
import br.com.souza.util.BaseInjecaoDependencia;

/**
 * Implementação base do servico EJB para ser herdada.
 * @author vinicius
 *
 */
public abstract class BaseServiceEJB implements BaseService{
	
	/**
	 * objecto logger para camada dao 
	 */
	private BaseLogger logger;
	
	/**
	 * construtor padrão
	 */
	public BaseServiceEJB() {
		super();
		this.logger = new BaseLogger(this.getClass());
	}
	
	/**
	 * injeta as dependencias no service,
	 * trata as anotações dao e entitymanager
	 */
	@PostConstruct
	private void injetarDependencia(){
		BaseInjecaoDependencia.injecaoDependenciaDao(this);
	}

	/**
	 * metodo retorna objeto de log
	 * @return the logger
	 */
	protected BaseLogger getLogger() {
		return logger;
	}
}
