package org.springframework.samples.petclinic.model;


import java.util.Set;  

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import lombok.Data;


@Entity
@Data
@Table(name = "oferta")
public class Oferta extends BaseEntity {
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "oferta_entradas", joinColumns = @JoinColumn(name = "oferta_id"),
			inverseJoinColumns = @JoinColumn(name = "entrada_id"))
	private Set<Entrada> entradas;
	
	@Column(name = "precioOferta")
	@NotNull
	private Integer precioOferta;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "tipoOferta_id")
	private TipoOferta tipoOferta;
	
	@Column(name = "nombre")
	@NotNull
	private String nombre; 
	
	@ManyToOne
	@JoinColumn(name = "festival_id")
	private Festival festival;	
	
}
