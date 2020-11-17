package org.springframework.samples.petclinic.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;


@Entity
@Data
@Table(name = "recinto")
public class Recinto extends NamedEntity {
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recinto")
	private Set<Concierto> conciertos;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recinto")
	private Set<Puesto> puestos;
	
	@ManyToOne
	@JoinColumn(name = "festival_id")
	private Festival festival;
	
	@Column(name = "aforoMaxRec")
	@NotNull
//	@Min(value = 1)
	private Integer aforoMaxRec;

	@Column(name = "huecos")
	@NotNull
//	@Min(value = 1)
	private Integer huecos;

	@ManyToOne
	@JoinColumn(name = "tipos_recinto")
	private TipoRecinto tipoRecinto;

	@Column(name = "numMaxEscenarios")
	private Integer numMaxEscenarios;

}