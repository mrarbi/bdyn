package com.orbit.dynamix.vo;

import java.io.Serializable;

/**
 * 
 * @author m.arbi
 *
 */
public class MatVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pos;
	
	private String id;

	private String value;

	public MatVO() {
	}

	public MatVO(String id, String value) {
		this.id = id;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	
	public void setId(String id) {
		this.id = id;
	}

	
	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	
	public String getPos() {
		return pos;
	}

	
	public void setPos(String pos) {
		this.pos = pos;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof MatVO))
			return false;
		MatVO pairo = (MatVO) o;
		return this.id.equals(pairo.getId());
	}

}
