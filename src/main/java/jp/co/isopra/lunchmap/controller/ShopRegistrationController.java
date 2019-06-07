package jp.co.isopra.lunchmap.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.isopra.lunchmap.entity.Shop;
import jp.co.isopra.lunchmap.service.ShopRegistrationService;

@Controller
public class ShopRegistrationController {

	@Autowired
	private ShopRegistrationService service;

	/**
	 * メンバー登録の処理
	 * @param place_id
	 * @param place_name
	 * @param latitude
	 * @param model 遷移先画面にセットするModel
	 * @return 遷移先画面名
	 */
	@RequestMapping("/shop/register")
	public String registerMember(
			@RequestParam String place_id,
			@RequestParam String place_name,
			@RequestParam BigDecimal latitude,
			@RequestParam BigDecimal longitude,
			Model model) {

//		// DEBUG parameter出力
//		java.lang.System.out.println("member regist:" + login_id + "/" + password + "/" + nickname);

		//memberテーブルにinsertする時の引数。
		Shop entity = new Shop();

		entity.setPlace_id(place_id);
		entity.setPlace_name(place_name);
		entity.setLatitude(latitude);
		entity.setLongitude(longitude);

		//memberテーブルにinsertする。
		service.registerShop(entity);

		model.addAttribute("message", "登録しました");

		return "login";
	}
}
