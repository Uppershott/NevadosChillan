package com.nevados.chillan.controller;

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

import com.nevados.chillan.domain.Caja;
import com.nevados.chillan.domain.Producto;
import com.nevados.chillan.domain.Sucursal;
import com.nevados.chillan.domain.Tipovoucher;
import com.nevados.chillan.domain.Usuario;
import com.nevados.chillan.service.CajaService;
import com.nevados.chillan.service.ProductoService;
import com.nevados.chillan.service.SucursalService;
import com.nevados.chillan.service.TipoVoucherService;

@Controller
@Scope("session")
public class AttributeController {
	//Inicialización de Servicios
	@Autowired
	ProductoService productoService;
	
	@Autowired
	SucursalService sucursalService;
	
	@Autowired
	TipoVoucherService tipoVoucherService;
	
	@Autowired
	CajaService cajaService;
	
	//Inicialización de Listas
	List<Producto> misProductos;
	List<Sucursal> misSucursales;
	List<Tipovoucher> misTipoVoucher;
	List<Caja> misCajas;
	
	//-----------------------------------------------------------------------------------------------------------------
	@GetMapping(value="/addAttribute.html")
	public String myAttributeList(Model model, HttpSession session)
	{
		return "addAttribute";
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@GetMapping(value="/caja")
	public String caja(Model model, HttpSession session) {
		model.addAttribute("caja", new Caja());
		
		/*List <Sucursal> sucursales = sucursalService.findAll();
		System.out.println("Lista de sucursales creada exitosamente.");
		model.addAttribute("sucursales", sucursales);
		System.out.println("Agregado atributo sucursales a model.");*/
		cargarListas(model,session);
		
		return "index";
	}
	
	@RequestMapping(value="/addCaja", method=RequestMethod.POST)
	public String boxRegister(@Valid @ModelAttribute("caja") Caja caja, BindingResult result, Model model, HttpSession session)
	{
		System.out.println(caja.toString());
		System.out.println("Registrando caja...");
		System.out.println("Correo: "+caja.getCorreo());
		System.out.println("Password: "+caja.getPassword());
		System.out.println("Sucursal: "+caja.getSucursal().getNombre());
		
		if (cajaService.findByCorreo(caja.getCorreo())!= null)
		{
			System.out.println("Error: esta caja ya existe.");
			result.addError(new FieldError("caja", "correo", "Ya existe una cuenta asociada a este correo"));
		}else {
			byte a=1;
			//Lo que sale a continuación corrigió el NullPointerException
			Caja ca = new Caja();
			ca.setCorreo(caja.getCorreo());
			ca.setPassword(caja.getPassword());
			ca.setSucursal(sucursalService.findById(caja.getSucursal().getId()));
			ca.setVisible(a);
			System.out.println("Se setearon correctamente todos los atributos de caja");
			
			cajaService.save(ca);
			System.out.println("Caja registrada exitosamente.");
		}
		if (result.hasErrors()) return "index";
		
		
		System.out.println("return /addAttribute");
		cargarListas(model, session);
		return "addAttribute";
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@GetMapping(value="/product")
	public String product(Model model, HttpSession session) {
		model.addAttribute("producto", new Producto());
		System.out.println("Se agrego exitosamente producto a model en /product");
		return "product";
	}
	
	@RequestMapping(value="/addProducto", method = RequestMethod.POST)
	public String addProduct(@Valid @ModelAttribute("producto") Producto producto, BindingResult result,Model model, HttpSession session) {
		System.out.println("Agregando producto...");
		if(productoService.findById(producto.getId())!=null) {
			System.out.println("Error: Ya existe un producto con ese código.");
			result.addError(new FieldError("producto", "id", "Ya existe un producto asociado a este id."));
		}else if(productoService.findByNombre(producto.getNombre())!=null) {
			System.out.println("Error: Ya existe un producto con ese nombre.");
			result.addError(new FieldError("producto", "nombre", "Ya existe un producto asociado a este nombre."));
		}else {
			productoService.save(producto);
			System.out.println("Producto registrado con éxito!");
			cargarListas(model, session);
			System.out.println("Las listas se cargaron exitosamente");
			System.out.println(model.containsAttribute("myProductoList"));
		}
		if(result.hasErrors())return "product";
		cargarListas(model,session);
		return "addAttribute";
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@GetMapping(value="/sucursal")
	public String sucursal(Model model, HttpSession session) {
		model.addAttribute("sucursal", new Sucursal());
		System.out.println("Se agrego exitosamente sucursal a model en /sucursal");
		return "sucursal";
	}
	
	@RequestMapping(value="/addSucursal", method = RequestMethod.POST)
	public String addSucursal(@Valid @ModelAttribute("sucursal") Sucursal sucursal, BindingResult result, Model model, HttpSession session) {
		System.out.println("Agregando sucursal...");
		if(sucursalService.findByNombre(sucursal.getNombre())!=null) {
			System.out.println("Error: Ya existe una sucursal con ese nombre.");
			result.addError(new FieldError("sucursal","nombre","Ya existe una sucursal asociada a este id."));
		/*}else if(sucursalService.findByCorreo(sucursal.getCorreo())!=null){
			System.out.println("Error: Ya existe una sucursal con ese correo.");
			result.addError(new FieldError("sucursal","correo","Ya existe una sucursal asociada a este correo."));*/
		}else {
			sucursalService.save(sucursal);
			System.out.println("Sucursal registrada con éxito!");
		}
		if(result.hasErrors())return "sucursal";
		cargarListas(model,session);
		return "addAttribute";
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@GetMapping(value="/tipoVoucher")
	public String tipoVoucher(Model model, HttpSession session) {
		model.addAttribute("tipoVoucher", new Tipovoucher());
		System.out.println("Se agrego exitosamente tipoVoucher a model en /tipoVoucher");
		return "tipoVoucher";
	}
	
	@RequestMapping(value="/addTipoVoucher", method = RequestMethod.POST)
	public String addTipoVoucher(@Valid @ModelAttribute("tipoVoucher") Tipovoucher tipoVoucher, BindingResult result, Model model, HttpSession session) {
		System.out.println("Agregando tipo de voucher...");
		if(tipoVoucherService.findByNombre(tipoVoucher.getNombre())!=null) {
			System.out.println("Error: Ya existe un tipo de voucher con ese nombre.");
			result.addError(new FieldError("sucursal","nombre","Ya existe un tipo de voucher asociado a este nombre."));
		}else {
			tipoVoucherService.save(tipoVoucher);
			System.out.println("Tipo de voucher registrado con éxito!");
		}
		if(result.hasErrors())return "tipoVoucher";
		cargarListas(model,session);
		return "addAttribute";
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@GetMapping(value="convenio")
	public String convenio(Model model, HttpSession session) {
		model.addAttribute("convenio", new Usuario());
		return "convenio";
	}
	
	@RequestMapping(value="addConvenio", method=RequestMethod.POST)
	public String addConvenio(@Valid @ModelAttribute("convenio") Usuario convenio, Model model, HttpSession session, BindingResult result) {
		
		return "addAttribute";
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	public void cargarListas(Model model, HttpSession session) {
		System.out.println("Comenzando carga de lista de cajas");
		//Lista de Cajas
		misCajas = cajaService.findAll();
		System.out.println("Size: "+misCajas.size()+" Se cargó bien el service Caja");
		for(int i=0;i<misCajas.size();i++) {
			misCajas.get(i).setSucursal(sucursalService.findById(misCajas.get(i).getSucursal().getId()));
		}
		model.addAttribute("myCajaList", misCajas);
		System.out.println(misCajas.toString()+" Pasó el model misCajas");
		
		System.out.println("Comenzando carga de lista Productos...");
		//Lista de Productos
		misProductos = productoService.findAll();
		System.out.println("Size: " + misProductos.size()+" Se cargó bien el service Producto...");
		model.addAttribute("myProductoList", misProductos);
		System.out.println(misProductos.toString()+" Pasó el model misProductos");
		
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
		
		System.out.println("Comenzando carga de lista Tipos Vouchers...");
		//Lista de TipoVoucher
		misTipoVoucher = tipoVoucherService.findAll();
		System.out.println("Size: " + misTipoVoucher.size()+" Se cargó bien el service Tipo Voucher...");
		model.addAttribute("myTipoVoucherList", misTipoVoucher);
		System.out.println(misTipoVoucher.toString()+" Pasó el model misTipoVoucher");
	}
	
}
