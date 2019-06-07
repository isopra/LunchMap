package jp.co.isopra.lunchmap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.isopra.lunchmap.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {


}
