package org.springframework.samples.petclinic.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;


@Entity
@Data
@Table(name = "entrada")
public class Entrada extends BaseEntity {
	
	@ManyToOne
	@JoinColumn(name = "festival_id")
	private Festival festival;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "entradaType")
	@NotNull
	private EntradaType entradaType;
	
	@Column(name = "precio")
	@NotNull
	@Positive
	private Integer precio;
	
//	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "entradas")
//	private Set<Oferta> ofertas;
	



}
