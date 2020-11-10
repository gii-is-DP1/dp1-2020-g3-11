package org.springframework.samples.petclinic.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Data;


@Entity
@Data
@Table(name = "valoracion")
public class Valoracion extends BaseEntity {

	@Column(name = "descripcion")
	private String descripcion;
	
	
	@Range(min=0, max=5)
	@Column(name = "puntuacion")
	@NotNull
	private Integer puntuacion;



}
