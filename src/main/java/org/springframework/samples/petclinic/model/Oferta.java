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
@Table(name = "oferta")
public class Oferta extends BaseEntity {
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "oferta")
	private Set<Entrada> entradas;
	
	@Column(name = "precioOferta")
	@NotNull
	private Integer precioOferta;
	
	@ManyToOne
	@JoinColumn(name = "tipoOferta_id")
	private TipoOferta tipoOferta;


}
