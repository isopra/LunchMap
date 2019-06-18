package jp.co.isopra.lunchmap.controller;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.isopra.lunchmap.entity.AccountDetails;
import jp.co.isopra.lunchmap.entity.FootPrint;
import jp.co.isopra.lunchmap.entity.Image;
import jp.co.isopra.lunchmap.repositories.FootPrintRepository;
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
	FootPrintRepository footprintRepository;

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	private ImageService imageService;


	@RequestMapping(value = "/shopinfo/{place_id}/{place_name}")
	public ModelAndView shopinfo(ModelAndView mav,
			@PathVariable String place_id,
			@PathVariable String place_name,
			Principal principal) {

		// viewの指定
		mav.setViewName("shopinfo");

		//URLからplace_idを取得しviewに送る
		mav.addObject("placeId",place_id);
		mav.addObject("place_name",place_name);

		// ログインしているidを取得しviewに送る
		Authentication auth = (Authentication)principal;
		AccountDetails accountDetails = (AccountDetails)auth.getPrincipal();
		String Login_id = accountDetails.getMember().getLogin_id();
		mav.addObject("Login_id",Login_id);

		// footprintについて
		//　レコード数の取得
		int footprintRecords = footprintRepository.getFootprintRecords(place_id) ;
		mav.addObject("footprintRecords",footprintRecords);
		//　place_idが一致するfootprintオブジェクト
		List<FootPrint> footprintDatalist = footprintRepository.getByPlace_id(place_id);
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

	@RequestMapping(value = "/shopinfo/delete/image/{place_id}/{image_id}")
	@Transactional(readOnly=false)
	public ModelAndView deleteImage(
			@PathVariable String place_id,
			@PathVariable Long image_id
			) {

		imageService.deleteImage(place_id, image_id);

		return new ModelAndView("forward:");

	}

}

