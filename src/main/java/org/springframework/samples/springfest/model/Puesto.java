package org.springframework.samples.springfest.model;



import java.util.Set;

import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "puesto")
public class Puesto extends BaseEntity {	

	@ManyToOne(optional=false)
	@JoinColumn(name = "festival_id")
	private Festival festival;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "recinto_id")
	@NotNull
	private Recinto recinto;

	@ManyToOne(optional=false)
	@NotNull
	@JoinColumn(name = "tipos_puesto")
	private TipoPuesto tipoPuesto;

	@ManyToOne(optional=false)
	@NotNull
	@JoinColumn(name = "tipos_tamaño")
	private TipoTamaño tipoTamanio;

	@Column(name = "precio")
	@NotNull
	@Positive
	private Integer precio;
	
	@ManyToOne(optional=true)
	@JoinColumn(name = "sponsor_id")
    private Usuario sponsor;

}
