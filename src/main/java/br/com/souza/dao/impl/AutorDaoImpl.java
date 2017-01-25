/**
 * 
 */
package br.com.souza.dao.impl;

import br.com.souza.dao.AutorDao;
import br.com.souza.entidades.Autor;

/**
 * @author vinicius
 *
 */
public class AutorDaoImpl extends BasicDaoImpl<Autor> implements AutorDao {

	/**
	 * @param classeEntity
	 */
	public AutorDaoImpl() {
		super(Autor.class);
	}
}
