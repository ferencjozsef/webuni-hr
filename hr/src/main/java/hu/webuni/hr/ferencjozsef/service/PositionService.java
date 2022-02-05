package hu.webuni.hr.ferencjozsef.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.ferencjozsef.model.Position;
import hu.webuni.hr.ferencjozsef.repository.PositionRepository;

@Service
public class PositionService {

	@Autowired
	private PositionRepository positionRepository;

	@Transactional
	public Position save(Position position) {
		return positionRepository.save(position);
	}
	
}
