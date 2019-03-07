package com.nevados.chillan.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.context.annotation.Scope;

import java.util.Date;


/**
 * The persistent class for the voucher database table.
 * 
 */
@Entity
@NamedQuery(name="Voucher.findAll", query="SELECT v FROM Voucher v")
@Scope("session")
public class Voucher implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;

	@Column(name="cobrado")
	private byte cobrado;

	@Column(name="codigo")
	private String codigo;

	@Column(name="fechaCobro")
	@Temporal(TemporalType.DATE)
	private Date fechaCobro;

	@Column(name="fechaEmision")
	@Temporal(TemporalType.DATE)
	private Date fechaEmision;

	@Column(name="folio")
	private int folio;

	//bi-directional many-to-one association to Caja
	@ManyToOne
	@JoinColumn(name="caja")
	private Caja cajaRegistro;

	//bi-directional many-to-one association to Caja
	@ManyToOne
	@JoinColumn(name="cajaCobro")
	private Caja cajaCobro;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="producto")
	private Producto productoId;

	//bi-directional many-to-one association to Tipovoucher
	@ManyToOne
	@JoinColumn(name="tipoVoucher")
	private Tipovoucher tipovoucherId;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idBeneficiario")
	private Usuario usuarioBeneficiario;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idBeneficiarioCobra")
	private Usuario usuarioBeneficiarioCobra;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idCajera")
	private Usuario usuarioCajera;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idCajeraCobra")
	private Usuario usuarioCajeraCobra;

	public Voucher() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getCobrado() {
		return this.cobrado;
	}

	public void setCobrado(byte cobrado) {
		this.cobrado = cobrado;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getFechaCobro() {
		return this.fechaCobro;
	}

	public void setFechaCobro(Date fechaCobro) {
		this.fechaCobro = fechaCobro;
	}

	public Date getFechaEmision() {
		return this.fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public int getFolio() {
		return this.folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public Caja getCajaRegistro() {
		return this.cajaRegistro;
	}

	public void setCajaRegistro(Caja cajaRegistro) {
		this.cajaRegistro = cajaRegistro;
	}

	public Caja getCajaCobro() {
		return this.cajaCobro;
	}

	public void setCajaCobro(Caja cajaCobro) {
		this.cajaCobro = cajaCobro;
	}

	public Producto getProductoId() {
		return this.productoId;
	}

	public void setProductoId(Producto productoId) {
		this.productoId = productoId;
	}

	public Tipovoucher getTipovoucherId() {
		return this.tipovoucherId;
	}

	public void setTipovoucherId(Tipovoucher tipovoucherId) {
		this.tipovoucherId = tipovoucherId;
	}

	public Usuario getUsuarioBeneficiario() {
		return this.usuarioBeneficiario;
	}

	public void setUsuarioBeneficiario(Usuario usuarioBeneficiario) {
		this.usuarioBeneficiario = usuarioBeneficiario;
	}

	public Usuario getUsuarioBeneficiarioCobra() {
		return this.usuarioBeneficiarioCobra;
	}

	public void setUsuarioBeneficiarioCobra(Usuario usuarioBeneficiarioCobra) {
		this.usuarioBeneficiarioCobra = usuarioBeneficiarioCobra;
	}

	public Usuario getUsuarioCajera() {
		return this.usuarioCajera;
	}

	public void setUsuarioCajera(Usuario usuarioCajera) {
		this.usuarioCajera = usuarioCajera;
	}

	public Usuario getUsuarioCajeraCobra() {
		return this.usuarioCajeraCobra;
	}

	public void setUsuarioCajeraCobra(Usuario usuarioCajeraCobra) {
		this.usuarioCajeraCobra = usuarioCajeraCobra;
	}

}