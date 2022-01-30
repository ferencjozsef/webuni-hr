package hu.webuni.hr.ferencjozsef.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;

@NamedEntityGraph(
		name = "Company.full",
		attributeNodes = {
				@NamedAttributeNode("employees"),
				@NamedAttributeNode("companyType")
		}
)
@Entity
public class Company {

	@Id
	@GeneratedValue
	private Long id;
	private int registrationNumber;
	private String name;
	private String address;
//	@Enumerated(EnumType.STRING)
	@ManyToOne
	private CompanyType companyType;
	@OneToMany(mappedBy = "company")
	private List<Employee> employees;

	public Company() {
	}

	public Company(Long id, int registrationNumber, String name, String address, CompanyType companyType,
			List<Employee> employees) {
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

	public CompanyType getCompanyType() {
		return companyType;
	}

	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public void addEmployee(Employee employee) {
		if (this.employees == null) {
			this.employees = new ArrayList<>();
		}
		this.employees.add(employee);
		employee.setCompany(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Company [id=");
		builder.append(id);
		builder.append(", registrationNumber=");
		builder.append(registrationNumber);
		builder.append(", name=");
		builder.append(name);
		builder.append(", address=");
		builder.append(address);
		builder.append(", companyType=");
		builder.append(companyType);
		builder.append("]");
		return builder.toString();
	}


}
