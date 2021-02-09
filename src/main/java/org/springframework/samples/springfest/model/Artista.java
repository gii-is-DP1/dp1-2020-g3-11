package org.springframework.samples.springfest.model;

import java.util.Set; 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "artista")
public class Artista extends AuditableEntity {

	@Column(name = "correo")
	@NotBlank
	private String correo;

	@Column(name = "telefono")
	@NotBlank
	private String telefono;

	@ManyToOne(optional=false)
	@JoinColumn(name = "genero_id")
	@NotNull
	private GeneroType genero;

	@ManyToMany
	@JoinColumn(name = "festival_id")
	private Set<Festival> festivales;

}
