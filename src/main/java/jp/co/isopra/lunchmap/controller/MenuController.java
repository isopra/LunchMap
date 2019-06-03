package jp.co.isopra.lunchmap.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.isopra.lunchmap.entity.AccountDetails;

@Controller
public class MenuController {

	@RequestMapping("/menu")
	public String showMenuPage() {
		return "menu";
	}

	@RequestMapping("/menu/acount_edit")
	public String showAccountEdit(@AuthenticationPrincipal AccountDetails accountDetails, Model model) {
		model.addAttribute("id", accountDetails.getUsername());
//		model.addAttribute("password", accountDetails.getMember().getPassword());
		model.addAttribute("nickname", accountDetails.getMember().getNickname());

		return "accountEdit";
	}
}
