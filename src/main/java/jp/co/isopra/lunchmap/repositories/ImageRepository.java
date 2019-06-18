package jp.co.isopra.lunchmap.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.isopra.lunchmap.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

	@Query("SELECT COUNT(*) FROM Image WHERE place_id = :place_id")
	int getImageRecords(@Param("place_id") String place_id );

	@Query("FROM Image i WHERE i.place_id = :place_id ORDER BY created_time DESC")
	public List<Image> getByPlace_id(@Param("place_id") String place_id);

	@Transactional
    @Modifying
	@Query("DELETE FROM Image  WHERE image_id = :image_id")
	public void deleteByImage_id(@Param("image_id") Long image_id);


//	自分の口コミだけがおされたとき
	@Query("SELECT DISTINCT place_id FROM Image where login_id = :id ")
	public List<String> findByLogin_id(@Param("id") String id);
//	最近の口コミだけがおされたとき
	@Query("SELECT DISTINCT place_id FROM Image where created_time > :date2")
	public List<String> findByCreated_time(@Param("date2") Date date);
//	どちらもおされたとき
	@Query("SELECT DISTINCT place_id FROM Image where created_time > :date and login_id = :id")
	public List<String> findByCreated_timeAndLogin_id(@Param("date") Date date,@Param("id") String id);


}
