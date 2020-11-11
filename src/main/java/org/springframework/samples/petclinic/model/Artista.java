package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

	@Column(name = "telefono")
	@NotNull
	@Digits(fraction = 0, integer = 10)
	private String telefono;

	@ManyToOne
	@JoinColumn(name = "genero_id")
	private GeneroType genero;

	@OneToMany(mappedBy = "artista", cascade = CascadeType.ALL)
	private Set<FestivalArtista> festivales;

}
