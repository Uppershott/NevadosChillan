package com.nevados.chillan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nevados.chillan.domain.Voucher;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher,Integer> {
	Voucher findById(int id);
	Voucher findByCodigo(String codigo);
	Voucher findByFolio(int folio);
	List<Voucher> findAll();
	<S extends Voucher> S save(Voucher voucher);
}
