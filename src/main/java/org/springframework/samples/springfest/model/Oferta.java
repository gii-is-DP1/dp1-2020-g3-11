package org.springframework.samples.springfest.model;


import java.util.Set;    

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "oferta")
public class Oferta extends BaseEntity {
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "oferta_entradas", joinColumns = @JoinColumn(name = "oferta_id"),
			inverseJoinColumns = @JoinColumn(name = "entrada_id"))
	private Set<Entrada> entradas;
	
    @NotNull
    @Positive
	@Column(name = "precioOferta")
	private Integer precioOferta;
	
    @NotNull
	@ManyToOne(optional=false)
	@JoinColumn(name = "tipoOferta_id")
	private TipoOferta tipoOferta;
	
	@Column(name = "nombre")
	@NotBlank
	private String nombre; 
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "festival_id")
	private Festival festival;	
	
}
