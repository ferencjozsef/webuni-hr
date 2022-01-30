package hu.webuni.hr.ferencjozsef.dto;

import java.util.ArrayList;
import java.util.List;

public class CompanyDto {
	
	private Long id;
	private int registrationNumber;
	private String name;
	private String address;
	private CompanyTypeDto companyType;
	List<EmployeeDto> employees = new ArrayList<>();

	public CompanyDto() {
	}

	public CompanyDto(Long id, int registrationNumber, String name, String address, CompanyTypeDto companyType,
			List<EmployeeDto> employees) {
		super();
		this.id = id;
		this.registrationNumber = registrationNumber;
		this.name = name;
		this.address = address;
		this.companyType = companyType;
		this.employees = employees;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(int registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public CompanyTypeDto getCompanyType() {
		return companyType;
	}

	public void setCompanyType(CompanyTypeDto companyType) {
		this.companyType = companyType;
	}

	public List<EmployeeDto> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeDto> employees) {
		this.employees = employees;
	}
}
