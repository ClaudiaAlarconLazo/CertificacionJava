package cl.aiep.certif.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.aiep.certif.dao.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer>{

	
	
}
