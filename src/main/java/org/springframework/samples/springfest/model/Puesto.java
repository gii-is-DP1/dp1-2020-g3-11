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

	@ManyToOne
	@JoinColumn(name = "festival_id")
	private Festival festival;
	
	@ManyToOne
	@JoinColumn(name = "recinto_id")
	@NotNull
	private Recinto recinto;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "tipos_puesto")
	private TipoPuesto tipoPuesto;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "tipos_tamaño")
	private TipoTamaño tipoTamanio;

	@Column(name = "precio")
	@NotNull
	@Positive
	private Integer precio;
	
	@ManyToOne
	@JoinColumn(name = "sponsor_id")
    private Usuario sponsor;

}