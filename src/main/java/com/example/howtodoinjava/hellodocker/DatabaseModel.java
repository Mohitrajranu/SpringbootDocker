package com.example.howtodoinjava.hellodocker;

public class DatabaseModel {

	private String host = null;
	private String databasename = null;
	private String username = null;
	private String password = null;
	private String port = null;
	private String webservicetype = null;
	private String databasetype = null;
	private String schema = null;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getDatabasename() {
		return databasename;
	}
	public void setDatabasename(String databasename) {
		this.databasename = databasename;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getWebservicetype() {
		return webservicetype;
	}
	public void setWebservicetype(String webservicetype) {
		this.webservicetype = webservicetype;
	}
	public String getDatabasetype() {
		return databasetype;
	}
	public void setDatabasetype(String databasetype) {
		this.databasetype = databasetype;
	}
	
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	@Override
	public String toString() {
		return String.format(
				"DatabaseModel [host=%s, databasename=%s, username=%s, password=%s, port=%s, webservicetype=%s, databasetype=%s, schema=%s]",
				host, databasename, username, password, port, webservicetype, databasetype, schema);
	}
	
	
}
