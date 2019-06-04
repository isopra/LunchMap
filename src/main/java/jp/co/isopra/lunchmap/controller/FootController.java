package jp.co.isopra.lunchmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.isopra.lunchmap.entity.AccountDetails;
import jp.co.isopra.lunchmap.entity.FootPrint;
import jp.co.isopra.lunchmap.repositories.FootPrintRepository;


@Controller
public class FootController {

	@Autowired
	FootPrintRepository repository;

	@RequestMapping("/foot")
	public String viewFoot() {
		return "foot";
}

	@RequestMapping("/foot/register")
	public String registerFoot(@RequestParam String comment,
			@AuthenticationPrincipal AccountDetails accountDetails) {

		FootPrint entity = new FootPrint();

		entity.setComment(comment);
		entity.setLogin_id(accountDetails.getMember());
		repository.save(entity);


		return "foot";
}
}