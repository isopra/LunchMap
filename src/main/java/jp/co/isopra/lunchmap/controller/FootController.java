package jp.co.isopra.lunchmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.isopra.lunchmap.entity.AccountDetails;
import jp.co.isopra.lunchmap.entity.FootPrint;
import jp.co.isopra.lunchmap.entity.Shop;
import jp.co.isopra.lunchmap.repositories.FootPrintRepository;
import jp.co.isopra.lunchmap.repositories.ShopRepository;

@Controller
public class FootController {

	@Autowired
	FootPrintRepository footRepository;

	@Autowired
	ShopRepository shopRepository;


	// コメント編集
	@RequestMapping(value = "menu/foot/edit", method = RequestMethod.GET )
	public ModelAndView footEdit (
		@RequestParam (name = "footprint_id", defaultValue = "" ) Long footprint_id,
		@RequestParam (name = "place_name", defaultValue = "") String place_name,
		@AuthenticationPrincipal AccountDetails accountDetails,
		ModelAndView mav)
	{
		mav.setViewName("footEdit");
		FootPrint entityFootPrint = this.footRepository.findById(footprint_id).get();
		Shop entityShop = this.shopRepository.findById(place_name).get();
		mav.addObject("editComment", entityFootPrint.getComment());
		mav.addObject("place_name", entityShop.getPlace_name());
		// ToDo: entityFootPrintから登録日時を表示

		return mav;
	}


	// コメント作成
	@RequestMapping(value = "menu/foot", method = RequestMethod.GET )
	public ModelAndView footCreate(
		@RequestParam(name = "place_id", defaultValue = "") String place_id,
		@RequestParam (name = "place_name", defaultValue = "") String place_name,
		@AuthenticationPrincipal AccountDetails accountDetails,
		ModelAndView mav)
	{
		mav.setViewName("foot");
		System.out.println(place_name);
		Shop entityShop = this.shopRepository.findById(place_id).get();

		mav.addObject("place_name", entityShop.getPlace_name());
		// ToDo:システム日時を表示


		return mav;
	}

	// 登録処理
	@RequestMapping(value = "menu/foot/register", method = RequestMethod.POST)
	public ModelAndView newFoot(
		@RequestParam String comment,
		@RequestParam String place_id,
		@AuthenticationPrincipal AccountDetails accountDetails,
		ModelAndView mav)
	{
		System.out.println("test");
		int commentLength =  comment.length();
		if  (commentLength <= 200) {
			// DBに各値をセット
			FootPrint entity = new FootPrint();

			entity.setComment(comment);
			entity.setMember(accountDetails.getMember());
			// ToDo:place_idを登録
			// entity.setShop(Shop.);

			// ToDo:日時を登録
			//entity.setDatetime();

			footRepository.saveAndFlush(entity);
			System.out.println("登録しました");

			mav.setViewName("foot");


		} else {
			mav.setViewName("foot");
			mav.addObject("error_message", "200字以内で入力してください");  // ToDo: 表示させる
		}

		return mav;
	}


	// 戻るボタン
	@RequestMapping("menu/foot/return")
	public ModelAndView returnButton(
		@RequestParam(name = "place_id", defaultValue = "") String place_id,
		@RequestParam(name = "place_name", defaultValue = "") String place_name,
		ModelAndView mav)
	{

		mav.setViewName("shop");
		return mav;
	}
}

