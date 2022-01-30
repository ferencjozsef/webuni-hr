package hu.webuni.hr.ferencjozsef.dto;

import java.util.ArrayList;
import java.util.List;

public class CompanyTypeDto {

	private Long id;
	private String name;
	List<CompanyDto> companies  = new ArrayList<>();;

	public CompanyTypeDto() {
		
	}

	public CompanyTypeDto(Long id, String name, List<CompanyDto> companies) {
		super();
		this.id = id;
		this.name = name;
		this.companies = companies;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CompanyDto> getCompanies() {
		return companies;
	}

	public void setCompanies(List<CompanyDto> companies) {
		this.companies = companies;
	}

}