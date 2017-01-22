package br.com.souza.anotacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.souza.dao.DaoFactory;

/**
 * Anotacao com informacao para construçao do dao por injecao
 * de dependencia
 * 
 * @author vinicius
 *
 */
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DAO {
	
	public Class<DaoFactory> fabrica();
}
