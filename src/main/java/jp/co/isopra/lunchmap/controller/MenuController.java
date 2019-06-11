package jp.co.isopra.lunchmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.isopra.lunchmap.entity.AccountDetails;
import jp.co.isopra.lunchmap.entity.Member;
import jp.co.isopra.lunchmap.service.MemberRegistrationService;

@Controller
public class MenuController {
	
	@Autowired
	private MemberRegistrationService service;

	//メニュー画面表示
	@RequestMapping("/menu")
	public String showMenuPage() {
		return "menu";
	}

	//アカウント情報編集画面表示
	@RequestMapping("/menu/acount_edit")
	public String showAccountEdit(@AuthenticationPrincipal AccountDetails accountDetails, Model model) {
		Member member = service.findMember(accountDetails.getUsername());
		model.addAttribute("id", member.getLogin_id());
		model.addAttribute("nickname", member.getNickname());

		return "accountEdit";
	}
	
	//メンバー管理画面表示
	@RequestMapping("/menu/member_manager")
	public String showMemberManager() {
		return "memberManager";
	}
}
