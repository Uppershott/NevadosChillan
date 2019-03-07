package com.nevados.chillan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.nevados.chillan.domain.Caja;
import com.nevados.chillan.repository.CajaRepository;

@Service
public class CajaServiceImpl implements CajaService{
	
	private CajaRepository cajaRepository;
	
	@Autowired
	public CajaServiceImpl(CajaRepository cajaRepository) {
		super();
		this.cajaRepository = cajaRepository;
	}

	@Override
	public Caja findById(int id) {
		Caja caja = null;
		try {
			caja = cajaRepository.findById(id);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return caja;
	}
	
	@Override
	public Caja findByCorreo(String correo) {
		Caja caja = null;
		try {
			caja = cajaRepository.findByCorreo(correo);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return caja;
	}	
	
	@Override
	public Caja findByPassword(String password) {
		Caja caja = null;
		try {
			caja = cajaRepository.findByPassword(password);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return caja;
	}	
	
	@Override
	public <S extends Caja> S save(Caja caja) {
		return cajaRepository.save(caja);
	}
	
	@Override
	public List<Caja> findAll() {
		List<Caja> caja = null;
		try {
			caja = cajaRepository.findAll();
			System.out.println("Caja: " + caja.toString());
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return caja;
	}
}
