package hu.webuni.hr.ferencjozsef.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.ferencjozsef.model.AverageSalaryByPosition;
import hu.webuni.hr.ferencjozsef.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	// Azon cégek listája, melyeknek van egy adott limitnél nagyobb fizetésű alkalmazotja
	//@Query("SELECT DISTINCT c FROM Company c JOIN c.employees e WHERE e.salary > :minSalary")
	@Query("SELECT c FROM Company c WHERE EXISTS (SELECT e FROM c.employees e WHERE e.salary > ?1)")
	List<Company> getCompaniesExistingMinSalaryEmployee(int minSalary);
	
	// azon cégek listája, melyeknél az alkalmazottak száma meghalad egy adott limitet
	// @Query("SELECT c FROM Company c WHERE SIZE(c.employees) > :minEmployeeCount")
	@Query("SELECT c FROM Company c WHERE ?1 < (SELECT count(*) FROM c.employees e)")
	List<Company> getCompaniesMinLimitHaveEmployee(long minLimit);
	//List<Company> getCompaniesMinLimitHaveEmployee(long minLimit, Pageable pageable);
	
	//@Query("SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.employees")
	//@EntityGraph(attributePaths = {"employees", "companyType"})
	@EntityGraph("Company.full")
	@Query("SELECT c FROM Company c")
	public List<Company> findAllWithEmployees();

	//Kérdezd le egy id-val adott cég alkalmazottainak átlagfizetését, beosztás szerint csoportosítva,
	//az átlagfizetések szerint csökkenő sorrendben
	@Query("SELECT e.position.name AS position, avg(e.salary) AS averageSalary "
			+ "FROM Company c "
			+ "INNER JOIN c.employees e "
			+ "WHERE c.id = : companyId "
			+ "GROUP BY e.position.name "
			+ "ORDER BY avg(e.salary) DESC")
	public List<AverageSalaryByPosition> findAverageSalariesByPosition(long companyId);

}
