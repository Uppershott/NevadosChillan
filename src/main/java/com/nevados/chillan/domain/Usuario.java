package com.nevados.chillan.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.context.annotation.Scope;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
@Scope("session")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;

	@Column(name="apellido")
	private String apellido;

	@Column(name="correo")
	private String correo;

	@Column(name="fechaRegistro")
	@Temporal(TemporalType.DATE)
	private Date fechaRegistro;

	@Column(name="nombre")
	private String nombre;

	@Column(name="rut")
	private String rut;

	@Column(name="telefono")
	private int telefono;

	//bi-directional many-to-one association to Caja
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="idCaja")
	private Caja caja;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="rol")
	private Rol rol;

	//bi-directional many-to-one association to Voucher
	@OneToMany(mappedBy="usuarioBeneficiario")
	private List<Voucher> vouchersBeneficiario;

	//bi-directional many-to-one association to Voucher
	@OneToMany(mappedBy="usuarioBeneficiarioCobra")
	private List<Voucher> vouchersBeneficiarioCobra;

	//bi-directional many-to-one association to Voucher
	@OneToMany(mappedBy="usuarioCajera")
	private List<Voucher> vouchersCajera;

	//bi-directional many-to-one association to Voucher
	@OneToMany(mappedBy="usuarioCajeraCobra")
	private List<Voucher> vouchersCajeraCobra;

	public Usuario() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRut() {
		return this.rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public int getTelefono() {
		return this.telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public Caja getCaja() {
		return this.caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public List<Voucher> getVouchersBeneficiario() {
		return this.vouchersBeneficiario;
	}

	public void setVouchersBeneficiario(List<Voucher> vouchersBeneficiario) {
		this.vouchersBeneficiario = vouchersBeneficiario;
	}

	public Voucher addVouchersBeneficiario(Voucher vouchersBeneficiario) {
		getVouchersBeneficiario().add(vouchersBeneficiario);
		vouchersBeneficiario.setUsuarioBeneficiario(this);

		return vouchersBeneficiario;
	}

	public Voucher removeVouchersBeneficiario(Voucher vouchersBeneficiario) {
		getVouchersBeneficiario().remove(vouchersBeneficiario);
		vouchersBeneficiario.setUsuarioBeneficiario(null);

		return vouchersBeneficiario;
	}

	public List<Voucher> getVouchersBeneficiarioCobra() {
		return this.vouchersBeneficiarioCobra;
	}

	public void setVouchersBeneficiarioCobra(List<Voucher> vouchersBeneficiarioCobra) {
		this.vouchersBeneficiarioCobra = vouchersBeneficiarioCobra;
	}

	public Voucher addVouchersBeneficiarioCobra(Voucher vouchersBeneficiarioCobra) {
		getVouchersBeneficiarioCobra().add(vouchersBeneficiarioCobra);
		vouchersBeneficiarioCobra.setUsuarioBeneficiarioCobra(this);

		return vouchersBeneficiarioCobra;
	}

	public Voucher removeVouchersBeneficiarioCobra(Voucher vouchersBeneficiarioCobra) {
		getVouchersBeneficiarioCobra().remove(vouchersBeneficiarioCobra);
		vouchersBeneficiarioCobra.setUsuarioBeneficiarioCobra(null);

		return vouchersBeneficiarioCobra;
	}

	public List<Voucher> getVouchersCajera() {
		return this.vouchersCajera;
	}

	public void setVouchersCajera(List<Voucher> vouchersCajera) {
		this.vouchersCajera = vouchersCajera;
	}

	public Voucher addVouchersCajera(Voucher vouchersCajera) {
		getVouchersCajera().add(vouchersCajera);
		vouchersCajera.setUsuarioCajera(this);

		return vouchersCajera;
	}

	public Voucher removeVouchersCajera(Voucher vouchersCajera) {
		getVouchersCajera().remove(vouchersCajera);
		vouchersCajera.setUsuarioCajera(null);

		return vouchersCajera;
	}

	public List<Voucher> getVouchersCajeraCobra() {
		return this.vouchersCajeraCobra;
	}

	public void setVouchersCajeraCobra(List<Voucher> vouchersCajeraCobra) {
		this.vouchersCajeraCobra = vouchersCajeraCobra;
	}

	public Voucher addVouchersCajeraCobra(Voucher vouchersCajeraCobra) {
		getVouchersCajeraCobra().add(vouchersCajeraCobra);
		vouchersCajeraCobra.setUsuarioCajeraCobra(this);

		return vouchersCajeraCobra;
	}

	public Voucher removeVouchersCajeraCobra(Voucher vouchersCajeraCobra) {
		getVouchersCajeraCobra().remove(vouchersCajeraCobra);
		vouchersCajeraCobra.setUsuarioCajeraCobra(null);

		return vouchersCajeraCobra;
	}

}