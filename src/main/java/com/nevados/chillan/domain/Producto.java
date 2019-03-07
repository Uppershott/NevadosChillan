package com.nevados.chillan.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.context.annotation.Scope;

import java.util.List;


/**
 * The persistent class for the producto database table.
 * 
 */
@Entity
@NamedQuery(name="Producto.findAll", query="SELECT p FROM Producto p")
@Scope("session")
public class Producto implements Serializable {
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
	@OneToMany(mappedBy="productoId")
	private List<Voucher> vouchersProductoId;

	public Producto() {
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

	public List<Voucher> getVouchersProductoId() {
		return this.vouchersProductoId;
	}

	public void setVouchersProductoId(List<Voucher> vouchersProductoId) {
		this.vouchersProductoId = vouchersProductoId;
	}

	public Voucher addVouchersProductoId(Voucher vouchersProductoId) {
		getVouchersProductoId().add(vouchersProductoId);
		vouchersProductoId.setProductoId(this);

		return vouchersProductoId;
	}

	public Voucher removeVouchersProductoId(Voucher vouchersProductoId) {
		getVouchersProductoId().remove(vouchersProductoId);
		vouchersProductoId.setProductoId(null);

		return vouchersProductoId;
	}

}