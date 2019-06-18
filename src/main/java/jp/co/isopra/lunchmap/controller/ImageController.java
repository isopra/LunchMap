package jp.co.isopra.lunchmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	// 画面遷移
	@RequestMapping(value= "create/image", method = RequestMethod.GET )
	public ModelAndView img(
			@RequestParam String place_id,
			@RequestParam String place_name,
			ModelAndView mav)
	{
		mav.setViewName("image");
		mav.addObject("place_name", place_name);
		
		// viewで非表示
		mav.addObject("place_id", place_id);

		return mav;
	}

	// 登録処理
	@RequestMapping(value="create/image/register")
	public ModelAndView createimg(
			@RequestParam String place_id,
//			@RequestParam String place_name,
			ModelAndView mav
			)
	{

		/* 画像枚数分Insert
		for() {
			Image entity = new image();

			entity.setImage_id(image_id);
			imageRepository.saveAndFlush(entity);
		}*/
		mav.setViewName("Image");
		System.out.print("register");
		return mav;
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

}
