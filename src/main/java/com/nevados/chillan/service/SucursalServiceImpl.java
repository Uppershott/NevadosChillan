package com.nevados.chillan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.nevados.chillan.domain.Sucursal;
import com.nevados.chillan.repository.SucursalRepository;

@Service
public class SucursalServiceImpl implements SucursalService {

	private SucursalRepository sucursalRepository;
	
	@Autowired
	public SucursalServiceImpl(SucursalRepository sucursalRepository) {
		super();
		this.sucursalRepository = sucursalRepository;
	}

	@Override
	public Sucursal findById(int id) {
		Sucursal sucursal = null;
		try {
			sucursal = sucursalRepository.findById(id);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return sucursal;
	}

	@Override
	public <S extends Sucursal> S save(Sucursal sucursal) {
		return sucursalRepository.save(sucursal);
	}
	
	@Override
	public List<Sucursal> findAll() {
		List<Sucursal> sucursal = null;
		try {
			sucursal = sucursalRepository.findAll();
			System.out.println("Sucursales: " + sucursal.toString());
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return sucursal;
	}
	
	@Override
	public Sucursal findByNombre(String nombre) {
		Sucursal sucursal = null;
		try {
			sucursal = sucursalRepository.findByNombre(nombre);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return sucursal;
	}
}
