/**
 * 
 */
package br.com.souza.dao;

import java.io.Serializable;
import java.util.List;

import br.com.souza.entidades.BaseEntity;
import br.com.souza.exception.WebProjectNegocialException;

/**
 * Interface inicial do dao
 * @author vinicius
 *
 */
public interface BasicDao<T extends BaseEntity> {
	
	/**
	 * @param entity
	 * @throws WebProjectNegocialException
	 */
	void salvar(T entity) throws WebProjectNegocialException;
	
	void atualizar(T entity) throws WebProjectNegocialException;
	
	void deletar(T entity) throws WebProjectNegocialException;
	
	void deletar(Serializable chave) throws WebProjectNegocialException;
	
	T obter(Serializable chave) throws WebProjectNegocialException;
	
	List<T> listaEntity() throws WebProjectNegocialException;
}
