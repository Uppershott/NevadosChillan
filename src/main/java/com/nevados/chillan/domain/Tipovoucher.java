package com.nevados.chillan.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.context.annotation.Scope;

import java.util.List;


/**
 * The persistent class for the tipovoucher database table.
 * 
 */
@Entity
@NamedQuery(name="Tipovoucher.findAll", query="SELECT t FROM Tipovoucher t")
@Scope("session")
public class Tipovoucher implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;

	@Column(name="descripcion")
	private String descripcion;

	@Column(name="nombre")
	private String nombre;

	@Column(name="visible")
	private byte visible;

	//bi-directional many-to-one association to Voucher
	@OneToMany(mappedBy="tipovoucherId")
	private List<Voucher> vouchersTipoVoucherId;

	public Tipovoucher() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte getVisible() {
		return this.visible;
	}

	public void setVisible(byte visible) {
		this.visible = visible;
	}

	public List<Voucher> getVouchersTipoVoucherId() {
		return this.vouchersTipoVoucherId;
	}

	public void setVouchersTipoVoucherId(List<Voucher> vouchersTipoVoucherId) {
		this.vouchersTipoVoucherId = vouchersTipoVoucherId;
	}

	public Voucher addVouchersTipoVoucherId(Voucher vouchersTipoVoucherId) {
		getVouchersTipoVoucherId().add(vouchersTipoVoucherId);
		vouchersTipoVoucherId.setTipovoucherId(this);

		return vouchersTipoVoucherId;
	}

	public Voucher removeVouchersTipoVoucherId(Voucher vouchersTipoVoucherId) {
		getVouchersTipoVoucherId().remove(vouchersTipoVoucherId);
		vouchersTipoVoucherId.setTipovoucherId(null);

		return vouchersTipoVoucherId;
	}

}