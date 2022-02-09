package hu.webuni.hr.ferencjozsef.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import hu.webuni.hr.ferencjozsef.model.DayOff;

public interface DayOffRepository extends JpaRepository<DayOff, Long>,  JpaSpecificationExecutor<DayOff>{

}
