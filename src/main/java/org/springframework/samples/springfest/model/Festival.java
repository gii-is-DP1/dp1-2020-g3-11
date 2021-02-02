package org.springframework.samples.springfest.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "festival")
public class Festival extends NamedEntity {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "festival")
	private Set<Recinto> recintos;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "festival")
	private Set<Opinion> valoraciones;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "festival")
	private Set<Entrada> entradas;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "festival")
	private Set<Oferta> ofertas;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "festival")
	private Set<Concert> conciertos;

	@Column(name = "aforoMax")
	@NotNull
	private Integer aforoMax;

	@Column(name = "entradasRestantes")
	@NotNull
	private Integer entradasRestantes;

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

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "festivales")
	private Set<Artista> artistas;

	@OneToOne
	private Usuario festivalAdmin;

}