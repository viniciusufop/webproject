/**
 * 
 */
package br.com.souza.ejb.service;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.souza.anotacao.Dao;
import br.com.souza.dao.AutorDao;
import br.com.souza.dao.DaoFactory;
import br.com.souza.ejb.AutorService;
import br.com.souza.entidades.Autor;
import br.com.souza.exception.WebProjectNegocialException;

/**
 * Ejb de servicos do Autor
 * @author vinicius
 *
 */
@Remote(AutorService.class)
@Stateless
public class AutorServiceEJB extends BaseServiceEJB implements AutorService{
	
	@PersistenceContext(unitName = "webproject")
	private EntityManager em;
	
	@Dao(fabrica = DaoFactory.class, entityManager = "em")
	private AutorDao dao;
	
	/**
	 * 
	 */
	public AutorServiceEJB() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see br.com.souza.ejb.AutorService#obterAutoresCadastrados()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Autor> obterAutoresCadastrados() throws WebProjectNegocialException{
		return dao.listaEntity();
	}

	/* (non-Javadoc)
	 * @see br.com.souza.ejb.AutorService#cadastrarAutor(br.com.souza.entidades.Autor)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void cadastrarAutor(Autor autor) throws WebProjectNegocialException {
		dao.salvar(autor);
		
	}

}
