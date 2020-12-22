package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.TipoOferta;
import org.springframework.samples.petclinic.repository.OfertaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OfertaService {
	@Autowired
	OfertaRepository ofertaRepo;

	public Collection<Oferta> findAll() throws DataAccessException {
		return ofertaRepo.findAll();
	}

	@Transactional(readOnly = true)
	public Collection<Oferta> findAllOfertasByFestivalId(int festivalId) throws DataAccessException {
		return ofertaRepo.findAllOfertasByFestivalId(festivalId);
	}
	
	@Transactional(readOnly = true)
	public Oferta findById(int id) throws DataAccessException {
		return ofertaRepo.findById(id).get();
	}
	
	@Transactional(readOnly = true)
	public TipoOferta findTipoOfertaByName(String tipoOferta) throws DataAccessException {
		return ofertaRepo.findTipoOfertaByName(tipoOferta);
	}
	
	@Transactional
	public void delete(Oferta oferta) throws DataAccessException {
		ofertaRepo.deleteById(oferta.getId());

	}
	
	@Transactional(readOnly = true)
    public Collection<String> findTiposOfertas() throws DataAccessException {
        return ofertaRepo.findTiposOfertas();
    } 
	
	@Transactional
	public void save(@Valid Oferta oferta) throws DataAccessException{
		ofertaRepo.save(oferta);

	}
}
