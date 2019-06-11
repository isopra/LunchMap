package jp.co.isopra.lunchmap.controller;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.isopra.lunchmap.entity.AccountDetails;
import jp.co.isopra.lunchmap.entity.FootPrint;
import jp.co.isopra.lunchmap.entity.Member;
import jp.co.isopra.lunchmap.entity.Shop;
import jp.co.isopra.lunchmap.repositories.FootPrintRepository;
import jp.co.isopra.lunchmap.repositories.ShopRepository;



@Controller
public class MapsController {

	@Autowired
	 ShopRepository shoprepo;

	@Autowired
	FootPrintRepository footrepo;

	@RequestMapping(path = "/menu/mapView" , method = RequestMethod.GET)
	public ModelAndView map(ModelAndView mav) {
		mav.setViewName("map");
		Iterable<Shop> list = shoprepo.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(path="/menu/searchView" , method = RequestMethod.GET)
	public ModelAndView search(ModelAndView mav) {
		mav.setViewName("search");
		return mav;
	}

	@RequestMapping(path="/menu/searchView" , method = RequestMethod.POST)
	public ModelAndView send(@RequestParam String locate, String near,String mylog,
			Principal principal,
			ModelAndView mav) {
		mav.setViewName("searchmap");
		mav.addObject("data", locate);
		Authentication authentication = (Authentication) principal; // (2)
        // get UserDetails
		AccountDetails userDetails = (AccountDetails) authentication
                .getPrincipal(); // (3)
        // get account object
		Member member =   userDetails.getMember(); // (4)
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -14);
		Date date = calendar.getTime();
		Iterable<FootPrint> list = footrepo.findAll();//Shopは後でFootPrintに変更
		mav.addObject("datalist", list);
		System.out.println(locate);
		System.out.println(near);
		System.out.println(mylog);

		if(near != null && mylog == null) {
			mav.addObject("data", near);
			Iterable<FootPrint> time = footrepo.findByCreated_timeGreaterThanEquals((Date) date);
			mav.addObject("datalist", time);
		}else if(mylog != null && near == null) {
			mav.addObject("data", mylog);
			Iterable<FootPrint> logId =footrepo.findByLogin_id((String) member.getLogin_id());
			mav.addObject("datalist", logId);
		}else if(mylog != null && near !=null) {
			mav.addObject("data", "both condition");
			Iterable<FootPrint> con = footrepo.findByCreated_timeAndLogin_id((Date) date,(String)member.getLogin_id());
			mav.addObject("datalist", con);
		}

		return mav;
	}

}
