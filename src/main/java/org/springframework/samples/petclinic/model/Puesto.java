package org.springframework.samples.petclinic.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import lombok.Data;


@Entity
@Data
@Table(name = "puesto")
public class Puesto extends BaseEntity {	

	@ManyToOne
	@JoinColumn(name = "festival_id")
//	@NotNull
	private Festival festival;
	
	@ManyToOne
	@JoinColumn(name = "recinto_id")
//	@NotNull
	private Recinto recinto;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "tipos_puesto")
	private TipoPuesto tipoPuesto;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "tipos_tamanio")
	private TipoTamaño tipoTamanio;

	@Column(name = "precio")
	@NotNull
	private Integer precio;

}
