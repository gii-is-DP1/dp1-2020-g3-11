package org.springframework.samples.springfest.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.springfest.model.TipoUsuario;
import org.springframework.samples.springfest.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

	@Query("SELECT utype FROM TipoUsuario utype where utype.name = ?1")
	TipoUsuario findTipoUsuarioByName(String usuario);

	@Query("SELECT u FROM Usuario u")
	List<Usuario> findAllUsuarios();

	Collection<Usuario> findAll();

	@Query("SELECT u FROM Usuario u where u.id = ?1")
	Usuario findUsuarioById(int id);

	@Query("SELECT u FROM Usuario u where u.user.username = ?1")
	Usuario findTipoUsuarioByUserName(String username);
	
	@Query("SELECT u FROM Usuario u where u.correo = ?1")
	Optional<Usuario> findUsuarioByCorreo(String correo);
	
	@Query("SELECT u FROM Usuario u where u.DNI = ?1")
	Optional<Usuario> findUsuarioByDNI(String dni);
	
	@Query("SELECT u FROM Usuario u where u.user.username = ?1")
	Optional<Usuario> findUsuarioByNombreUsuario(String nombreUsuario);

	@Query("SELECT u FROM Usuario u where u.telefono = ?1")
	Optional<Usuario> findUsuarioByTelefono(String telefono);

}
