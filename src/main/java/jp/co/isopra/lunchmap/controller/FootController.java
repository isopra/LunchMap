package jp.co.isopra.lunchmap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FootController {

	@RequestMapping("/foot")
	
	public ModelAndView view(ModelAndView mav) {
		
		mav.setViewName("foot");
		
		return mav;
	
	}
	
	



}
