/**
 * 
 */
package br.com.souza.ejb;

import java.util.List;

import br.com.souza.entidades.Autor;
import br.com.souza.exception.WebProjectNegocialException;

/**
 * Interface AutorService
 * @author vinicius
 *
 */
public interface AutorService extends BaseService {
	
	/**
	 * Metodo que retorna todos os autores cadastrados
	 * @return
	 * @throws WebProjectNegocialException
	 */
	List<Autor> obterAutoresCadastrados() throws WebProjectNegocialException;
}
