package jp.co.isopra.lunchmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.isopra.lunchmap.entity.Footprint;
import jp.co.isopra.lunchmap.repositories.FootprintRepository;


@Controller
public class FootController {

	@Autowired
	FootprintRepository repository;

	@RequestMapping("/foot")
	public String viewFoot() {

		return "foot";
}

	@RequestMapping("/foot/register")
	public String registerFoot(@RequestParam String comment) {

		Footprint entity = new Footprint();

		entity.setComment(comment);
		repository.save(entity);

		//memberテーブルにinsertする。
		// service.registerMember(entity);

		return "foot";
}
}