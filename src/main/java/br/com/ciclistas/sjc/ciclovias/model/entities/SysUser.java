package br.com.ciclistas.sjc.ciclovias.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_USER")
public class SysUser extends DefaultEntity {

	private static final long serialVersionUID = 1L;

	@Column(length = 40)
	private String name;
	
	@Column(updatable = false, nullable = false)
	private String password;

	@Column(updatable = false, nullable = false)
	private String role;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
