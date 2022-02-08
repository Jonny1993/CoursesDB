//Author: Ramy

package model;

public class Session {
	String id;
	String username;
	String password;
	
	public Session(String id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	 @Override
	public String toString() {
		return "Session [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
}
