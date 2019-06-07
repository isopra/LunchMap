package jp.co.isopra.lunchmap.controller;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import jp.co.isopra.lunchmap.entity.AccountDetails;
import jp.co.isopra.lunchmap.entity.Footprint;
import jp.co.isopra.lunchmap.entity.Image;
import jp.co.isopra.lunchmap.repositories.FootprintRepository;
import jp.co.isopra.lunchmap.repositories.ImageRepository;
import jp.co.isopra.lunchmap.repositories.MemberRepository;
import jp.co.isopra.lunchmap.repositories.ShopRepository;
import jp.co.isopra.lunchmap.service.ImageService;

@Controller
public class ShopinfoController {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	ShopRepository shopRepository;

	@Autowired
	FootprintRepository footprintRepository;

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	private ImageService imageService;


	@RequestMapping(value = "/shopinfo/{place_id}")
	public ModelAndView shopinfo(ModelAndView mav,
			@PathVariable String place_id,
			Principal principal) {

		// ログインしているidを取得しviewに送る
		Authentication auth = (Authentication)principal;
		AccountDetails accountDetails = (AccountDetails)auth.getPrincipal();
		String Login_id = accountDetails.getMember().getLogin_id();
		mav.addObject("Login_id",Login_id);

		// 動作確認のための変数たち
		String placeIdTest = "ChIJ4XvxNw6MGGAR6EctyrTwmyI";
		mav.addObject("placeId",placeIdTest);


		mav.setViewName("shopinfo");

		// footprintについて
		//　レコード数の取得
		int footprintRecords = footprintRepository.getFootprintRecords(place_id) ;
		mav.addObject("footprintRecords",footprintRecords);
		//　place_idが一致するfootprintオブジェクト
		List<Footprint> footprintDatalist = footprintRepository.getByPlace_id(place_id);
		mav.addObject("footprintDatalist",footprintDatalist);

		// imageについて
		//　レコード数の取得
		int imageRecords = imageRepository.getImageRecords(place_id) ;
		mav.addObject("imageRecords",imageRecords);
		//　place_idが一致するImageオブジェクト
		List<Image> imageDatalist = imageRepository.getByPlace_id(place_id);
		mav.addObject("imageDatalist",imageDatalist);

		return mav;

	}

	@RequestMapping(value = "/shopinfo/update/{footprint_id}")
	public ModelAndView updateFootprint(
			@PathVariable Long footprint_id,
			ModelAndView mav) {

		mav.setViewName(""); //あしあとアップデートページへ
		mav.addObject("place_id",footprint_id);

        return mav;
    }

	@RequestMapping(value = "/shopinfo/delete/{place_id}/{image_id}")
	@Transactional(readOnly=false)
	public ModelAndView deleteImage(
			@PathVariable String place_id,
			@PathVariable Long image_id
			) {

		System.out.println(image_id);
		imageService.deleteImage(place_id, image_id);

		return new ModelAndView("redirect:/shopinfo/" + place_id);

	}

}
