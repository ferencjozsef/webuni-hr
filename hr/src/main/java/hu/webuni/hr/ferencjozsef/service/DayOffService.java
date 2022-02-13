
package hu.webuni.hr.ferencjozsef.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import hu.webuni.hr.ferencjozsef.dto.SearchDayOffDto;
import hu.webuni.hr.ferencjozsef.model.DayOff;
import hu.webuni.hr.ferencjozsef.model.Employee;
import hu.webuni.hr.ferencjozsef.repository.DayOffRepository;

@Service
public class DayOffService {

	
	@Autowired
	private DayOffRepository dayOffRepository;
	
	@Autowired
	EmployeeService employeeService;

	public Page<DayOff> findAll(Pageable pageable) {
		return dayOffRepository.findAll(pageable);
	}

	public Optional<DayOff> findById(long id) {
		return dayOffRepository.findById(id);
	}
	
	public Page<DayOff> findDayOffByExample(SearchDayOffDto searchDayOffDto, Pageable pageable) {
		Boolean approved = searchDayOffDto.getApproved();
		String employeeName = searchDayOffDto.getEmployeeName();
		String approverName = searchDayOffDto.getApproverName();
		LocalDate createDateStart = searchDayOffDto.getCreateDateStart();
		LocalDate createDateEnd = searchDayOffDto.getCreateDateEnd();
		LocalDate startDayOffDate = searchDayOffDto.getStartDayOffDate();
		LocalDate endDayOffDate = searchDayOffDto.getEndDayOffDate();
		
		Specification<DayOff> spec = Specification.where(null);

		if (approved != null) {
			spec = spec.and(DayOffSpecification.hasApproved(approved));
		}

		if (employeeName != null && StringUtils.hasText(employeeName)) {
			spec = spec.and(DayOffSpecification.hasEmployeeName(employeeName));
		}

		if (approverName != null && StringUtils.hasText(approverName)) {
			spec = spec.and(DayOffSpecification.hasApproverName(approverName));
		}

		if (createDateStart != null && createDateEnd != null) {
			spec = spec.and(DayOffSpecification.createDateIsBeetwen(createDateStart, createDateEnd));
		}

		if (startDayOffDate != null ) {
			spec = spec.and(DayOffSpecification.isStartDateLeesThan(startDayOffDate));
		}
		
		if (endDayOffDate != null ) {
			spec = spec.and(DayOffSpecification.isEndtDateGreaterThan(endDayOffDate));
		}

		return dayOffRepository.findAll(spec, pageable);
	}
	
	@Transactional
	public DayOff addDayOff(DayOff dayOff, long employeeId) {
		Employee employee = employeeService.findById(employeeId).get();
		employee.addDayOff(dayOff);
		dayOff.setCreateDate(LocalDateTime.now());
		return dayOffRepository.save(dayOff);
	}

	@Transactional
	public void delete(long id) {
		Optional<DayOff> savedDayOff = findById(id);
		if (savedDayOff.get().getApproved() != null) {
			throw new IllegalStateException();
		}
		savedDayOff.get().getEmployee().getDayOffs().remove(savedDayOff.get());
		dayOffRepository.deleteById(id);
	}
	
	@Transactional
	public DayOff modifyDayOff(long id, DayOff dayOff) {
		DayOff savedDayOff = findById(dayOff.getId()).get();
		if (savedDayOff.getApproved() != null) {
			throw new IllegalStateException();
		}

		savedDayOff.setStartDate(dayOff.getStartDate());
		savedDayOff.setEndDate(dayOff.getEndDate());
		savedDayOff.setCreateDate(LocalDateTime.now());
			
		return savedDayOff;
	}

	@Transactional
	public DayOff approveDayOff(long id, long approvedId, boolean status) {
		DayOff savedDayOff = findById(id).get();
		Employee employee = savedDayOff.getEmployee();
		Employee boss = employee.getBoss();
		if (boss == null || !employee.getBoss().getId().equals(approvedId)) {
			throw new IllegalStateException();
		}
		savedDayOff.setApproved(status);
		savedDayOff.setApprovedDate(LocalDateTime.now());
		savedDayOff.setApprover(employeeService.findById(approvedId).get());
		return savedDayOff;
	}
}
