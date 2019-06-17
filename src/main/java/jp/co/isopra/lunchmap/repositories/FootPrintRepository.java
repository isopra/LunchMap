package jp.co.isopra.lunchmap.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.isopra.lunchmap.entity.FootPrint;

@Repository
public interface FootPrintRepository extends JpaRepository<FootPrint, Long>{
//---------------------------------------------------------------------------
//	エラーが発生したためクエリーを作成
//	public List<FootPrint> findByCreated_timeGreaterThanEquals(Date date);
//	public Optional<FootPrint> findByFootprint_id(Integer id);
//	自分の口コミだけがおされたとき
	@Query("SELECT DISTINCT place_id FROM FootPrint where login_id = :id ")
	public List<String> findByLogin_id(@Param("id") String id);
//	最近の口コミだけがおされたとき
	@Query("SELECT DISTINCT place_id FROM FootPrint where created_time > :date")
	public List<String> findByCreated_timeGreaterThanEquals(@Param("date") Date date);
//	どちらもおされたとき
	@Query("SELECT DISTINCT place_id FROM FootPrint where created_time > :date and login_id = :id")
	public List<String> findByCreated_timeAndLogin_id(@Param("date") Date date,@Param("id") String id);
//	初期表示時
	@Query("SELECT DISTINCT place_id FROM FootPrint ")
	public List<String> findByplace_id();

//---------------------------------------------------------------------------

	@Query("SELECT COUNT(*) FROM FootPrint WHERE place_id = :place_id")
	int getFootprintRecords(@Param("place_id") String place_id );

	@Query("FROM FootPrint f WHERE f.place_id = :place_id ORDER BY created_time DESC")
	public List<FootPrint> getByPlace_id(@Param("place_id") String place_id);
}
