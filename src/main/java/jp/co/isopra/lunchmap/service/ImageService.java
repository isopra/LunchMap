package jp.co.isopra.lunchmap.service;

import java.io.File;

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


		String url = "images/"+ place_id + "/" + place_id + "_" + image_id + ".jpg";

		File file = new File(url);


		if (!file.exists()) {
            System.out.println("ファイル:[" + url + "]が存在しません");
        }

        if (file.delete()) {
        	imageRepository.deleteByImage_id(image_id);
            System.out.println("ファイル:[" + url + "]の削除に成功しました");
        } else {
        	System.out.println("ファイル:[" + url + "]の削除に失敗しました");
        }
    }
}
