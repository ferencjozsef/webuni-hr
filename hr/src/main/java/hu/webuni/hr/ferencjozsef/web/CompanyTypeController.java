package hu.webuni.hr.ferencjozsef.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.ferencjozsef.dto.CompanyTypeDto;
import hu.webuni.hr.ferencjozsef.mapper.CompanyTypeMapper;
import hu.webuni.hr.ferencjozsef.service.CompanyTypeService;

@RestController
@RequestMapping("/api/companytypes")
public class CompanyTypeController {

	@Autowired
	CompanyTypeService companyTypeService;

	@Autowired
	CompanyTypeMapper companyTypeMapper;

	// Az összes cég visszaadása
	@GetMapping
	public List<CompanyTypeDto> getAll() {
		return companyTypeMapper.companyTypesToDtos(companyTypeService.findAll());
	}


}
