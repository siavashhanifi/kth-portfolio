package fileCatalog.common.integration;

import java.io.Serializable;

public class ActionDetailsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String user;
	private String action;
	
	public ActionDetailsDTO(String user, String action) {
		this.user = user;
		this.action = action;
	}

	public String getAction() {
		return this.action;
	}

	public String getUser() {
		return this.user;
	}
}