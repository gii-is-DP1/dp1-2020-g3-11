package org.springframework.samples.petclinic.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import lombok.Data;


@Entity
@Data
@Table(name = "recinto")
public class Recinto extends BaseEntity {
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recinto")
	private Set<Puesto> puestos;

	@ManyToOne
	@JoinColumn(name = "festival_id")
	private Festival festival;
	
	@Column(name = "aforoMaxRec")
	@NotNull
	private Integer aforoMaxRec;

	@Column(name = "huecos")
	@NotNull
	private Integer huecos;

	@Column(name = "tipoRecinto")
	private TipoRecinto tipoRecinto;

	@Column(name = "numMaxEscenarios")
	@NotNull
	private Integer numMaxEscenarios;

}
