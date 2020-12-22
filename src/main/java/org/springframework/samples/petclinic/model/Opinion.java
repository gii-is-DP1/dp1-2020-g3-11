package org.springframework.samples.petclinic.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "opinion")
public class Opinion extends BaseEntity {

	@NotNull
	@ManyToOne
	@JoinColumn(name = "festival_id")
	private Festival festival;

	@Size(min = 10, max = 1024)
	@Column(name = "descripcion", length = 1024)
	@NotBlank
	private String descripcion;

	@Range(min = 0, max = 5)
	@Column(name = "puntuacion")
	@NotNull
	private Integer puntuacion;

	@Column(name = "fecha")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@NotNull
	private LocalDateTime fecha;
	
	@OneToOne
	@NotNull
	private Usuario opinionUsuario;

}