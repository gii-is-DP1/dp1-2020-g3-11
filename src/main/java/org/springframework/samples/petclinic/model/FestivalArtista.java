package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class FestivalArtista extends BaseEntity{
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "festival_id")
	private Festival festival;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "artista_id")
	private Artista artista;
	

}
