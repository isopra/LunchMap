package jp.co.isopra.lunchmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.isopra.lunchmap.entity.FootPrint;
import jp.co.isopra.lunchmap.repositories.FootPrintRepository;


@Controller
public class FootController {

	@Autowired
	FootPrintRepository repository;

	@RequestMapping("/foot")

	public ModelAndView view(ModelAndView mav) {

		mav.setViewName("foot");
		Iterable<FootPrint> List = repository.findAll();

		return mav;

	}





}
