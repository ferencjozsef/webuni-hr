package hu.webuni.hr.ferencjozsef.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Position {

	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private Education education;

	@OneToMany(mappedBy = "position")
	private List<Employee> employees;
	
	public Position() {
		
	}

	public Position(String name, Education education) {
		super();
		this.name = name;
		this.education = education;
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

	public Education getEducation() {
		return education;
	}

	public void setEducation(Education education) {
		this.education = education;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Position [id=");
		builder.append(id);
		builder.append(", positionName=");
		builder.append(name);
		builder.append(", education=");
		builder.append(education);
		builder.append("]");
		return builder.toString();
	}
	
}
