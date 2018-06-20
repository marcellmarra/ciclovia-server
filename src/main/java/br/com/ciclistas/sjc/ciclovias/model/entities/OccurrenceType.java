package br.com.ciclistas.sjc.ciclovias.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Pedro Hos
 *
 */
@Entity
@Table(name = "occurrence_type")
public class OccurrenceType extends DefaultEntity {

	private static final long serialVersionUID = -2642088687432648811L;
	
	@Column(nullable = false)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
