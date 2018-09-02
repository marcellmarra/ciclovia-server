package br.com.ciclistas.sjc.ciclovias.model.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.ciclistas.sjc.ciclovias.resources.config.LocalDateDeserializer;
import br.com.ciclistas.sjc.ciclovias.resources.config.LocalDateSerializer;

/**
 * @author Pedro Hos
 *
 */
@Entity
@Table(name = "OCCURRENCE")
public class Occurrence extends DefaultEntity {

	private static final long serialVersionUID = 6683654832605009831L;

	@Column(name = "name", updatable = false, nullable = false)
	private String name; //TODO: use keycloak in the futere
	
	@Column(nullable = false, updatable = false)
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate occurrenceDate = LocalDate.now();
	
	@Column(updatable = false, nullable = false)
	private Double latitude;

	@Column(updatable = false, nullable = false)
	private Double longitude;
	
	private String location;
	
	@CollectionTable(name="PATH_PHOTOS")
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> pathPhoto;
	
	@OneToOne
	private Status status;
	
	@OneToOne
	private OccurrenceType type;
	
	@Column(length = 1000)
	private String description;

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public OccurrenceType getType() {
		return type;
	}

	public void setType(OccurrenceType type) {
		this.type = type;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<String> getPathPhoto() {
		return pathPhoto;
	}

	public void setPathPhoto(List<String> pathPhoto) {
		this.pathPhoto = pathPhoto;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

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

	public LocalDate getOccurrenceDate() {
		return occurrenceDate;
	}

}
