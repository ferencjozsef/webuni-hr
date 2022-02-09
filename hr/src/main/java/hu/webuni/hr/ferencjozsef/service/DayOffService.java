
package hu.webuni.hr.ferencjozsef.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import hu.webuni.hr.ferencjozsef.model.DayOff;
import hu.webuni.hr.ferencjozsef.model.DayOff;
import hu.webuni.hr.ferencjozsef.repository.DayOffRepository;

@Service
public class DayOffService {

	
	@Autowired
	private DayOffRepository dayOffRepository;

	public Page<DayOff> findAll(Pageable pageable) {
		return dayOffRepository.findAll(pageable);
	}

	public Optional<DayOff> findById(long id) {
		return dayOffRepository.findById(id);
	}
	
	@Transactional
	public DayOff save(DayOff dayOff) {
		return dayOffRepository.save(dayOff);
	}

	@Transactional
	public DayOff update(DayOff dayOff) {
		Optional<DayOff> savedDayOff = findById(dayOff.getId());
		if (savedDayOff.isEmpty() || savedDayOff.get().getApproved() != null) {
			return null;
		}

		savedDayOff.get().setStartDate(dayOff.getStartDate());
		savedDayOff.get().setEndDate(dayOff.getEndDate());
		savedDayOff.get().setCreateUser(dayOff.getCreateUser());
		savedDayOff.get().setCreateDate(dayOff.getCreateDate());
			
		return dayOffRepository.save(savedDayOff.get());
	}

	@Transactional
	public void delete(long id) {
		Optional<DayOff> savedDayOff = findById(id);
		if (savedDayOff.get().getApproved() == null) {
			dayOffRepository.deleteById(id);
		}
	}

	@Transactional
	public DayOff aprove(DayOff dayOff) {
		Optional<DayOff> savedDayOff = findById(dayOff.getId());
		if (savedDayOff.isEmpty()) {
			return null;
		}
		savedDayOff.get().setApproved(dayOff.getApproved());
		savedDayOff.get().setBoss(dayOff.getBoss());
		return dayOffRepository.save(savedDayOff.get());
	}

	public List<DayOff> findDayOffByExample(Boolean approved, String createUser, String boss, 
			LocalDate createDateStart, LocalDate createDateEnd, LocalDate startDayOffDate,
			LocalDate endDayOffDate) {

		Specification<DayOff> spec = Specification.where(null);

		if (approved != null) {
			spec = spec.and(DayOffSpecification.hasApproved(approved));
		}

		if (createUser != null && StringUtils.hasText(createUser)) {
			spec = spec.and(DayOffSpecification.hasCreateUser(createUser));
		}

		if (boss != null && StringUtils.hasText(boss)) {
			spec = spec.and(DayOffSpecification.hasBoss(boss));
		}

		if (createDateStart != null && createDateEnd != null) {
			spec = spec.and(DayOffSpecification.hasCreateDate(createDateStart, createDateEnd));
		}

		if (startDayOffDate != null && endDayOffDate != null) {
			spec = spec.and(DayOffSpecification.hasDayOffDate(startDayOffDate, endDayOffDate));
		}
		
		return dayOffRepository.findAll(spec, Sort.by("id"));
	}

}
