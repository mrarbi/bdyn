package com.orbit.dynamix.utils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * Cette classe repr�sente un couple (<i>identifiant</i>, <i>valeur</i>) d'une
 * liste de valeurs. C'est un simple Java Bean muni de getter et setter pour ses
 * propriétés id et value.
 * 
 * @author m.arbi
 */
public class IDValuePair implements Serializable, Cloneable {

	private static Logger log = LoggerFactory.getLogger(IDValuePair.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * L'identifiant.
	 */
	private Object id;

	/**
	 * La valeur.
	 */
	private Object value;

	private Object code;
	
	private Object secondCode;
	
	private Object value2;
    
    private Object value3;

	public Object getCode() {
		return code;
	}

	public void setCode(Object code) {
		this.code = code;
	}

	/**
	 * Constructeur par d�faut.
	 */
	public IDValuePair() {

	}

	/**
	 * Contructeur avec param�tres.
	 * 
	 * @param key
	 *            L'identifiant de type String.
	 * @param label
	 *            La valeur de type String.
	 */
	public IDValuePair(String key, String label) {
		this.id = key;
		this.value = label;
	}

	/**
	 * Contructeur avec param�tres.
	 * 
	 * @param key
	 *            L'identifiant de type String.
	 * @param label
	 *            La valeur de type String.
	 */
	public IDValuePair(Object key, Object code, Object label) {
		this.id = key;
		this.code = code;
		this.value = label;
	}

	/**
	 * Contructeur avec param�tres.
	 * 
	 * @param key
	 *            L'identifiant de type String.
	 * @param label
	 *            La valeur de type String.
	 */
	public IDValuePair(Object key, Object code, Object label, Object secondCode) {
		this.id = key;
		this.code = code;
		this.value = label;
		this.secondCode = secondCode;
	}
	/**
	 * Contructeur avec param�tres.
	 * 
	 * @param key
	 *            L'identifiant de type String.
	 * @param label
	 *            La valeur de type String.
	 */
	public IDValuePair(Object key, Object label) {
		this.id = key;
		this.value = label;
	}

	/**
     * Contructeur avec param�tres.
     * @param key L'identifiant de type String.
     * @param label La valeur de type String.
     * @param label2 La valeur2 de type String.
     * @param label3 La valeur3 de type String.
     *   
     */
    public IDValuePair(Object key, Object code, Object label, Object label2, Object label3) {
        this.id = key;        
        this.code = code;
        this.value = label;
        this.value2 = label2;
        this.value3 = label3;
    }
    
	/**
	 * Retourne id L'identifiant.
	 * 
	 * @return String.
	 */
	public final Object getId() {
		return id;
	}

	/**
	 * Positionne id.
	 * 
	 * @param key
	 *            L'identifiant de type String.
	 */
	public final void setId(Object key) {
		this.id = key;
	}

	/**
	 * Retourne value La valeur.
	 * 
	 * @return String.
	 */
	public final Object getValue() {
		return value;
	}

	/**
	 * Positionne value.
	 * 
	 * @param label
	 *            La valeur de type String.
	 */
	public final void setValue(Object label) {
		this.value = label;
	}

	public IDValuePair clone() {
		IDValuePair copie = null;
		try {
			copie = new IDValuePair();
			BeanUtils.copyProperties(copie, this);
		} catch (IllegalAccessException e) {
			log.error("Error", e);
		} catch (InvocationTargetException e) {
			log.error("Error", e);
		}
		return copie;
	}
	public Object getSecondCode() {
		return secondCode;
	}

	public void setSecondCode(Object secondCode) {
		this.secondCode = secondCode;
	}

	public Object getValue2() {
		return value2;
	}

	public void setValue2(Object value2) {
		this.value2 = value2;
	}

	public Object getValue3() {
		return value3;
	}

	public void setValue3(Object value3) {
		this.value3 = value3;
	}

}
