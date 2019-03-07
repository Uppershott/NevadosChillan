package com.nevados.chillan.service;

import java.util.List;

import com.nevados.chillan.domain.Voucher;

public interface VoucherService {
	Voucher findById(int id);
	Voucher findByCodigo(String codigo);
	Voucher findByFolio(int folio);
	List<Voucher> findAll();
	<S extends Voucher> S save(Voucher voucher);
}
