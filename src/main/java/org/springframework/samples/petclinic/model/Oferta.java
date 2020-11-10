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
@Table(name = "oferta")
public class Oferta extends BaseEntity {

	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "precioOferta")
	@NotNull
	private Integer precioOferta;



}
