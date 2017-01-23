/**
 * 
 */
package br.com.souza.ejb.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import br.com.souza.anotacao.DAO;
import br.com.souza.dao.DaoFactory;
import br.com.souza.dao.impl.BasicDaoImpl;
import br.com.souza.ejb.BaseService;
import br.com.souza.logger.BaseLogger;

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
		DaoFactory fabrica = null;
		//Obtem todos os atributos da classe
		Field[] atributos = this.getClass().getDeclaredFields();
		for (Field atributo : atributos) {
			atributo.setAccessible(Boolean.TRUE);
			//Obtem anotacao DAO
			DAO anotacao = atributo.getAnnotation(DAO.class);
			//Possui anotacao DAO
			if(anotacao != null){
				try {
					//cria a fabrica com o metodo que controi o dao
					fabrica = construirFabricaDao(anotacao, fabrica);
					// inseri o dao no campo
					inserirDao(atributo, fabrica);
					// informar o entitymanager do EJB
					setEntityManager(atributo, anotacao);
				} catch (IllegalAccessException | NoSuchMethodException | SecurityException
						| IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
					getLogger().erro("Erro ao injetar dependencia no EJB", e);
				}
			}
		}
	}

	/**
	 * Metodo para construir a fabrica de dao's informada
	 * @param anotacao
	 * @param fabrica
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private DaoFactory construirFabricaDao(DAO anotacao, DaoFactory fabrica) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method metodoSingleton = anotacao.fabrica().getMethod("getInstance");
		return (DaoFactory) metodoSingleton.invoke(fabrica);
	}

	/**
	 * inclui o entity manager no dao para execução dos metodos de acesso ao banco
	 * @param field
	 * @param anotacaoDAO 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private void setEntityManager(Field field, DAO anotacaoDAO) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		String nomeCampoEM = anotacaoDAO.entityManager();
		if(nomeCampoEM != null && !nomeCampoEM.trim().equals("")){
			Field atributoEM = this.getClass().getDeclaredField(nomeCampoEM);
			atributoEM.setAccessible(Boolean.TRUE);
			((BasicDaoImpl<?>) field.get(this)).setEm((EntityManager) atributoEM.get(this));
		} else {
			getLogger().erro("Sem entitymanager declarado no EJB para associar aos dao's.");
		}
	}

	/**
	 * injetar objeto dao no atributo, atraves do metodo da fabrica informada
	 * @param field
	 * @param metodoCriacao
	 * @param fabrica
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	private void inserirDao(Field field, DaoFactory fabrica) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String nomeMetodoCriacao = "criar"+field.getType().getSimpleName();
		Method metodoCriacao = fabrica.getClass().getMethod(nomeMetodoCriacao);
		metodoCriacao.setAccessible(Boolean.TRUE);
		field.set(this, metodoCriacao.invoke(fabrica));
	}

	/**
	 * metodo retorna objeto de log
	 * @return the logger
	 */
	protected BaseLogger getLogger() {
		return logger;
	}
}
