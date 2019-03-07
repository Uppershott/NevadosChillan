package com.nevados.chillan.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nevados.chillan.domain.Tipovoucher;

@Repository
public interface TipoVoucherRepository extends JpaRepository <Tipovoucher, Integer> {
	Tipovoucher findById(int id);
	Tipovoucher findByNombre(String nombre);
	List<Tipovoucher> findAll();
	<S extends Tipovoucher> S save(Tipovoucher tipoVoucher);
}
