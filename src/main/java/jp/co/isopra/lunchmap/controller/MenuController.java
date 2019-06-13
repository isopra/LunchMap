package jp.co.isopra.lunchmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.isopra.lunchmap.controller.MapsController.ConditionSession;



@Controller
public class MenuController {

	@Autowired
	protected ConditionSession conditionSession;


	@RequestMapping("/menu")
	public String showMenuPage() {
//		検索条件のリセット
		conditionSession.setCondition(null);
		System.out.println(conditionSession.getCondition());
		return "menu";
	}
}
