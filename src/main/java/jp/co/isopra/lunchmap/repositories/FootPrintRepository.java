package jp.co.isopra.lunchmap.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.isopra.lunchmap.entity.FootPrint;

@Repository
public interface FootPrintRepository extends JpaRepository<FootPrint, Long> {

	@Query("SELECT COUNT(*) FROM FootPrint WHERE place_id = :place_id")
	int getFootprintRecords(@Param("place_id") String place_id );

	@Query("FROM FootPrint f WHERE f.place_id = :place_id ORDER BY created_time DESC")
	public List<FootPrint> getByPlace_id(@Param("place_id") String place_id);
}
