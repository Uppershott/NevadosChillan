package com.nevados.chillan.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.context.annotation.Scope;

import java.util.List;


/**
 * The persistent class for the sucursal database table.
 * 
 */
@Entity
@NamedQuery(name="Sucursal.findAll", query="SELECT s FROM Sucursal s")
@Scope("session")
public class Sucursal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;

	@Column(name="direccion")
	private String direccion;

	@Column(name="nombre")
	private String nombre;

	@Column(name="telefono")
	private int telefono;

	@Column(name="visible")
	private byte visible;

	//bi-directional many-to-one association to Caja
	@OneToMany(mappedBy="sucursal")
	private List<Caja> cajas;

	public Sucursal() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTelefono() {
		return this.telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public byte getVisible() {
		return this.visible;
	}

	public void setVisible(byte visible) {
		this.visible = visible;
	}

	public List<Caja> getCajas() {
		return this.cajas;
	}

	public void setCajas(List<Caja> cajas) {
		this.cajas = cajas;
	}

	public Caja addCaja(Caja caja) {
		getCajas().add(caja);
		caja.setSucursal(this);

		return caja;
	}

	public Caja removeCaja(Caja caja) {
		getCajas().remove(caja);
		caja.setSucursal(null);

		return caja;
	}

}