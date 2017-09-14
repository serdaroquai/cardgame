package org.serdaroquai.me;

import java.security.Principal;

public class Action {

	private String type; 
	private Principal principal;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Principal getPrincipal() {
		return principal;
	}
	
	public void setPrincipal(Principal principal) {
		this.principal = principal;
	}

	@Override
	public String toString() {
		return "Action [type=" + type + ", principal=" + principal + "]";
	}
	
	
	
}
