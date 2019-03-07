package com.nevados.chillan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.nevados.chillan.domain.Voucher;
import com.nevados.chillan.repository.VoucherRepository;

@Service
public class VoucherServiceImpl implements VoucherService{
	
	private VoucherRepository voucherRepository;
	
	@Autowired
	public VoucherServiceImpl(VoucherRepository voucherRepository) {
		super();
		this.voucherRepository = voucherRepository;
	}

	@Override
	public Voucher findById(int id) {
		Voucher voucher = null;
		try {
			voucher = voucherRepository.findById(id);
		}catch(ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return voucher;
	}
	
	@Override
	public Voucher findByCodigo(String codigo) {
		Voucher voucher = null;
		try {
			voucher = voucherRepository.findByCodigo(codigo);
		}catch(ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return voucher;
	}
	
	@Override
	public Voucher findByFolio(int id) {
		Voucher voucher = null;
		try {
			voucher = voucherRepository.findByFolio(id);
		}catch(ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return voucher;
	}
	
	@Override
	public <S extends Voucher> S save(Voucher voucher) {
		return voucherRepository.save(voucher);
	}
	
	@Override
	public List<Voucher> findAll() {
		List<Voucher> voucher = null;
		try {
			voucher = voucherRepository.findAll();
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return voucher;
	}
}
