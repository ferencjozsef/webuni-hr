package hu.webuni.hr.ferencjozsef.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.ferencjozsef.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee>{

	// Adott fizetéstól nagyobb alkalmazottak
	List<Employee> findBySalaryGreaterThan(Integer minSalary);
	
	// Adott beosztású alkalmazottak
	List<Employee> findByPositionName(String position);
	
	// Adott stringgel kezdődő nevű alkalmazottak, kis-/negybetű ne számítoson
	List<Employee> findByNameStartingWithIgnoreCase(String name);
	
	// Két adott dátum között belépő alkalmazottak
	List<Employee> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);
	
	// Visszaadja a céghez tartozó alkamazottakat
	List<Employee> findByCompanyId(long id);
	
//	@EntityGraph("Employee.full")
//	@Query("SELECT e FROM Employee e")
//	public List<Employee> findAll();
}
