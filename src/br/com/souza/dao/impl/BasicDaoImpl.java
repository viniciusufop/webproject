/**
 * 
 */
package br.com.souza.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.souza.dao.BasicDao;
import br.com.souza.entidades.BaseEntity;
import br.com.souza.exception.WebProjectNegocialException;
import br.com.souza.logger.BaseLogger;

/**
 * Implentação dos metodos basicos do dao
 * 
 * @author vinicius
 * @param <T>
 */
public class BasicDaoImpl<T extends BaseEntity> implements BasicDao<T> {

	/**
	 * entity manager do dao
	 */
	private EntityManager em;

	/**
	 * tipo generico
	 */
	private Class<T> classe;
	
	/**
	 * objecto logger para camada dao 
	 */
	private BaseLogger logger;
	
	/**
	 * construtor base
	 * @param classeEntity
	 * @param em
	 */
	public BasicDaoImpl(Class<T> classeEntity) {
		super();
		this.classe = classeEntity;
		this.logger = new BaseLogger(this.getClass());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.souza.dao.BasicDao#salvar(br.com.souza.entidades.BaseEntity)
	 */
	@Override
	public void salvar(T entity) throws WebProjectNegocialException {
		em.persist(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.souza.dao.BasicDao#atualizar(br.com.souza.entidades.BaseEntity)
	 */
	@Override
	public void atualizar(T entity) throws WebProjectNegocialException {
		em.merge(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.souza.dao.BasicDao#obter(java.io.Serializable)
	 */
	@Override
	public T obter(Serializable chave) throws WebProjectNegocialException {
		return em.find(classe, chave);
	}

	/* (non-Javadoc)
	 * @see br.com.souza.dao.BasicDao#remover(br.com.souza.entidades.BaseEntity)
	 */
	@Override
	public void deletar(T entity) throws WebProjectNegocialException {
		em.remove(entity);
		
	}

	/* (non-Javadoc)
	 * @see br.com.souza.dao.BasicDao#remover(java.io.Serializable)
	 */
	@Override
	public void deletar(Serializable chave) throws WebProjectNegocialException {
		deletar(obter(chave));
	}
	
	/* (non-Javadoc)
	 * @see br.com.souza.dao.BasicDao#listaEntity()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> listaEntity() throws WebProjectNegocialException {
		Query query = em.createQuery("FROM "+classe.getSimpleName());
		return query.getResultList();
	}

	/**
	 * @param em the em to set
	 */
	public void setEm(EntityManager em) {
		this.em = em;
	}

	/**
	 * metodo retorna objeto de log
	 * @return the logger
	 */
	protected BaseLogger getLogger() {
		return logger;
	}
}
