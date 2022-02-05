package hu.webuni.hr.ferencjozsef.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.jpa.domain.Specification;

import hu.webuni.hr.ferencjozsef.model.Company_;
import hu.webuni.hr.ferencjozsef.model.Employee;
import hu.webuni.hr.ferencjozsef.model.Employee_;
import hu.webuni.hr.ferencjozsef.model.Position_;

public class EmployeeSpecification {
	
	// Id: pontos egyezés
	public static Specification<Employee> hasId (long id) {
		return (root, cq, cb) -> cb.equal(root.get(Employee_.id), id);
	}

	// Név: elég a név elejánek egyeznie, case-insensitive módon
	public static Specification<Employee> hasName(String name) {
		return (root, cq, cb) -> cb.like(cb.upper(root.get(Employee_.name)), name.toUpperCase() + "%");
	}
	
	// Beosztás: a beosztás nevének pontos egyezése szükséges
	public static Specification<Employee> hasPositionName(String name) {
		return (root, cq, cb) -> cb.equal(root.get(Employee_.position).get(Position_.name), name);
	}
	
	// Fizetés: a megadott érték +/- 5 százalék
	public static Specification<Employee> hasSalary(int salary) {
		int minSalary = (int) (salary - (salary * 0.05));
		int maxSalary = (int) (salary + (salary * 0.05));
		return (root, cq, cb) -> cb.between(root.get(Employee_.salary), minSalary, maxSalary );
	}

	
	// Belépés dátuma: a megadott napon történjen a belépés, az idő nem számít
	public static Specification<Employee> hasEntryDate(LocalDateTime entryDate) {
		LocalDateTime startOfDay = LocalDateTime.of(entryDate.toLocalDate(), LocalTime.of(0, 0));
		return (root, cq, cb) -> cb.between(root.get(Employee_.startDate), startOfDay, startOfDay.plusDays(1));
	}

	
	// Company: a megadott cég nevének eleje egyezen, case-insensitive módon
	public static Specification<Employee> hasCompanyName(String name) {
		return (root, cq, cb) -> cb.like(cb.upper(root.get(Employee_.company).get(Company_.name)), name.toUpperCase() + "%");
	}

}
