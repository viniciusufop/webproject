/**
 * 
 */
package br.com.souza.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author vinicius
 *
 */
@Entity
@Table(name = "book")
public class Livro extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 22668082031329978L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", insertable = false, updatable = false, nullable = false, unique =true)
	private Integer identificador;
	
	@Column(name = "title", nullable = false, length = 150)
	private String titulo;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="id_author", referencedColumnName = "id", nullable = false)
	private Autor autor;
	
	/**
	 * 
	 */
	public Livro() {
		super();
	}
	
	/**
	 * 
	 */
	public Livro(Integer identificador, String titulo, Autor autor) {
		this();
		this.identificador = identificador;
		this.titulo = titulo;
		this.autor = autor;
	}

	/**
	 * @return the identificador
	 */
	public Integer getIdentificador() {
		return identificador;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @return the autor
	 */
	public Autor getAutor() {
		return autor;
	}

	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @param autor the autor to set
	 */
	public void setAutor(Autor autor) {
		this.autor = autor;
	}
}
