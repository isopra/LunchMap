package jp.co.isopra.lunchmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.isopra.lunchmap.entity.Shop;
import jp.co.isopra.lunchmap.repositories.ShopRepository;



@Controller
public class MapsController {

	@Autowired
	private ShopRepository repository;

	@RequestMapping(path = "/menu/map" , method = RequestMethod.GET)
	public ModelAndView map(ModelAndView mav) {
		mav.setViewName("map");
		Iterable<Shop> list = this.repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

}
