package org.springframework.samples.springfest.model;



import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "entrada")
public class Entrada extends BaseEntity {
	
	@ManyToMany
    @JoinColumn(name = "usuario_id")
    private Set<Usuario> usuario;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "festival_id")
	private Festival festival;

	@ManyToOne(optional=false)
	@JoinColumn(name = "entradaType")
	@NotNull
	private EntradaType entradaType;
	
	@Column(name = "precio")
	@NotNull
	@Positive
	private Integer precio;
	
	@ManyToMany
    @JoinColumn(name = "oferta_id")
	private Set<Oferta> ofertas;
	



}