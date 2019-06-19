package jp.co.isopra.lunchmap.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.isopra.lunchmap.entity.AccountDetails;
import jp.co.isopra.lunchmap.entity.Image;
import jp.co.isopra.lunchmap.repositories.ImageRepository;
import jp.co.isopra.lunchmap.repositories.ShopRepository;

@Controller
public class ImageController {

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	ShopRepository ShopRepository;

	// 画像登録画面
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
	public String createimg(
			@RequestParam String place_id,
//			@RequestParam String place_name,
			@ModelAttribute Image image,
			@AuthenticationPrincipal AccountDetails accountDetails,
			ModelAndView mav
			)
	{
		Image entity = new Image();
		Date nowDate = new Date();

		System.out.println("place_id : " + place_id);

		/* 画像枚数分Insert
		for() {

		}
		*/
//		Shop entityShop = this.ShopRepository.findById(place_id).get();

		// DBに各値をセット
		entity.setMember(accountDetails.getMember());
		entity.setLogin_id(accountDetails.getMember().getLogin_id());
		entity.setPlace_id(place_id);
		entity.setCreated_time(nowDate);
//		entity.setShop(entityShop);


		imageRepository.save(entity);
		return "redirect:/shopinfo/" + place_id;
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
