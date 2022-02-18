package hu.webuni.hr.ferencjozsef.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	private String username;
	private String password;
	@ManyToOne
	private Employee boss;
	@OneToMany(mappedBy = "boss")
	private List<Employee> bossEmployees;
	@ManyToOne
	private Position position;
	private int salary;
	private LocalDateTime startDate;
	@ManyToOne
	private Company company;
	@OneToMany(mappedBy = "employee")
	private List<DayOff> dayOffs;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Employee getBoss() {
		return boss;
	}

	public void setBoss(Employee boss) {
		this.boss = boss;
	}

	public List<Employee> getBossEmployees() {
		return bossEmployees;
	}

	public void setBossEmployees(List<Employee> bossEmployees) {
		this.bossEmployees = bossEmployees;
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

	public List<DayOff> getDayOffs() {
		return dayOffs;
	}

	public void setDayOffs(List<DayOff> dayOffs) {
		this.dayOffs = dayOffs;
	}
	
	public void addDayOff(DayOff dayOff) {
		if (this.dayOffs == null) {
			this.dayOffs = new ArrayList<>();
		}
		
		this.dayOffs.add(dayOff);
		dayOff.setEmployee(this);
		
	}
}
