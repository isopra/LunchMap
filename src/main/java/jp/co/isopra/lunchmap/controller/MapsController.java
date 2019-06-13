package jp.co.isopra.lunchmap.controller;

import java.io.Serializable;
import java.security.Principal;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.isopra.lunchmap.entity.AccountDetails;
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

	@Autowired
	protected ConditionSession conditionSession;

	@RequestMapping(path = "/menu/mapView" , method = RequestMethod.GET)
	public ModelAndView map(Principal principal,
			ModelAndView mav) {
		mav.setViewName("map");
		Authentication authentication = (Authentication) principal; // (2)
        // get UserDetails
		AccountDetails userDetails = (AccountDetails) authentication
                .getPrincipal(); // (3)
        // get account object
		Member member =   userDetails.getMember(); // (4)
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -14);
		Date date = calendar.getTime();
		String condition = conditionSession.getCondition();
		mav.addObject("data",condition);
		System.out.println("session::" + condition);

		if(condition == null || condition.equals("Both") || condition.equals("Exist")) {
		Iterable<Shop> list = shoprepo.findAll();
		mav.addObject("datalist", list);
		}
		else if(condition.equals("near")) {
			Iterable<String> time = footrepo.findByCreated_timeGreaterThanEquals((Date) date);
			mav.addObject("datalist", time);
		}else if(condition.equals("mylog")) {
			Iterable<String> logId =footrepo.findByLogin_id((String) member.getLogin_id());
			mav.addObject("datalist", logId);
		}else if(condition.equals("both condition")) {
			Iterable<String> con = footrepo.findByCreated_timeAndLogin_id((Date) date,(String)member.getLogin_id());
			mav.addObject("datalist", con);
		}

		return mav;
	}

	@RequestMapping(path="/menu/searchView" , method = RequestMethod.POST)
	public String search(@RequestParam String locate, String near,String mylog) {
//		検索条件をセッションの保存
		conditionSession.setCondition(locate );
		if(near != null && mylog == null) {
			conditionSession.setCondition( near);
		}else if(mylog != null && near == null) {
			conditionSession.setCondition( mylog);
		}else if(mylog != null && near !=null) {
			conditionSession.setCondition("both condition");
		}

		return "redirect:mapView";
	}


	@Component
	@Scope(value= "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public class ConditionSession implements Serializable {

	      private String condition;

	      public String getCondition() {
	             return condition;
	      }

	      public void setCondition(String condition) {
	             this.condition = condition;
	      }

	}

}
