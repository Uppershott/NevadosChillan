package com.nevados.chillan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.nevados.chillan.domain.Tipovoucher;
import com.nevados.chillan.repository.TipoVoucherRepository;

@Service
public class TipoVoucherServiceImpl implements TipoVoucherService {
	
private TipoVoucherRepository tipoVoucherRepository;
	
	@Autowired
	public TipoVoucherServiceImpl(TipoVoucherRepository tipoVoucherRepository) {
		super();
		this.tipoVoucherRepository = tipoVoucherRepository;
	}

	@Override
	public Tipovoucher findById(int id) {
		Tipovoucher tipoVoucher = null;
		try {
			tipoVoucher = tipoVoucherRepository.findById(id);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return tipoVoucher;
	}
	
	@Override
	public <S extends Tipovoucher> S save(Tipovoucher tipoVoucher) {
		return tipoVoucherRepository.save(tipoVoucher);
	}
	
	@Override
	public List<Tipovoucher> findAll() {
		List<Tipovoucher> tipoVoucher = null;
		try {
			tipoVoucher = tipoVoucherRepository.findAll();
			System.out.println("Tipos de Voucher: " + tipoVoucher.toString());
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return tipoVoucher;
	}
	
	@Override
	public Tipovoucher findByNombre(String nombre) {
		Tipovoucher tipoVoucher = null;
		try {
			tipoVoucher = tipoVoucherRepository.findByNombre(nombre);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return tipoVoucher;
	}
}
