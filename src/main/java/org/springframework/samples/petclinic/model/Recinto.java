package org.springframework.samples.petclinic.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Constraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;

import lombok.Data;


@Entity
@Data
@Table(name = "recinto")
public class Recinto extends NamedEntity {
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recinto")
	private Set<Concert> conciertos;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recinto")
	private Set<Puesto> puestos;
	
	@ManyToOne
	@JoinColumn(name = "festival_id")
	@NotNull
	private Festival festival;

	@Column(name = "huecos")
	@NotNull
	private Integer huecos;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "tipos_recinto")
	private TipoRecinto tipoRecinto;

	@Column(name = "numMaxEscenarios")
	private Integer numMaxEscenarios;

}