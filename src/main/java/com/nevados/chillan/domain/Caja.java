package com.nevados.chillan.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.context.annotation.Scope;

import java.util.List;


/**
 * The persistent class for the caja database table.
 * 
 */
@Entity
@NamedQuery(name="Caja.findAll", query="SELECT c FROM Caja c")
@Scope("session")
public class Caja implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;

	@Column(name="correo")
	private String correo;

	@Column(name="password")
	private String password;

	@Column(name="visible")
	private byte visible;

	//bi-directional many-to-one association to Sucursal
	@ManyToOne
	@JoinColumn(name="idSucursal")
	private Sucursal sucursal;

	//bi-directional many-to-one association to Usuario
	
	@OneToMany(mappedBy="caja", cascade = CascadeType.ALL)
	private List<Usuario> usuarios;

	//bi-directional many-to-one association to Voucher
	@OneToMany(mappedBy="cajaRegistro")
	private List<Voucher> vouchersRegistro;

	//bi-directional many-to-one association to Voucher
	@OneToMany(mappedBy="cajaCobro")
	private List<Voucher> vouchersCajaCobro;

	public Caja() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte getVisible() {
		return this.visible;
	}

	public void setVisible(byte visible) {
		this.visible = visible;
	}

	public Sucursal getSucursal() {
		return this.sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setCaja(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setCaja(null);

		return usuario;
	}

	public List<Voucher> getVouchersRegistro() {
		return this.vouchersRegistro;
	}

	public void setVouchersRegistro(List<Voucher> vouchersRegistro) {
		this.vouchersRegistro = vouchersRegistro;
	}

	public Voucher addVouchersRegistro(Voucher vouchersRegistro) {
		getVouchersRegistro().add(vouchersRegistro);
		vouchersRegistro.setCajaRegistro(this);

		return vouchersRegistro;
	}

	public Voucher removeVouchersRegistro(Voucher vouchersRegistro) {
		getVouchersRegistro().remove(vouchersRegistro);
		vouchersRegistro.setCajaRegistro(null);

		return vouchersRegistro;
	}

	public List<Voucher> getVouchersCajaCobro() {
		return this.vouchersCajaCobro;
	}

	public void setVouchersCajaCobro(List<Voucher> vouchersCajaCobro) {
		this.vouchersCajaCobro = vouchersCajaCobro;
	}

	public Voucher addVouchersCajaCobro(Voucher vouchersCajaCobro) {
		getVouchersCajaCobro().add(vouchersCajaCobro);
		vouchersCajaCobro.setCajaCobro(this);

		return vouchersCajaCobro;
	}

	public Voucher removeVouchersCajaCobro(Voucher vouchersCajaCobro) {
		getVouchersCajaCobro().remove(vouchersCajaCobro);
		vouchersCajaCobro.setCajaCobro(null);

		return vouchersCajaCobro;
	}

}