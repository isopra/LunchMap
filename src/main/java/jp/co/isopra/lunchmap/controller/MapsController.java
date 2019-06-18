package jp.co.isopra.lunchmap.controller;

import java.io.Serializable;
import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import jp.co.isopra.lunchmap.repositories.FootPrintRepository;
import jp.co.isopra.lunchmap.repositories.ImageRepository;
import jp.co.isopra.lunchmap.repositories.MemberRepository;
import jp.co.isopra.lunchmap.repositories.ShopRepository;



@Controller
public class MapsController {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	 ShopRepository shoprepo;

	@Autowired
	FootPrintRepository footrepo;

	@Autowired
	ImageRepository imagerepo;

	@Autowired
	protected ConditionSession conditionSession;

	@RequestMapping(path = "/menu/mapView" , method = RequestMethod.GET)
	public ModelAndView map(Principal principal,
			ModelAndView mav) {
		mav.setViewName("map");
//		ログイン情報の取得
		Authentication auth = (Authentication) principal;
		AccountDetails accountDetails = (AccountDetails) auth.getPrincipal();
		String Login_id = accountDetails.getMember().getLogin_id();
//		時間の取得
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -14);
		Date date = calendar.getTime();
//		セッション情報の取得
		String condition = conditionSession.getCondition();
		mav.addObject("data",condition);
		System.out.println("session::" + condition);
//		検索条件の判定
		if(condition == null || condition.equals("Both") || condition.equals("Exist")) {
		Iterable<String> list = shoprepo.findByplace_id();
		mav.addObject("datalist", list);
		}
		else if(condition.equals("near")) {
			List<String> time = footrepo.findByCreated_time((Date) date);
			List<String> time2 = imagerepo.findByCreated_time((Date) date);
			time2.addAll(time);
			Set<String> set = new HashSet<>(time2);
			System.out.println(set);
			mav.addObject("datalist", set);
		}else if(condition.equals("mylog")) {
			List<String> logId =footrepo.findByLogin_id((String) Login_id);
			List<String> logId2 =imagerepo.findByLogin_id((String) Login_id);
			logId2.addAll(logId);
			Set<String> set = new HashSet<>(logId2);
			mav.addObject("datalist", set);
		}else if(condition.equals("both condition")) {
			List<String> con = footrepo.findByCreated_timeAndLogin_id((Date) date,(String)Login_id);
			List<String> con2 = imagerepo.findByCreated_timeAndLogin_id((Date) date,(String)Login_id);
			con2.addAll(con);
			Set<String> set = new HashSet<>(con2);
			mav.addObject("datalist", set);
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
