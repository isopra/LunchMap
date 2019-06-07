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
import jp.co.isopra.lunchmap.repositories.FootPrintRepository;

@Controller
public class FootController {

	@Autowired
	FootPrintRepository repository;


	// コメント編集ボタンからの遷移時
	@RequestMapping(value = "menu/foot/edit", method = RequestMethod.GET )
	public ModelAndView footEdit (
		//@RequestParam String comment,
		@RequestParam (name = "footprint_id", defaultValue = "" ) Long footprint_id,
		@RequestParam (name = "place_name", defaultValue = "") String place_name,
		@AuthenticationPrincipal AccountDetails accountDetails,
		ModelAndView mav
	) {

		// footprint_id != ""  && place_name != "") {

		mav.setViewName("footEdit");
		FootPrint entityFootPrint = this.repository.findById(footprint_id).get();
		mav.addObject("editComment", entityFootPrint.getComment());
		return mav;
	}

	// あしあとボタンからの遷移時
	@RequestMapping(value = "menu/foot",  method = RequestMethod.GET )
	public ModelAndView footCreate(
			@RequestParam(name = "place_id", defaultValue = "") String place_id,
		//	@RequestParam (name = "place_name", defaultValue = "") String place_name,
			@AuthenticationPrincipal AccountDetails accountDetails,
			ModelAndView mav) {

			 	// (place_id != "" && place_name !="")
				// あしあとIDからシステム日時
				// textareaを空で表示
				mav.setViewName("menu/foot");
				return mav;
			}

	// 登録処理
	@RequestMapping(value = "menu/foot/register", method = RequestMethod.POST)
	public ModelAndView newFoot(
			@RequestParam String comment,
			@AuthenticationPrincipal AccountDetails accountDetails,
			ModelAndView mav) {

				int commentLength =  comment.length();
				if  (commentLength <= 200) {
					FootPrint entity = new FootPrint();
					// DBに各値をセット
					entity.setComment(comment);
					entity.setLogin_id(accountDetails.getMember());
					/* ToDo
					entity.setCreated_time();
					*/
					repository.save(entity);


				} else {
					mav.setViewName("menu/foot");
					mav.addObject("error_message", "200字以内で入力してください");  // ToDo: エラー表示させる
				}

				return mav;
			}

	// 戻るボタン
	@RequestMapping("")
	public ModelAndView returnButton(
		@RequestParam(name = "place_id", defaultValue = "") String place_id,
		@RequestParam(name = "place_name", defaultValue = "") String place_name,
		ModelAndView mav) {

			mav.setViewName("");
			return mav;
	}
}

