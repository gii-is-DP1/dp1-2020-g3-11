package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Artista;
import org.springframework.samples.petclinic.model.Festival;
import org.springframework.samples.petclinic.model.FestivalArtista;
import org.springframework.samples.petclinic.repository.FestivalArtistaRepository;
import org.springframework.samples.petclinic.repository.FestivalRepository;
import org.springframework.stereotype.Service;

@Service
public class FestivalArtistaService {

	@Autowired
	FestivalArtistaRepository festivalArtistaRepository;

	public void save(FestivalArtista fa) {
		festivalArtistaRepository.save(fa);
	}

	public Collection<Artista> findAllArtistasByFestivalId(int festivalId) {
		return festivalArtistaRepository.findAllArtistasByFestivalId(festivalId);
	}

	public FestivalArtista findByArtistaIdFestivalId(int festivalId, int artistaId) {
		return festivalArtistaRepository.findByArtistaIdFestivalId(festivalId, artistaId);
	}

	public void delete(FestivalArtista fa) {
		festivalArtistaRepository.delete(fa);
	}

	public Boolean existByArtistaIdFestivalId(int festivalId, int artistaId) {
		return festivalArtistaRepository.existByArtistaIdFestivalId(festivalId, artistaId);
	}

}
