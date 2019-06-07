package jp.co.isopra.lunchmap.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.isopra.lunchmap.entity.FootPrint;

@Repository
public interface FootPrintRepository extends JpaRepository<FootPrint, Long>{

	public Optional<FootPrint> findById(Long footprint_id);
}

