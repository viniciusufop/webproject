/**
 * 
 */
package br.com.souza.ejb.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import br.com.souza.anotacao.DAO;
import br.com.souza.anotacao.EntityManager;
import br.com.souza.dao.DaoFactory;
import br.com.souza.dao.impl.BasicDaoImpl;
import br.com.souza.logger.BaseLogger;

/**
 * Implementação base do servico EJB para ser herdada.
 * @author vinicius
 *
 */
public abstract class BasicServiceEJB {
	
	/**
	 * objecto logger para camada dao 
	 */
	private BaseLogger logger;
	
	/**
	 * construtor padrão
	 */
	public BasicServiceEJB() {
		super();
		this.logger = new BaseLogger(this.getClass());
	}
	
	/**
	 * injeta as dependencias no service,
	 * trata as anotações dao e entitymanager
	 */
	@PostConstruct
	private void injetarDependencia(){
		//obtem todas as anotacoes da classe
		DAO anotacao =  this.getClass().getAnnotation(DAO.class);
		if(anotacao != null){
			DaoFactory fabrica;
			
			//obtem atributos associados a anotacao DAO
			Field fields[] = anotacao.annotationType().getDeclaredFields();
			for (Field field : fields) {
				DAO anotacaoDAO = field.getAnnotation(DAO.class);
				try {
					//cria a fabrica com o metodo que controi o dao
					fabrica = anotacaoDAO.fabrica().newInstance();
					//identifica metodo de criacao do dao
					Method metodoCriacao = retornaMetodoCriacaoDAO(field, fabrica);	
					//inseri o dao no campo
					inserirDao(field, metodoCriacao, fabrica);
					//informar o entitymanager do EJB
					setEntityManager(field);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} 
			}	
		} else {
			getLogger().info("Não possui dao's para injetar");
		}
	}

	/**
	 * inclui o entity manager no dao para execução dos metodos de acesso ao banco
	 * @param field
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private void setEntityManager(Field field) throws IllegalArgumentException, IllegalAccessException {
		EntityManager anotacaoEM = this.getClass().getAnnotation(EntityManager.class);
		Field[] fieldsEM = anotacaoEM.getClass().getFields();
		
		if(fieldsEM != null && fieldsEM.length > 0){
			((BasicDaoImpl<?>) field.get(this)).setEm((javax.persistence.EntityManager)fieldsEM[0].get(this));
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
	 */
	private void inserirDao(Field field, Method metodoCriacao, DaoFactory fabrica) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		metodoCriacao.setAccessible(Boolean.TRUE);
		field.setAccessible(Boolean.TRUE);
		field.set(this, metodoCriacao.invoke(fabrica));
	}

	/**
	 * retorna o metodo que vai criar o dao
	 * @param field
	 * @param fabrica
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private Method retornaMetodoCriacaoDAO(Field field, DaoFactory fabrica) throws NoSuchMethodException, SecurityException {
		String nomeMetodoCriacao = "criar"+field.getType().getSimpleName();
		return fabrica.getClass().getMethod(nomeMetodoCriacao);
	}

	/**
	 * metodo retorna objeto de log
	 * @return the logger
	 */
	protected BaseLogger getLogger() {
		return logger;
	}
}
