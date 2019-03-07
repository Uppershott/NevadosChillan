package com.nevados.chillan.controller;

import java.util.List;

//import java.util.LinkedList;
//import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;


import com.nevados.chillan.domain.*;
import com.nevados.chillan.service.CajaService;
import com.nevados.chillan.service.SucursalService;


@Controller
@Scope("session")
public class WebController {
	
	@Autowired
	CajaService cajaService;
	
	@Autowired
	SucursalService sucursalService;
	
	@GetMapping("/")
	public String index(Model model, HttpSession session)
	{
		return volverAInicio(model, session);
	}
	
	@GetMapping("/index.html")
	public String home(Model model, HttpSession session)
	{
		model.addAttribute("caja", new Caja());
		session.setAttribute("caja", null);
		
		List <Sucursal> sucursales = sucursalService.findAll();
		System.out.println("Lista de sucursales creada exitosamente.");
		model.addAttribute("sucursales", sucursales);
		System.out.println("Agregado atributo sucursales a model.");
		
		return "index.html";
	}
	
	/*@GetMapping("/voucher.html")
	public String backHome(Model model, HttpSession session)
	{
		return "voucher";
	}*/
	String volverAInicio(Model model, HttpSession session)
	{
		model.addAttribute("caja", new Caja());
		session.setAttribute("caja", null);
		return "login";
	}
	
}
