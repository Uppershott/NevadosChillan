package com.nevados.chillan.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nevados.chillan.domain.*;
import com.nevados.chillan.service.*;

@Controller
@Scope("session")
public class VoucherController {
	//Declaración de Servicios
	@Autowired
	VoucherService voucherService;
	
	@Autowired
	TipoVoucherService tipoVoucherService;
	
	@Autowired
	ProductoService productoService;
	
	@Autowired
	UsuarioService usuarioService;

	//Declaración de Listas
	List <Voucher> misVouchers;
	List<Producto> misProductos;
	List<Tipovoucher> misTipoVoucher;
	List<Usuario> misUsuarios;
		
	/*@GetMapping(value="/voucher")
	public String voucher(Model model, HttpSession session) {
		model.addAttribute("voucher", new Voucher());
		return "voucher";
	}*/
	
	//-----------------------------------------------------------------------------------------------------------------
	@GetMapping(value="/voucher")
	public String voucher(Model model, HttpSession session) {	
		
		
		System.out.println("return VoucherController/voucher");
		return "voucher";
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@RequestMapping(value="/generateVoucher", method=RequestMethod.POST)
	public String generateVoucher(@Valid @ModelAttribute("voucher") Voucher voucher, Model model, HttpSession session) {
		System.out.println("Iniciando proceso VoucherController/generateVoucher...");
		/*
		if(voucher.getProductoId().getId()==0) {
			System.out.println("Error: Se debe ingresar un producto de voucher...");
			result.addError(new FieldError("voucher","productoId","Debe seleccionar un producto"));
		}else if(voucher.getTipovoucherId().getId()==0) {
			System.out.println("Error: Se debe ingresar un tipo de voucher...");
			result.addError(new FieldError("voucher","tipoVoucherId","Debe seleccionar un tipo de voucher"));
		}else if(voucher.getUsuarioBeneficiario().getId()==0) {
			System.out.println("Error: Se debe ingresar un usuario beneficiario");
			result.addError(new FieldError("voucher","usuarioBeneficiario","Debe seleccionar un usuario beneficiario"));
		}else {
		*/
			String codigo = generarCodigo(model,session);
			System.out.println("Código generado: "+codigo);
			
			misVouchers = voucherService.findAll();
			System.out.println("Se han agregado todos los vouchers satisfatoriamente");
			
			if(misVouchers.size()>0) {
				for(int i =0; i<misVouchers.size();i++) {
					if(codigo.equals(misVouchers.get(i).getCodigo())) {
						System.out.println("Se ha detectado un código igual, procediendo a cambiar...");
						System.out.println("Código antiguo: "+codigo);
						
						codigo = generarCodigo(model,session);
						System.out.println("Código nuevo: "+codigo);
					}
					System.out.println("El código generado ["+codigo+"] es distinto del existente ["
							+misVouchers.get(i).getCodigo()+"]");
				}
			}
			voucher.setCodigo(codigo);
			
			//Producto producto = productoService.findById(voucher.getProductoId().getId());
			int folio;
			String folioStr = "2019"+voucher.getProductoId().getId()+voucher.getId();
			folio = Integer.parseInt(folioStr);
			voucher.setFolio(folio);
			
			voucher.setProductoId(productoService.findById(voucher.getProductoId().getId()));
			System.out.println("voucher.ProductoId: "+voucher.getProductoId().getId()+" Seteado exitosamente");
			
			voucher.setTipovoucherId(tipoVoucherService.findById(voucher.getTipovoucherId().getId()));
			System.out.println("voucher.TipoVoucherId: "+voucher.getTipovoucherId().getId()+" Seteado exitosamente");
			
			voucher.setUsuarioBeneficiario(usuarioService.findById(voucher.getUsuarioBeneficiario().getId()));
			System.out.println("voucher.UsuarioBeneficiario: "+voucher.getUsuarioBeneficiario().getId()+" Seteado exitosamente");
			
			Caja ca = (Caja) session.getAttribute("caja");
			voucher.setCajaRegistro(ca);
			System.out.println("Seteada exitosamente caja de registro ["+voucher.getCajaRegistro().getCorreo()+"] en voucher");
			
			Usuario us = (Usuario) session.getAttribute("usuario");
			voucher.setUsuarioCajera(us);
			System.out.println("Seteado exitosamente usuario ["+voucher.getUsuarioCajera().getNombre()
					+"] en caja ["+voucher.getCajaRegistro().getCorreo()+"]");
			
			java.util.Date d = new java.util.Date(); 
			java.sql.Date date2 = new java.sql.Date(d.getTime());
			System.out.println("Fecha util "+d+" parseada a sql: "+date2);
			
			voucher.setFechaEmision(date2);
			System.out.println("Seteada exitosamente fecha de emisión: "+voucher.getFechaEmision());
			
			voucher.setCobrado((byte)0);
			System.out.println("Seteado exitosamente valore de cobrado a: "+voucher.getCobrado());
		//}
		//if(result.hasErrors()) return "voucher";
		
		session.setAttribute("voucher", voucher);
		
		cargarListas(model,session);
		
		return "voucher"; 
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@GetMapping(value="saveVoucher")
	public String saveVoucher(Model model, HttpSession session) {
		Voucher voucher = (Voucher) session.getAttribute("voucher");
		System.out.println("Obteniendo voucher, codigo ["+voucher.getCodigo()+"] desde session");
		
		voucherService.save(voucher);
		System.out.println("Guardado Voucher exitosamente");
		
		model.addAttribute("voucher", new Voucher());
		model.addAttribute("productoId", new Producto());
		model.addAttribute("tipovoucherId", new Tipovoucher());
		System.out.println("Agregados los 3 objetos al model");
		
		cargarListas(model,session);
		System.out.println("Listas exitosamente cargadas");
		
		System.out.println("return voucher");
		return "voucher";
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@RequestMapping(value="estadoVoucher", method=RequestMethod.POST)
	public String estadoVoucher(@Valid @ModelAttribute("voucher") Voucher voucher, Model model, HttpSession session,
			BindingResult result) {
		Voucher vou = voucherService.findByCodigo(voucher.getCodigo());
		if(vou==null) {
			System.out.println("Error: No existe un voucher asociado al código ["+voucher.getCodigo()+"]");
			result.addError(new FieldError("voucher","codigo","El voucher asociado a este código no existe, ingrese un código válido"));
		}else if(vou.getCobrado()==1){
			System.out.println("Aviso: El voucher asociado al código ["+vou.getCodigo()+"] ya ha sido cobrado");
			result.addError(new FieldError("voucher","codigo","El voucher ha sido cobrado"));
		}
		if(result.hasErrors()) return "voucher";
		return "voucher";
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	public String generarCodigo(Model model, HttpSession session) {
		char[] elementos={'0','1','2','3','4','5','6','7','8','9' ,'a',
				'b','c','d','e','f','g','h','i','j','k','l','m','n','o',
				'p','q','r','s','t','u','v','w','x','y','z'};

		char[] conjunto = new char[10];
		String pass;
		
		for(int i=0;i<10;i++){
			int el = (int)(Math.random()*36);
			conjunto[i] = (char)elementos[el];
		}
		
		pass = new String(conjunto);
		return pass;
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	public void cargarListas(Model model, HttpSession session) {
		System.out.println("Comenzando carga de lista de cajas");
		
		//--------------------------------------------------------------------
		System.out.println("Comenzando carga de lista Productos...");
		//Lista de Productos
		misProductos = productoService.findAll();
		System.out.println("Size: " + misProductos.size()+" Se cargó bien el service Producto...");
		model.addAttribute("myProductoList", misProductos);
		System.out.println(misProductos.toString()+" Pasó el model misProductos");
		
		//--------------------------------------------------------------------
		System.out.println("Comenzando carga de lista Tipos Vouchers...");
		//Lista de TipoVoucher
		misTipoVoucher = tipoVoucherService.findAll();
		System.out.println("Size: " + misTipoVoucher.size()+" Se cargó bien el service Tipo Voucher...");
		model.addAttribute("myTipoVoucherList", misTipoVoucher);
		System.out.println(misTipoVoucher.toString()+" Pasó el model misTipoVoucher");
		
		
		//--------------------------------------------------------------------
		System.out.println("Comenzando carga de lista Usuarios Beneficiarios...");
		//Lista de Usuarios Beneficiarios
		misUsuarios = usuarioService.findAll();
		System.out.println("Tamaño lista misUsuarios: "+misUsuarios.size());
		List <Usuario> usua = new ArrayList<Usuario>();
		for(int i=0; i<misUsuarios.size();i++) {
			System.out.println("Usuario: "+misUsuarios.get(i).getNombre()+"Rol: "+misUsuarios.get(i).getRol());
			if(misUsuarios.get(i).getRol().getId()==3) {
				usua.add(misUsuarios.get(i));
				System.out.println("Agregado a usua: "+misUsuarios.get(i).getNombre());
			}else {
				System.out.println("Removiendo usuario id: "+misUsuarios.get(i).getId());
				misUsuarios.remove(i);
			}
		}
		System.out.println("Size: "+usua.size()+" Se cargó bien el service Usuario");
		model.addAttribute("myUsuarioList", usua);
		System.out.println(misUsuarios.toString()+"Pasó el model misUsuarios");
	}
	
	/* Código del folio en voucher.html
	Producto producto = productoService.findById(voucher.producto.getId());
	int folio;
	String aux = ""+;
	String folioStr = "2019"+producto.getId()+voucher.getId();
	folio = Integer.parseInt(folioStr);
		 
	 */
}
