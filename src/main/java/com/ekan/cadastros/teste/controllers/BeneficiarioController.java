package com.ekan.cadastros.teste.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/v1/beneficiario")
public class BeneficiarioController {

	@GetMapping
	public String home() {
		return "Hello JWT";
	}
}
