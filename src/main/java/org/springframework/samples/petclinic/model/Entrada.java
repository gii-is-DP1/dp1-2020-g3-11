package org.springframework.samples.petclinic.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import lombok.Data;


@Entity
@Data
@Table(name = "entrada")
public class Entrada extends BaseEntity {
	
	@ManyToOne
	@JoinColumn(name = "festival_id")
	private Festival festival;

	@Column(name = "tipoEntrada")
	private TipoEntrada tipoEntrada;
	
	@Column(name = "precio")
	@NotNull
	private Integer precio;



}
