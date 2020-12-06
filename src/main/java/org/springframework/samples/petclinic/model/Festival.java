package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;


@Entity
@Data
@Table(name = "festival")
public class Festival extends NamedEntity {
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "festival")
	private Set<Recinto> recintos;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "festival")
	private Set<Opinion> valoraciones;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "festival")
	private Set<Concert> conciertos;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "festival")
	private Set<Entrada> entradas;
	
	@Column(name = "aforoMax")
	@NotNull
	private Integer aforoMax;

	@Column(name = "localizacion")
	@NotBlank
	private String localizacion;

	@Column(name = "fechaCom")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	private LocalDate fechaCom;

	@Column(name = "fechaFin")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	private LocalDate fechaFin;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "festival")
	private Set<FestivalArtista> artistas;

}
