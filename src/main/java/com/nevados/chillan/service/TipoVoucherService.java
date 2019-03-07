package com.nevados.chillan.service;
import java.util.List;
import com.nevados.chillan.domain.Tipovoucher;

public interface TipoVoucherService {
	Tipovoucher findById(int id);
	Tipovoucher findByNombre(String nombre);
	List<Tipovoucher> findAll();
	<S extends Tipovoucher> S save(Tipovoucher tipoVoucher);
}
