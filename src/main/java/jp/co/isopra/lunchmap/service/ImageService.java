package jp.co.isopra.lunchmap.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.isopra.lunchmap.repositories.ImageRepository;

@Service
public class ImageService {

	@Autowired
	ImageRepository imageRepository;

	public void deleteImage(String place_id,Long image_id) {

		String urltest = "C:\\Users\\kondo.takuya\\Documents\\LunchMap\\src\\main\\resources\\static\\images\\test\\test_1.jpg";
		String url = "http://localhost:8080/images/" + place_id + "/" + place_id + "_" + image_id + ".jpg";
		File file = new File(urltest);

		if (!file.exists()) {
            System.out.println("ファイル:[" + urltest + "]が存在しません");

        }
        if (file.delete()) {
            System.out.println("ファイル:[" + url + "]の削除に成功しました");
            imageRepository.deleteById(image_id);

        }
        System.out.println("ファイル:[" + url + "]の削除に失敗しました");

    }
}
