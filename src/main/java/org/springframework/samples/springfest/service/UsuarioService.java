package org.springframework.samples.springfest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.springfest.model.TipoUsuario;
import org.springframework.samples.springfest.model.Usuario;
import org.springframework.samples.springfest.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

	private UsuarioRepository usuarioRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public boolean checkCorreoExists(String correo) {
		return usuarioRepository.findUsuarioByCorreo(correo).isPresent();
	}
	public boolean checkUsuarioExists(String nombreUsuario) {
		return usuarioRepository.findUsuarioByNombreUsuario(nombreUsuario).isPresent();
	}
	
	public boolean checkDNIExists(String dni) {
		return usuarioRepository.findUsuarioByDNI(dni).isPresent();
	}
	
	public boolean checkTelefonoExists(String telefono) {
		return usuarioRepository.findUsuarioByTelefono(telefono).isPresent();
	}

	@Transactional(readOnly = true)
	public List<Usuario> findAllUsuarios() throws DataAccessException {
		return usuarioRepository.findAllUsuarios();
	}

	@Transactional
	public void saveUsuario(Usuario usuario) throws DataAccessException {
		usuarioRepository.save(usuario);
		userService.saveUser(usuario.getUser());
		if (usuario.getTipoUsuario().getName().equals("sponsor")) {
			authoritiesService.saveAuthorities(usuario.getUser().getUsername(), "sponsor");
		} else {
			authoritiesService.saveAuthorities(usuario.getUser().getUsername(), "usuario");
		}
	}

	@Transactional(readOnly = true)
	public Usuario findUsuarioById(Integer id) {
		return usuarioRepository.findUsuarioById(id);
	}

	@Transactional(readOnly = true)
	public TipoUsuario findTipoUsuario(String usuario) throws DataAccessException {
		return usuarioRepository.findTipoUsuarioByName(usuario);
	}

	@Transactional(readOnly = true)
	public Usuario findUsuarioByUsername(String username) {
		return usuarioRepository.findTipoUsuarioByUserName(username);
	}
}
