package jp.co.isopra.lunchmap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MenuController {

	@RequestMapping("/menu")
	public String showMenuPage() {
		return "menu";
	}
}
