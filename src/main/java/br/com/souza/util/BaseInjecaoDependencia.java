package br.com.souza.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.EntityManager;

import br.com.souza.anotacao.Dao;
import br.com.souza.dao.DaoFactory;
import br.com.souza.dao.impl.BasicDaoImpl;
import br.com.souza.logger.BaseLogger;

/**
 * Classe responsavel pela injeção de dependencia do projeto
 * @author vinicius
 *
 */
public final class BaseInjecaoDependencia {
	
	private static final String ERRO_AO_INJETAR_DEPENDENCIA_NO_EJB = "Erro ao injetar dependencia no EJB";

	/**
	 * construtor privado, execução apenas em metodo static
	 */
	private BaseInjecaoDependencia() {}
	
	public static void injecaoDependenciaDao(Object obj){
		DaoFactory fabrica = null;
		//Obtem todos os atributos da classe
		Field[] atributos = obj.getClass().getDeclaredFields();
		for (Field atributo : atributos) {
			atributo.setAccessible(Boolean.TRUE);
			//Obtem anotacao DAO
			Dao anotacao = atributo.getAnnotation(Dao.class);
			//Possui anotacao DAO
			if(anotacao != null){
				try {
					//cria a fabrica com o metodo que controi o dao
					fabrica = construirFabricaDao(anotacao, fabrica);
					// inseri o dao no campo
					inserirDao(atributo, fabrica, obj);
					// informar o entitymanager
					setEntityManager(atributo, anotacao, obj);
				} catch (IllegalAccessException e) {
					getLogger(obj).erro(ERRO_AO_INJETAR_DEPENDENCIA_NO_EJB, e);
				} catch (NoSuchMethodException e) {
					getLogger(obj).erro(ERRO_AO_INJETAR_DEPENDENCIA_NO_EJB, e);
				} catch (SecurityException e) {
					getLogger(obj).erro(ERRO_AO_INJETAR_DEPENDENCIA_NO_EJB, e);
				} catch (IllegalArgumentException e) {
					getLogger(obj).erro(ERRO_AO_INJETAR_DEPENDENCIA_NO_EJB, e);
				} catch (InvocationTargetException e) {
					getLogger(obj).erro(ERRO_AO_INJETAR_DEPENDENCIA_NO_EJB, e);
				} catch (NoSuchFieldException e) {
					getLogger(obj).erro(ERRO_AO_INJETAR_DEPENDENCIA_NO_EJB, e);
				}
			}
		}
	}
	
	/**
	 * Metodo para construir a fabrica de dao's informada
	 * @param anotacao
	 * @param fabrica
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private static DaoFactory construirFabricaDao(Dao anotacao, DaoFactory fabrica) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method metodoSingleton = anotacao.fabrica().getMethod("getInstance");
		return (DaoFactory) metodoSingleton.invoke(fabrica);
	}

	/**
	 * inclui o entity manager no dao para execução dos metodos de acesso ao banco
	 * @param field
	 * @param anotacaoDAO 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static void setEntityManager(Field field, Dao anotacaoDAO, Object obj) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		String nomeCampoEM = anotacaoDAO.entityManager();
		if(nomeCampoEM != null && !nomeCampoEM.trim().equals("")){
			Field atributoEM = obj.getClass().getDeclaredField(nomeCampoEM);
			atributoEM.setAccessible(Boolean.TRUE);
			((BasicDaoImpl<?>) field.get(obj)).setEm((EntityManager) atributoEM.get(obj));
		} else {
			getLogger(obj).erro("Sem entitymanager declarado no EJB para associar aos dao's.");
		}
	}

	/**
	 * injetar objeto dao no atributo, atraves do metodo da fabrica informada
	 * @param field
	 * @param metodoCriacao
	 * @param fabrica
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	private static void inserirDao(Field field, DaoFactory fabrica, Object obj) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String nomeMetodoCriacao = "criar"+field.getType().getSimpleName();
		Method metodoCriacao = fabrica.getClass().getMethod(nomeMetodoCriacao);
		metodoCriacao.setAccessible(Boolean.TRUE);
		field.set(obj, metodoCriacao.invoke(fabrica));
	}

	/**
	 * metodo retorna objeto de log
	 * @return the logger
	 */
	private static BaseLogger getLogger(Object obj) {
		return new BaseLogger(obj.getClass());
	}
}
