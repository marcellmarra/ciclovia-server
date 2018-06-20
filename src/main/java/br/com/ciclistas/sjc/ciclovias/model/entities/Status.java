package br.com.ciclistas.sjc.ciclovias.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Pedro Hos
 *
 */
@Entity
@Table(name = "status")
public class Status extends DefaultEntity {

	private static final long serialVersionUID = 6846487498523797594L;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String description;
	
	@Column(name = "pin_name", nullable = false)
	private String pinName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPinName() {
		return pinName;
	}

	public void setPinName(String pinIconName) {
		this.pinName = pinIconName;
	}
}
