
package hu.webuni.hr.ferencjozsef.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.ferencjozsef.model.CompanyType;
import hu.webuni.hr.ferencjozsef.repository.CompanyTypeRepository;

@Service
public class CompanyTypeService {

	
	@Autowired
	private CompanyTypeRepository companyTypeRepository;

	public CompanyType save(CompanyType companyType) {
		return companyTypeRepository.save(companyType);
	}

	public CompanyType update(CompanyType companyType) {
		if (!companyTypeRepository.existsById(companyType.getId())) {
			return null;
		}
		return companyTypeRepository.save(companyType);
	}

	public List<CompanyType> findAll() {
		return companyTypeRepository.findAll();
	}

	public Optional<CompanyType> findById(long id) {
		return companyTypeRepository.findById(id);
	}

	public void delete(long id) {
		companyTypeRepository.deleteById(id);
	}

}
