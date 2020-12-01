package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.TipoUsuario;
import org.springframework.samples.petclinic.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

	@Query("SELECT utype FROM TipoUsuario utype where utype.name = ?1")
	TipoUsuario findTipoUsuarioByName(String usuario);

}
