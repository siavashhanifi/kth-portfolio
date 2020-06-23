package fileCatalog.common.integration;

import java.io.Serializable;

public class FileMeta implements Serializable{

	private static final long serialVersionUID = -4503363124472458401L;
	private String name;
	private String owner;
	private long size;
	
	public FileMeta(String name, long filesize) {
		this.name = name;
		this.owner = owner;
		this.size = filesize;
	}

	public FileMeta(String name, String owner, long filesize) {
		this.name = name;
		this.owner = owner;
		this.size = filesize;
	}

	public String getName() {
		return this.name;
	}
	public String getOwner() {
		return this.owner;
	}
	public long getSize() {
		return this.size;
	}

	public void setOwner(String username) {
		this.owner = username;
	}
	
}
