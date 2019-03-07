package com.nevados.chillan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.nevados.chillan.domain.Usuario;
import com.nevados.chillan.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	//Inicialización de Repositorios
	private UsuarioRepository usuarioRepository;
	
	//-----------------------------------------------------------------------------------------------------------------
	@Autowired
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	//-----------------------------------------------------------------------------------------------------------------
	@Override
	public Usuario findById(int id) {
		Usuario usuario = null;
		try {
			usuario = usuarioRepository.findById(id);
		}catch(ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return usuario;
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@Override
	public Usuario findByRut(String rut) {
		Usuario usuario = null;
		try {
			usuario = usuarioRepository.findByRut(rut);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return usuario;
	}

	//-----------------------------------------------------------------------------------------------------------------
	@Override
	public <S extends Usuario> S save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	//-----------------------------------------------------------------------------------------------------------------
	@Override
	public Usuario findByCorreo(String correo) {
		Usuario usuario = null;
		try {
			usuario = usuarioRepository.findByCorreo(correo);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return usuario;
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@Override
	public List<Usuario> findAll() {
		List<Usuario> usuario = null;
		try {
			usuario = usuarioRepository.findAll();
		}catch(ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return usuario;
	}
	
	/*@Override
	public Usuario findByCaja(Caja caja) {
		Usuario usuario = null;
		try {
			usuario = usuarioRepository.findByCaja(caja);
		}catch(ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return usuario;
	}*/
}
