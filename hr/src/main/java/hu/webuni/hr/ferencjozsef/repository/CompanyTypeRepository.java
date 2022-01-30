package hu.webuni.hr.ferencjozsef.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.ferencjozsef.model.CompanyType;

public interface CompanyTypeRepository extends JpaRepository<CompanyType, Long>  {

	//Page<CompanyType> findAll(Pageable pageable);
	
	@Query("SELECT ct FROM CompanyType ct")
	List<CompanyType> findAll(Sort sort);

}
