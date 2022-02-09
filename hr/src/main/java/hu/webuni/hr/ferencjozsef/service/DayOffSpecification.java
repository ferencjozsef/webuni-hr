package hu.webuni.hr.ferencjozsef.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.jpa.domain.Specification;

import hu.webuni.hr.ferencjozsef.model.DayOff;
import hu.webuni.hr.ferencjozsef.model.DayOff_;

public class DayOffSpecification {
	
	// Az igény állapota: pontos egyezés
	public static Specification<DayOff> hasApproved (Boolean approved) {
		return (root, cq, cb) -> cb.equal(root.get(DayOff_.approved), approved);
	}

	// Az igény leadója: elég a név elejánek egyeznie, case-insensitive módon
	public static Specification<DayOff> hasCreateUser(String createUserName) {
		return (root, cq, cb) -> cb.like(cb.upper(root.get(DayOff_.createUser)), createUserName.toUpperCase() + "%");
	}

	// Az igény elbirálója: elég a név elejánek egyeznie, case-insensitive módon
	public static Specification<DayOff> hasBoss(String bossName) {
		return (root, cq, cb) -> cb.like(cb.upper(root.get(DayOff_.boss)), bossName.toUpperCase() + "%");
	}

	// Tól/ig határokkal az igény leadásának dátuma
	public static Specification<DayOff> hasCreateDate(LocalDate startDate, LocalDate endDate) {
		LocalDateTime startOfDay = LocalDateTime.of(startDate, LocalTime.of(0, 0));
		LocalDateTime endOfDay = LocalDateTime.of(endDate, LocalTime.of(0, 0)).plusDays(1);
		return (root, cq, cb) -> cb.between(root.get(DayOff_.createDate), startOfDay, endOfDay);
	}

	// Tól/ig határokkal az igényelt szabadság időszakra. Az jelent találatot, ha van átfedés a szűrésnél
	// megadott két dátum, és a szabadság kezdő és végdátuma által meghatározott két időintervallum között
	public static Specification<DayOff> hasDayOffDate(LocalDate startDate, LocalDate endDate) {
//		return (root, cq, cb) -> cb.and(cb.between(root.get(DayOff_.startDate), startDate, endDate),
//										cb.between(root.get(DayOff_.endDate), startDate, endDate));
		return (root, cq, cb) -> cb.equal(cb.greaterThan(root.get(DayOff_.startDate), startDate),
										cb.lessThan(root.get(DayOff_.endDate), endDate));

	}
}
