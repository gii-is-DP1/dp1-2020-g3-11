package org.springframework.samples.petclinic.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import lombok.Data;


@Entity
@Data
@Table(name = "artista")
public class Artista extends NamedEntity {

	@Column(name = "correo")
	@NotBlank
	private String correo;
	
	@Column(name = "genero")
	@NotNull
	private Genero genero;


	@Column(name = "telefono")
	@NotNull
	@Digits(fraction = 0, integer = 10)
	private String telefono;

	

}

