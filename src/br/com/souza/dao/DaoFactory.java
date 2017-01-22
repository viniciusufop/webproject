/**
 * 
 */
package br.com.souza.dao;

import br.com.souza.dao.impl.BasicDaoImpl;
import br.com.souza.entidades.BaseEntity;

/**
 * Fabrica de criação de dao's para injeção de dependencia
 * @author vinicius
 *
 */
public class DaoFactory {
	
	private static DaoFactory singleton;
	
	/**
	 * construtor 
	 */
	private DaoFactory() {
		super();
	}
	
	/**
	 * metodo static getInstace para retornar sempre a mesma instancia (Base do padrão Singleton)
	 * @return
	 */
	public static DaoFactory getInstance(){
		if(singleton == null){
			synchronized(DaoFactory.class){
				if(singleton == null){
					singleton = new DaoFactory();
				}
			}
		}
		return singleton;
	}
	
	/**
	 * metodo que cria dao basica, todos os outros metodos de criacao deve seguir esse
	 * padrão, com o sulfixo criar+<nome_da_interface_dao> 
	 * 
	 * @return BasicDao
	 */
	public BasicDao<BaseEntity> criarBasicDao(){
		return new BasicDaoImpl<BaseEntity>(BaseEntity.class);
	}
}
