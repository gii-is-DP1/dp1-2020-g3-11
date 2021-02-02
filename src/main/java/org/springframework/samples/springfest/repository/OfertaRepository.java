package org.springframework.samples.springfest.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.springfest.model.Oferta;
import org.springframework.samples.springfest.model.TipoOferta;

public interface OfertaRepository extends CrudRepository<Oferta, Integer> {

    Collection<Oferta> findAll();
        
	@Query("select c from Oferta c where c.festival.id = ?1")
	Collection<Oferta> findAllOfertasByFestivalId(int festivalId);
	
	@Query("SELECT t FROM TipoOferta t where t.name = ?1")
	TipoOferta findTipoOfertaByName(String tipoOferta);
	
	@Query("SELECT t.name FROM TipoOferta t ORDER BY t.name")
	List<String> findTiposOfertas() throws DataAccessException;

//    @Query("select oe.oferta from OfertaEntradas oe where oe.entrada.id = ?1")
//	public Collection<Oferta> findAllOfertasByEntradaId(int entradaId);
//	
	

}