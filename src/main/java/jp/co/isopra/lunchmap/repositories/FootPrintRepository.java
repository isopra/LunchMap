package jp.co.isopra.lunchmap.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.isopra.lunchmap.entity.FootPrint;

@Repository
public interface FootPrintRepository extends JpaRepository<FootPrint, Integer>{
//---------------------------------------------------------------------------
//	エラーが発生したためクエリーを作成
//	public List<FootPrint> findByCreated_timeGreaterThanEquals(Date date);
//	public Optional<FootPrint> findByFootprint_id(Integer id);
//	自分の口コミだけがおされたとき
	@Query(" FROM FootPrint where login_id = :id")
	public List<FootPrint> findByLogin_id(@Param("id") String id);
//	最近の口コミだけがおされたとき
	@Query(" FROM FootPrint where created_time > :date")
	public List<FootPrint> findByCreated_timeGreaterThanEquals(@Param("date") Date date);
//	どちらもおされたとき
	@Query(" FROM FootPrint where created_time > :date and login_id = :id")
	public List<FootPrint> findByCreated_timeAndLogin_id(@Param("date") Date date,@Param("id") String id);
//---------------------------------------------------------------------------

}

