package org.springframework.samples.petclinic.model;

import java.util.Set; 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "artista")
public class Artista extends NamedEntity {

	@Column(name = "correo")
	@NotBlank
	private String correo;

	@Column(name = "telefono")
	@NotBlank
	private String telefono;

	@ManyToOne
	@JoinColumn(name = "genero_id")
	private GeneroType genero;

	@ManyToMany
	@JoinColumn(name = "festival_id")
	private Set<Festival> festivales;

}
