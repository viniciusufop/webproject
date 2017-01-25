/**
 * 
 */
package br.com.souza.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.souza.ejb.AutorService;
import br.com.souza.entidades.Autor;
import br.com.souza.exception.WebProjectNegocialException;

/**
 * Classe Bean para coorderar solicitações para autor
 * @author vinicius
 *
 */
@ManagedBean(name = "autorbean")
@SessionScoped
public class AutorBean extends BaseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3375255248122523505L;
	
	@EJB
	private AutorService ejb;
	
	private List<Autor> listaAutores;
	
	/**
	 * consrutor
	 */
	public AutorBean() {
		super();
	}

	/**
	 * @return the listaAutores
	 */
	public List<Autor> getListaAutores() {
		try {
			setListaAutores(ejb.obterAutoresCadastrados());
		} catch (WebProjectNegocialException e) {
			getLogger().erro("Erro ao obter os autores cadastrados", e);
		}
		return listaAutores;
	}

	/**
	 * @param listaAutores the listaAutores to set
	 */
	public void setListaAutores(List<Autor> listaAutores) {
		this.listaAutores = listaAutores;
	}
}
