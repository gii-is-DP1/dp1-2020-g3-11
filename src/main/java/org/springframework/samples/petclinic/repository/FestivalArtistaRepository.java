package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Artista;
import org.springframework.samples.petclinic.model.Festival;
import org.springframework.samples.petclinic.model.FestivalArtista;

public interface FestivalArtistaRepository extends CrudRepository<FestivalArtista, Integer> {

	@Query("select fa.artista from FestivalArtista fa where fa.festival.id = ?1")
	public Collection<Artista> findAllArtistasByFestivalId(int festivalId);
	
	@Query("select fa from FestivalArtista fa where fa.festival.id = ?1 and fa.artista.id = ?2")
	public FestivalArtista findByArtistaIdFestivalId(int festivalId, int artistaId);
	
	@Query("select count(fa)>0 from FestivalArtista fa where fa.festival.id = ?1 and fa.artista.id = ?2")
	public Boolean existByArtistaIdFestivalId(int festivalId, int artistaId);

}
