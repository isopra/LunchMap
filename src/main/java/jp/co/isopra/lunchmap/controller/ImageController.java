package jp.co.isopra.lunchmap.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.imageio.IIOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jp.co.isopra.lunchmap.entity.AccountDetails;
import jp.co.isopra.lunchmap.repositories.ImageRepository;
import jp.co.isopra.lunchmap.repositories.ShopRepository;


@Controller
public class ImageController {

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	ShopRepository ShopRepository;

	// 画像登録画面
	@RequestMapping(value= "create/image", method = RequestMethod.GET)
    ModelAndView uploadview(
    		ModelAndView mav,
    		@RequestParam String place_id,
    		@RequestParam String place_name)
	{

		mav.setViewName("image");
	    mav.addObject("place_id", place_id);
	    mav.addObject("place_name", place_name);
	    return mav;
	}

	// 登録処理 ToDo:繰り返し
	@RequestMapping(value="create/image/register", method = RequestMethod.POST)
	String upload(
			ModelAndView mav,
			@RequestParam (value = "files") List<MultipartFile> files,
			@RequestParam String place_id,
			@RequestParam String place_name,
			@AuthenticationPrincipal AccountDetails accountDetails
			)
	{

		// 画像保存処理
		savefiles(files, place_id);



		return "image";
	}

	// 戻るボタン
	@RequestMapping(value = "menu/image/return", method = RequestMethod.POST)
		public String previous(
		@RequestParam String place_id,
		@AuthenticationPrincipal AccountDetails accountDetails,
		ModelAndView mav)
	{
		return "redirect:/shopinfo/" + place_id;

	}

	/* 保存場所の指定 :ここでファイル名を変える*/
    private void savefile(MultipartFile file, String place_id) {
    	String filename = file.getOriginalFilename();
    	Path uploadfile = Paths.get("images/" + place_id + "/" + filename);

    	try (OutputStream os = Files.newOutputStream(uploadfile, StandardOpenOption.CREATE)) {
    		byte[] bytes = file.getBytes();
    		os.write(bytes);
    	} catch (IIOException e) {
    		// エラー処理は省略
    	} catch (IOException e1) {
    		// TODO 自動生成された catch ブロック
    		e1.printStackTrace();
    	}
    }


    //ファイルを保存　：ここで繰り返し処理 place_idも引数で受け取る
    private void savefiles(List<MultipartFile> multipartFiles, String place_id) {
    	createDirectory(place_id);
    	for (MultipartFile file : multipartFiles)
    	{
    		savefile(file, place_id);
    	}
    }


   // フォルダの作成
	private void createDirectory(String place_id) {
		Path path = Paths.get("images/");
		if (!Files.exists(path)) {
			try {
				Files.createDirectory(path);
			} catch (Exception e) {
				//エラー処理は省略
			}
		}

		Path path2 = Paths.get("images/" + place_id);
		if (!Files.exists(path2)) {
			try {
				Files.createDirectory(path2);
			} catch (Exception e) {
				//エラー処理は省略
			}
		}

	}

}

