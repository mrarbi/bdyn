package com.orbit.dynamix.action;

import org.apache.struts2.convention.annotation.Namespace;

/**
 * 
 */
@Namespace(value = "/admin")
public class AbstarctAdminAction extends AbstarctAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String titreAction;
	

	public String getTitreAction() {
		return titreAction;
	}

	public void setTitreAction(String titreAction) {
		this.titreAction = titreAction;
	}
	
}
