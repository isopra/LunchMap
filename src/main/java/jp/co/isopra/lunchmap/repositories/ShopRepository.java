package jp.co.isopra.lunchmap.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.isopra.lunchmap.entity.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, String> {

//	public Optional<Shop> findById(Long place_id);
	@Query("SELECT place_id FROM Shop ")
	public List<String> findByplace_id();

	@Query("FROM Shop where place_id in(:id) ")
	public List<Shop> findByid(@Param("id") Set<String> id);

	@Query("FROM Shop where latitude < :lat1 and latitude >:lat2 and longitude <:lng1 and longitude > :lng2 ")
	public List<Shop> findBylatlng(@Param("lat1") BigDecimal lat1,@Param("lat2") BigDecimal lat2,@Param("lng1") BigDecimal lng1,@Param("lng2") BigDecimal lng2 );


	@Query("FROM Shop where place_id in(:id) and  latitude < :lat1 and latitude >:lat2 and longitude <:lng1 and longitude > :lng2 ")
	public List<Shop> findBylatlngAndId(@Param("id") Set<String> id,@Param("lat1") BigDecimal lat1,@Param("lat2") BigDecimal lat2,@Param("lng1") BigDecimal lng1,@Param("lng2") BigDecimal lng2 );


}
