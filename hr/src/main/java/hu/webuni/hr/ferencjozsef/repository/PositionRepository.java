package hu.webuni.hr.ferencjozsef.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.ferencjozsef.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long>  {
	
	@Query("SELECT p FROM Position p")
	public List<Position> findAll();
}
