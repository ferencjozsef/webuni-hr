package hu.webuni.hr.ferencjozsef.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;

//@NamedEntityGraph(
//		name = "Employee.full",
//		attributeNodes = {
//				@NamedAttributeNode("position"),
//				@NamedAttributeNode("company")
//		}
//)
@Entity
public class Employee {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@ManyToOne
	private Position position;
	private int salary;
	private LocalDateTime startDate;
	@ManyToOne
	private Company company;

	public Employee() {

	}

	public Employee(Long id, String name, Position position, int salary, LocalDateTime startDate, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.startDate = startDate;
		this.company = company;
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

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
