package jp.co.isopra.lunchmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.isopra.lunchmap.entity.Shop;
import jp.co.isopra.lunchmap.repositories.ImageRepository;
import jp.co.isopra.lunchmap.repositories.ShopRepository;

@Controller
public class ImageController {

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	ShopRepository ShopRepository;

	// 画面遷移
	@RequestMapping(value= "menu/image", method = RequestMethod.GET )
	public ModelAndView img(
			@RequestParam (name = "place_id", defaultValue = "") String place_id,
			@RequestParam (name = "place_name", defaultValue = "") String place_name,
			ModelAndView mav)
	{
		mav.setViewName("image");
		Shop entityShop = this.ShopRepository.findById(place_id).get();
		mav.addObject("getplace_name", entityShop.getPlace_name());
		return mav;
	}

	/* 登録処理
	@RequestMapping(value = "menu/image/register", method = RequestMethod.POST)
	public ModelAndView newImg(
			@RequestParam String image_id,
			ModelAndView mav)
	{

		if(){
			// 画像数分DBにInsert
			for( ) {
				Image entity = new Image();
				entity.setImage_id(image_id);

				// ToDo 画像のファイル名を変更

				repository.save(entity);
			}
			System.out.println("登録しました");
		} else {
			// 未選択の場合alert表示
			mav.setViewName("menu/image");
			mav.addObject("img_error_message", "画像ファイルを選択してください");  // ToDo: 表示させる

		}
		return mav;
	}*/


	// 戻るボタン
	@RequestMapping("menu/image/return")
	public ModelAndView returnButton(
		@RequestParam(name = "place_id", defaultValue = "") String place_id,
		@RequestParam(name = "place_name", defaultValue = "") String place_name,
		ModelAndView mav)
	{

		mav.setViewName("shop");
		return mav;
	}

}
