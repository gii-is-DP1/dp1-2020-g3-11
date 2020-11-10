package org.springframework.samples.petclinic.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import lombok.Data;


@Entity
@Data
@Table(name = "puesto")
public class Puesto extends BaseEntity {

	@Column(name = "tipoPuesto")
	private TipoPuesto tipoPuesto;


	@Column(name = "tipoTamaño")
	private TipoTamaño tipoTamaño;

	@Column(name = "precio")
	@NotNull
	private Integer precio;

}
