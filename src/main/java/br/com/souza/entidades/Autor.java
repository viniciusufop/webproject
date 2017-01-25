/**
 * 
 */
package br.com.souza.entidades;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author vinicius
 *
 */
@Entity
@Table(name = "author")
public class Autor extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 731710353291440234L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", insertable = false, updatable = false, nullable = false, unique =true)
	private Integer identificador;
	
	@Column(name = "name", nullable = false, length = 150)
	private String nome;
	
	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Livro> listaLivros;
	
	/**
	 * construtor base
	 */
	public Autor() {
		super();
	}
	
	/**
	 * construtor 2 parametros
	 */
	public Autor(Integer identificador, String nome) {
		this();
		this.identificador = identificador;
		this.nome = nome;
	}

	
	/**
	 * contrutor 3 parametros
	 */
	public Autor(Integer identificador, String nome, List<Livro> listaLivros) {
		this(identificador, nome);
		this.listaLivros = listaLivros;
	}

	/**
	 * @return the identificador
	 */
	public Integer getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the listaLivros
	 */
	public List<Livro> getListaLivros() {
		return listaLivros;
	}

	/**
	 * @param listaLivros the listaLivros to set
	 */
	public void setListaLivros(List<Livro> listaLivros) {
		this.listaLivros = listaLivros;
	}
}
