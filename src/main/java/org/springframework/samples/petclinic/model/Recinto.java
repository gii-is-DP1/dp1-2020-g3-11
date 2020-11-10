package org.springframework.samples.petclinic.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import lombok.Data;


@Entity
@Data
@Table(name = "recinto")
public class Recinto extends BaseEntity {

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
