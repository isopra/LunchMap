package jp.co.isopra.lunchmap.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.isopra.lunchmap.repositories.FootPrintRepository;
import jp.co.isopra.lunchmap.repositories.ImageRepository;
import jp.co.isopra.lunchmap.repositories.MemberRepository;
import jp.co.isopra.lunchmap.repositories.ShopRepository;

@Service
public class ImageService {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	ShopRepository shopRepository;

	@Autowired
	FootPrintRepository footprintRepository;

	@Autowired
	ImageRepository imageRepository;

	public void deleteImage(String place_id,Long image_id) {

		Path path = Paths.get("images/" + place_id + "/" + place_id + "_" + image_id + ".jpg");

		if (!Files.exists(path)) {
            System.out.println("ファイル:[" + path + "]が存在しません");
        }

        try {
        	Files.delete(path);
        	imageRepository.deleteByImage_id(image_id);
            System.out.println("ファイル:[" + path + "]の削除に成功しました");
        } catch (Exception e) {
        	System.out.println("ファイル:[" + path + "]の削除に失敗しました");
        }
    }
}
