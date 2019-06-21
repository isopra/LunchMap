package jp.co.isopra.lunchmap.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.imageio.IIOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jp.co.isopra.lunchmap.controller.MapsController.ConditionSession;
import jp.co.isopra.lunchmap.entity.AccountDetails;
import jp.co.isopra.lunchmap.entity.Image;
import jp.co.isopra.lunchmap.entity.Shop;
import jp.co.isopra.lunchmap.repositories.ImageRepository;
import jp.co.isopra.lunchmap.repositories.ShopRepository;


@Controller
@SessionAttributes("scopedTarget.ConditionSession")
public class ImageController {

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	ShopRepository ShopRepository;

	@Autowired
	protected ConditionSession conditionSession;

	// 画像登録画面
	@RequestMapping(value= "create/image", method = RequestMethod.GET)
    ModelAndView uploadview(
    		ModelAndView mav,
    		@RequestParam String place_id,
    		Principal Principal,
    		@AuthenticationPrincipal AccountDetails accountDetails)
	{

		mav.setViewName("image");
	    mav.addObject("place_id", place_id);
	    mav.addObject("place_name", conditionSession.getPlacename());
	    return mav;
	}

	// 登録処理
	@RequestMapping(value="create/image/register", method = RequestMethod.POST)
	String upload(
			ModelAndView mav,
			@RequestParam List<MultipartFile> files,
			@RequestParam String place_id,
			Principal Principal,
			@AuthenticationPrincipal AccountDetails accountDetails
			)
	{
		String place_name = conditionSession.getPlacename();

		for(MultipartFile file : files) {

			Image entity = new Image();
			Date nowDate = new Date();
			Shop entityShop;

			// ShopTableにinsert
			if(this.ShopRepository.existsById(place_id)) {
				entityShop = this.ShopRepository.findById(place_id).get();
			} else {
				entityShop = new Shop();
				entityShop.setPlace_id(place_id);
				entityShop.setPlace_name(place_name);
				entityShop.setLatitude(conditionSession.getLatitude());
				entityShop.setLongitude(conditionSession.getLongitude());

				ShopRepository.save(entityShop);
			}

			// ImageTableにinsert
			entity.setMember(accountDetails.getMember());
			entity.setLogin_id(accountDetails.getMember().getLogin_id());
			entity.setPlace_id(place_id);
			entity.setCreated_time(nowDate);
			entity.setShop(entityShop);

			imageRepository.save(entity);


			// 画像をフォルダに保存
			savefiles(files, place_id);
		}

		return "redirect:/shopinfo/" + place_id;
	}

	/* 保存場所の指定 ToDo:ここでファイル名を変える*/
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

    // ファイルを保存 TODO：ここで繰り返し処理
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

	// 戻るボタン TODO:前のページに戻るようにする
	@RequestMapping(value = "/shopinfo", method = RequestMethod.POST)
		public String previous(
				ModelAndView mav,
				@RequestParam String place_id,
				Principal Principal,
				@AuthenticationPrincipal AccountDetails accountDetails)
	{
		return "redirect:/shopinfo/" + place_id;

	}

}

