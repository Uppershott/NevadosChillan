package com.nevados.chillan.controller;

import java.util.ArrayList;
import java.util.List;

//import java.util.LinkedList;
//import java.util.List;

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
import com.nevados.chillan.service.CajaService;
import com.nevados.chillan.service.ProductoService;
import com.nevados.chillan.service.SucursalService;
import com.nevados.chillan.service.TipoVoucherService;
import com.nevados.chillan.service.UsuarioService;

@Controller
@Scope("session")
public class AuthenticationController {
	//Declaración de Servicios
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ProductoService productoService;
	
	@Autowired
	SucursalService sucursalService;
	
	@Autowired
	TipoVoucherService tipoVoucherService;
	
	@Autowired
	CajaService cajaService;
	
	//Declaración de Listas
	List<Producto> misProductos;
	List<Sucursal> misSucursales;
	List<Tipovoucher> misTipoVoucher;
	List<Caja> misCajas;
	List<Usuario> misUsuarios;
	
	//-----------------------------------------------------------------------------------------------------------------
	@GetMapping("/login.html")
	public String login(Model model, HttpSession session)
	{
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("caja", new Caja());
		session.setAttribute("usuario", new Usuario());
		session.setAttribute("caja", new Caja());
		return "login";
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@GetMapping("/logout")
	public String logout(Model model, HttpSession session)
	{
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("caja", new Caja());
		session.setAttribute("usuario", new Usuario());
		session.setAttribute("caja", new Caja());
		return "login";
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@RequestMapping(value="/authenticate", method=RequestMethod.POST)
	public String authenticate(@Valid @ModelAttribute("caja") Caja caja, BindingResult result, Model model, HttpSession session)
	{
		System.out.println("Iniciando sesión...");
		
		System.out.println("Iniciando proceso de verificaciones if...");
		if (cajaService.findByCorreo(caja.getCorreo()) == null)
		{
			System.out.println("Error: esta cuenta no existe.");
			result.addError(new FieldError("caja", "correo", "No existe una cuenta asociada a este correo"));
		}
		else if (!caja.getPassword().equals(cajaService.findByCorreo(caja.getCorreo()).getPassword()))
		{
			System.out.println("Error: Contraseña incorrecta.");
			result.addError(new FieldError("caja", "password", "Contraseña incorrecta"));
		}
		else if(caja.getCorreo().equals("admin@nevadosdechillan.com")){
			System.out.println("Iniciando login de Administrador..");
			Caja ca = cajaService.findByCorreo(caja.getCorreo());
			
			session.setAttribute("caja", ca);
			System.out.println("Seteado exitosamente caja en session de Admin");
			
			List<Usuario> usuario = usuarioService.findAll();
			System.out.println("Lista usuario rellenada");
			
			for(int i=0; i<usuario.size(); i++) {
				if(usuario.get(i).getCaja().getId() == ca.getId()) {
					session.setAttribute("usuario", usuario.get(i));
					System.out.println("Seteado usuario: "+usuario.get(i).getNombre());
					break;
				}else {
					System.out.println("No es compatible el id de usuario: "+usuario.get(i).getCaja().getId()
							+" con el id de caja: "+ca.getId());
				}
			}
			
			//Cambio a cargarListas para filtrar por rol
			//Admin carga a usuarios rol 1 en caja y rol 2 en convenio
			//podría cargar rol 3 Beneficiarios para visualizar a las personas que han comprado voucher o cobrado voucher
			
			cargarListas(model,session);	
			System.out.println("Listas cargadas exitosamente");
			
			System.out.println("return addAttribute");
			return "addAttribute";
		}else {
			System.out.println("Login exitoso.");
			Caja ca = cajaService.findByCorreo(caja.getCorreo());
			
			if(ca.getVisible()==0){
				System.out.println("Error: esta cuenta no se encuentra habilitada.");
				result.addError(new FieldError("caja","visible","Esta caja no se encuentra habilitada"));
			}else {
				session.setAttribute("caja", ca);
				model.addAttribute("usuario", new Usuario());
				session.setAttribute("usuario", new Usuario());
				System.out.println("Agregado nuevo usuario al model");
			}
		}
		if (result.hasErrors()) return "login";
		
		System.out.println("Comenzando carga de lista de Usuarios");
		//Lista de Usuarios
		misUsuarios = usuarioService.findAll();
		for(int i=0; i<misUsuarios.size();i++) {
			if(misUsuarios.get(i).getRol().getId() !=1) {
				System.out.println("Removiendo usuario id: "+misUsuarios.get(i).getId());
				misUsuarios.remove(i);
			}else {
				System.out.println("Usuario no eliminado de la lista: "+misUsuarios.get(i).getId());
			}
			
		}
		System.out.println("Size: "+misUsuarios.size()+" Se cargó bien el service Usuario");
		
		model.addAttribute("myUsuarioList", misUsuarios);
		System.out.println("Pasó el model misUsuarios");
		
		System.out.println("return indexRegister /register");
		return "indexRegister";
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(@Valid @ModelAttribute("usuario") Usuario usuario, Model model, BindingResult result, HttpSession session) {
		System.out.println("Comienza registro usuario..");
		if(usuario.getRut() == null) {
			System.out.println("Error: El rut se encuentra nulo");
			result.addError(new FieldError("usuario","rut","Debe ingresar un valor válido para rut."));
		}else if(usuario.getNombre() == null){
			System.out.println("Errro: El nombre se encuentra nulo");
			result.addError(new FieldError("usuario","nombre","Debe ingresar un valor válido para nombre"));
		}else if(usuario.getApellido() == null){
			System.out.println("Error: El apellido se encuentra nulo");
			result.addError(new FieldError("usuario","apellido","Debe ingresar un valor válido para apellido"));
		}else if(usuario.getTelefono() == 0){
			System.out.println("Error: El telefono es 0");
			result.addError(new FieldError("usuario","telefono","Debe ingresar un valor válido para telefono"));
		}else {
			Rol rol = new Rol();
			System.out.println("Seteando rol.id");
			rol.setId(1);
			usuario.setRol(rol);
			System.out.println("Rol seteado exitosamente: "+usuario.getRol().getId());
			
			java.util.Date d = new java.util.Date(); 
			java.sql.Date date2 = new java.sql.Date(d.getTime());
			System.out.println("Fecha util "+d+" parseada a sql: "+date2);
			
			usuario.setFechaRegistro(date2);
			System.out.println("Seteada fecha en usuario..");
			
			Caja aux = (Caja) session.getAttribute("caja");
			Caja caja = cajaService.findByCorreo(aux.getCorreo());
			System.out.println("Id de Caja en caja por session.getAttribute: "+caja.getId());
			
			usuario.setCaja(caja);
			System.out.println("Id de Caja en Usuario: "+usuario.getCaja().getId());
			
			session.setAttribute("caja", caja);
			System.out.println("Seteada exitosamente caja en session: "+caja.getCorreo());
			
			usuarioService.save(usuario);
			System.out.println("Usuario registrado exitosamente con usuarioService.save");
		}
		if(result.hasErrors()) return "indexRegister";
		
		session.setAttribute("usuario", usuario);
		System.out.println("Seteado exitosamente usuario en session: "+usuario.getNombre());
		
		model.addAttribute("voucher", new Voucher());
		model.addAttribute("productoId", new Producto());
		model.addAttribute("tipovoucherId", new Tipovoucher());
		
		System.out.println("Comenzando carga de lista de Usuarios");
		
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
		
		cargarListas(model,session);
		System.out.println("Carga de listas realizado con éxito");
		
		System.out.println("return voucher");		
		return "voucher";
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	String volverAInicio(Model model, HttpSession session)
	{
		model.addAttribute("usuario", new Usuario());
		session.setAttribute("usuario", null);
		return "index";
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	public void cargarListas(Model model, HttpSession session) {
		System.out.println("Comenzando carga de lista de cajas");
		//Lista de Cajas
		misCajas = cajaService.findAll();
		for(int i=0; i<misCajas.size(); i++) {
			if(misCajas.get(i).getCorreo().equals("admin@nevadosdechillan.com")) {
				System.out.println("Removiendo administrador de lista cajas de id: "+ misCajas.get(i).getId());
				misCajas.remove(i);
				break;
			}
		}		
		for(int i=0;i<misCajas.size();i++) {
			misCajas.get(i).setSucursal(sucursalService.findById(misCajas.get(i).getSucursal().getId()));
			System.out.println(misCajas.get(i)+" | "+misCajas.get(i).getCorreo()+" | "+misCajas.get(i).getSucursal().getNombre());
		}
		model.addAttribute("myCajaList", misCajas);
		System.out.println(misCajas.toString()+" Pasó el model misCajas");
		
		
		//--------------------------------------------------------------------
		System.out.println("Comenzando carga de lista Productos...");
		//Lista de Productos
		misProductos = productoService.findAll();
		System.out.println("Size: " + misProductos.size()+" Se cargó bien el service Producto...");
		model.addAttribute("myProductoList", misProductos);
		System.out.println(misProductos.toString()+" Pasó el model misProductos");
		
		
		//--------------------------------------------------------------------
		System.out.println("Comenzando carga de lista Sucursales...");
		//Lista de Sucursales
		misSucursales = sucursalService.findAll();
		for(int i=0; i<misSucursales.size();i++) {
			if(misSucursales.get(i).getNombre().equals("Admin")) {
				System.out.println("Removiendo sucursal Admin id: "+misSucursales.get(i).getId());
				misSucursales.remove(i);
				break;
			}
		}
		System.out.println("Size: " + misSucursales.size()+" Se cargó bien el service Sucursal...");
		model.addAttribute("mySucursalList", misSucursales);
		System.out.println(misSucursales.toString()+" Pasó el model misSucursales");
		
		
		//--------------------------------------------------------------------
		System.out.println("Comenzando carga de lista Tipos Vouchers...");
		//Lista de TipoVoucher
		misTipoVoucher = tipoVoucherService.findAll();
		System.out.println("Size: " + misTipoVoucher.size()+" Se cargó bien el service Tipo Voucher...");
		model.addAttribute("myTipoVoucherList", misTipoVoucher);
		System.out.println(misTipoVoucher.toString()+" Pasó el model misTipoVoucher");
		
		
		//--------------------------------------------------------------------
		/*
		System.out.println("Comenzando carga de lista de Usuarios");
		Lista de Usuarios
		misUsuarios = usuarioService.findAll();
		for(int i=0; i<misUsuarios.size();i++) {
			if(misUsuarios.get(i).getNombre().equals("Admin")) {
				System.out.println("Removiendo usuario Admin id: "+misUsuarios.get(i).getId());
				misUsuarios.remove(i);
				break;
			}
		}
		System.out.println("Size: "+misUsuarios.size()+" Se cargó bien el service Usuario");
		model.addAttribute("myUsuarioList", misUsuarios);
		System.out.println(misUsuarios.toString()+"Pasó el model misUsuarios");
		*/
	}
	
	@RequestMapping(value="cargarUsuario", method=RequestMethod.POST)
	public String cargarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, Model model, HttpSession session, BindingResult result) {
		System.out.println("id usuario del combobox: "+usuario.getId());
		Usuario us = new Usuario(); 
		us = usuarioService.findById(usuario.getId());
		
		java.util.Date d = new java.util.Date(); 
		java.sql.Date date2 = new java.sql.Date(d.getTime());
		System.out.println("Nombre: "+us.getNombre());
		System.out.println("Fecha vieja: "+us.getFechaRegistro());
		System.out.println("Fecha nueva: "+date2);
		
		us.setFechaRegistro(date2);
		System.out.println("Fecha registro de usuario us realizada exitosamente");
		
		session.setAttribute("usuario", us);
		System.out.println("Seteado exitosamente usuario en session: "+us.getNombre());
		
		model.addAttribute("voucher", new Voucher());
		model.addAttribute("productoId", new Producto());
		model.addAttribute("tipovoucherId", new Tipovoucher());
		
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
		
		cargarListas(model,session);
		
		System.out.println("return voucher");
		return "voucher";
	}
}
