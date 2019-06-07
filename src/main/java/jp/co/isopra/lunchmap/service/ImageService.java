package jp.co.isopra.lunchmap.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.isopra.lunchmap.repositories.FootprintRepository;
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
	FootprintRepository footprintRepository;

	@Autowired
	ImageRepository imageRepository;

	public void deleteImage(String place_id,Long image_id) {

		String urltest = "C:\\Users\\kondo.takuya\\Documents\\LunchMap\\src\\main\\resources\\static\\images\\"+ place_id + "\\" + place_id + "_" + image_id + ".jpg";
		String url = "http://localhost:8080/images/" + place_id + "/" + place_id + "_" + image_id + ".jpg";
		File file = new File(urltest);

		if (!file.exists()) {
            System.out.println("ファイル:[" + urltest + "]が存在しません");
        }
		
        if (file.delete()) {
            System.out.println("ファイル:[" + url + "]の削除に成功しました");
			imageRepository.deleteByImage_id(image_id);
        } else {
        	System.out.println("ファイル:[" + url + "]の削除に失敗しました");
        }
    }
}
