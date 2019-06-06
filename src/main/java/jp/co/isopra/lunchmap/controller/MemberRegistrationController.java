package jp.co.isopra.lunchmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.isopra.lunchmap.entity.AccountDetails;
import jp.co.isopra.lunchmap.entity.Member;
import jp.co.isopra.lunchmap.service.MemberRegistrationService;

@Controller
public class MemberRegistrationController {

	@Autowired
	private MemberRegistrationService service;

	/**
	 * メンバー登録の処理
	 * @param login_id ログインID
	 * @param password パスワード
	 * @param nickname 表示用氏名
	 * @param model 遷移先画面にセットするModel
	 * @return 遷移先画面名
	 */
	@RequestMapping("/member/register/result")
	public String registerMember(
			@RequestParam String login_id,
			@RequestParam String password,
			@RequestParam String nickname) {

//		// DEBUG parameter出力
//		java.lang.System.out.println("member regist:" + login_id + "/" + password + "/" + nickname);

		//memberテーブルにinsertする時の引数。
		Member entity = new Member();

		entity.setLogin_id(login_id);
		entity.setPassword(password);
		entity.setNickname(nickname);

		//memberテーブルにinsertする。
		service.registerMember(entity);

		//TODO 結果をアラート

//		model.addAttribute("message", "登録しました");

		return "menu";
	}

	//登録画面表示
	@RequestMapping("/member/register")
	public String showMemberRegisterForm() {
		return "memberRegisterOrEdit";
	}

	//アカウント情報編集
	@RequestMapping("/member/edit")
	public String editMember(
			@RequestParam String password, 
			@RequestParam String nickname, 
			@AuthenticationPrincipal AccountDetails accountDetails)
	{
		return registerMember(accountDetails.getUsername(), password, nickname);
	}
}
