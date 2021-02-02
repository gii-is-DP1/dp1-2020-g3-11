package org.springframework.samples.springfest.model;

import java.util.Set;  

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
	@Min(value = 1)
	private Integer huecos;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "tipos_recinto")
	private TipoRecinto tipoRecinto;


}