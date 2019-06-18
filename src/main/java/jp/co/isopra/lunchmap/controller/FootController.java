package jp.co.isopra.lunchmap.controller;

import java.text.SimpleDateFormat;
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


	// コメント編集時画面
	@RequestMapping(value = "menu/foot/edit", method = RequestMethod.GET )
	public ModelAndView footEdit (
		@RequestParam (name = "footprint_id", defaultValue = "" ) Long footprint_id,
		@RequestParam (name = "place_id", defaultValue = "") String place_id,
		@AuthenticationPrincipal AccountDetails accountDetails,
		ModelAndView mav)
	{

		// DBから登録内容を検索
		FootPrint entityFootPrint = this.footRepository.findById(footprint_id).get();
		Shop entityShop = this.shopRepository.findById(place_id).get();

		// 登録日時のフォーマットを指定
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		String formatDate = dateFormat.format(entityFootPrint.getCreated_time());

		// 表示
		mav.setViewName("footEdit"); // これないと画面表示されない
		mav.addObject("editComment", entityFootPrint.getComment());
		mav.addObject("editPlace_name", entityShop.getPlace_name());
		mav.addObject("editDatetime", formatDate);
		//viewでは非表示
		mav.addObject("place_id", entityShop.getPlace_id());
		mav.addObject("footprint_id", footprint_id);

		return mav;
	}

	//  コメント内容更新
	@RequestMapping(value = "menu/foot/update", method = RequestMethod.POST)
	public String Update(
			@RequestParam String comment_edit,
			@RequestParam String place_id,
			@RequestParam Long footprint_id,
			@ModelAttribute FootPrint footPrint,
			@AuthenticationPrincipal AccountDetails accountDetails,
			ModelAndView mav)
	{
		Date nowDate = new Date();

		Shop entityShop = this.shopRepository.findById(place_id).get();
		FootPrint entityFootPrint = this.footRepository.findById(footprint_id).get();

		// DBに各値をセット
		entityFootPrint.setComment(comment_edit);
		entityFootPrint.setCreated_time(nowDate);
		entityFootPrint.setShop(entityShop);

		footRepository.saveAndFlush(entityFootPrint);

		/* ToDo: alert表示してから戻る
		 * 	mav.setViewName("foot");
			return mav;
		 */

		return "redirect:/shopinfo/" + place_id;

	}


	// コメント新規作成時画面
	@RequestMapping(value = "menu/foot", method = RequestMethod.GET )
	public ModelAndView footCreate(
		@RequestParam String place_id,
		@RequestParam String place_name,
		@AuthenticationPrincipal AccountDetails accountDetails,
		ModelAndView mav)
	{

		// 現在日時のフォーマットを指定
		Date nowDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		String formatDate = dateFormat.format(nowDate);

		// 表示
		mav.setViewName("foot");
		mav.addObject("place_name", place_name);
		mav.addObject("datetime", formatDate);
		mav.addObject("place_id", place_id);

		return mav;
	}

	// 登録
	@RequestMapping(value = "menu/foot/register", method = RequestMethod.POST)
	public String newFoot(
		@RequestParam String comment,
		@RequestParam String place_id,
		@ModelAttribute FootPrint footprint,
		@AuthenticationPrincipal AccountDetails accountDetails,
		ModelAndView mav)
	{

		FootPrint entity = new FootPrint();
		Date nowDate = new Date();
		Shop entityShop = this.shopRepository.findById(place_id).get();

		// DBに各値をセット
		entity.setComment(comment);
		entity.setMember(accountDetails.getMember());
		entity.setLogin_id(accountDetails.getMember().getLogin_id());
		entity.setPlace_id(place_id);
		entity.setCreated_time(nowDate);
		entity.setShop(entityShop);

		footRepository.saveAndFlush(entity);

		/* ToDo:
		 * alert表示してから戻る
		 * mav.setViewName("foot");
		 * return mav;
		 */

		return "redirect:/shopinfo/" + place_id;

	}

	// 戻る
	@RequestMapping(value = "menu/foot/previous", method = RequestMethod.POST)
		public String previous(
		@RequestParam String place_id,
		@AuthenticationPrincipal AccountDetails accountDetails,
		ModelAndView mav)
	{
		return "redirect:/shopinfo/" + place_id;

	}
}