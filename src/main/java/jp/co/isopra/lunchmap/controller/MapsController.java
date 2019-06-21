package jp.co.isopra.lunchmap.controller;

import java.io.Serializable;
import java.math.BigDecimal;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import jp.co.isopra.lunchmap.entity.AccountDetails;
import jp.co.isopra.lunchmap.entity.Shop;
import jp.co.isopra.lunchmap.repositories.FootPrintRepository;
import jp.co.isopra.lunchmap.repositories.ImageRepository;
import jp.co.isopra.lunchmap.repositories.MemberRepository;
import jp.co.isopra.lunchmap.repositories.ShopRepository;



@Controller
@SessionAttributes("scopedTarget.ConditionSession")
public class MapsController {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	 ShopRepository shoprepo;

	@Autowired
	FootPrintRepository footrepo;

	@Autowired
	ImageRepository imagerepo;

	/*sessionを使う場合必要----------------------------*/
	@Autowired
	protected ConditionSession conditionSession;
	/*------------------------------------------------*/


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
		System.out.println("session::" + conditionSession.getLatitude());
		System.out.println("session::" + conditionSession.getLongitude());
		System.out.println(conditionSession.getPlacename());
//		検索条件の判定
		if(condition == null || condition.equals("Both") || condition.equals("Exist")) {
		Iterable<Shop> list = shoprepo.findAll();
		mav.addObject("datalist", list);
		}
		else if(condition.equals("near")) {
			List<String> time = footrepo.findByCreated_time((Date) date);
			List<String> time2 = imagerepo.findByCreated_time((Date) date);
			time2.addAll(time);
			Set<String> set = new HashSet<>(time2);
			System.out.println(set);
			Iterable<Shop> neartime = shoprepo.findByid(set);
			mav.addObject("datalist", neartime);
		}else if(condition.equals("mylog")) {
			List<String> logId =footrepo.findByLogin_id((String) Login_id);
			List<String> logId2 =imagerepo.findByLogin_id((String) Login_id);
			logId2.addAll(logId);
			Set<String> set = new HashSet<>(logId2);
			Iterable<Shop> mylog = shoprepo.findByid(set);
			mav.addObject("datalist", mylog);
		}else if(condition.equals("both condition")) {
			List<String> con = footrepo.findByCreated_timeAndLogin_id((Date) date,(String)Login_id);
			List<String> con2 = imagerepo.findByCreated_timeAndLogin_id((Date) date,(String)Login_id);
			con2.addAll(con);
			Set<String> set = new HashSet<>(con2);
			Iterable<Shop> both = shoprepo.findByid(set);
			mav.addObject("datalist",both );
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

	@RequestMapping(path="/sessionAdd/{placeId}/{placeName}/{lat}/{lng}" , method = RequestMethod.GET)
	public String sessionadd(
			@PathVariable String placeId,
			@PathVariable String placeName,
			@PathVariable String lat,
			@PathVariable String lng ){

		BigDecimal lat1 = new BigDecimal(lat);
		BigDecimal lng1 = new BigDecimal(lng);
//		double lat1 =Double.parseDouble(lat);
//		double lng1 =Double.parseDouble(lng);
		System.out.println(placeName);
//		緯度経度、名前をセッションに保存
		conditionSession.setLatitude(lat1);
		conditionSession.setLongitude(lng1);
		conditionSession.setPlacename(placeName);



		return "redirect:/shopinfo/"+ placeId;
	}


	@Component
	@Scope(value= "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public class ConditionSession implements Serializable {

	      private String condition;
	      private String placename;
	      private BigDecimal latitude;
	      private BigDecimal longitude;



	      public String getCondition() {
	             return condition;
	      }

	      public void setCondition(String condition) {
	             this.condition = condition;
	      }
	      public String getPlacename() {
	             return placename;
	      }

	      public void setPlacename(String placename) {
	             this.placename = placename;
	      }
	      public BigDecimal getLatitude() {
	             return latitude;
	      }

	      public void setLatitude(BigDecimal latitude) {
	             this.latitude = latitude;
	      }
	      public BigDecimal getLongitude() {
	             return longitude;
	      }

	      public void setLongitude(BigDecimal longitude) {
	             this.longitude = longitude;
	      }

	}
//		ajax確認用
	@ResponseBody
	@RequestMapping(path="/menu/search" , method = RequestMethod.POST, produces="application/json")
	public Iterable<Shop> ajax(@RequestBody String formData,Principal principal,
			Model mav) {

		conditionSession.setCondition("Both");
		System.out.println("----------------");
//		System.out.println("ajax=" +formData);
		System.out.println("----------------");
		if(formData.equals("\"locate=Exist\"")) {
			System.out.println("ajax=Exist true" );
			conditionSession.setCondition("Exist");
			System.out.println("----------------");
		}else if(formData.equals("\"locate=Exist&near=near\"")) {
			System.out.println("ajax=locate=Exist&near=near" );
			conditionSession.setCondition("near");
			System.out.println("----------------");
		}else if(formData.equals("\"locate=Exist&mylog=mylog\"")) {
			System.out.println("ajax=Exist&mylog=mylog" );
			conditionSession.setCondition("mylog");
			System.out.println("----------------");
		}else if(formData.equals("\"locate=Exist&near=near&mylog=mylog\"")) {
			System.out.println("ajax=Exist&near=near&mylog=mylog" );
			conditionSession.setCondition("both condition");
			System.out.println("----------------");
		}
		Iterable<Shop> list = null;
		Authentication auth = (Authentication) principal;
		AccountDetails accountDetails = (AccountDetails) auth.getPrincipal();
		String Login_id = accountDetails.getMember().getLogin_id();
//		時間の取得
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -14);
		Date date = calendar.getTime();
//		セッション情報の取得
		String condition = conditionSession.getCondition();
//		mav.addObject("data",condition);

		System.out.println("session::" + condition);
		System.out.println("session::" + conditionSession.getLatitude());
		System.out.println("session::" + conditionSession.getLongitude());
		System.out.println(conditionSession.getPlacename());
//		検索条件の判定
		if(condition == null || condition.equals("Both") || condition.equals("Exist")) {
		list = shoprepo.findAll();
		System.out.println("-----------------------");
		System.out.println(list);
		System.out.println("-----------------------");
//		mav.addAttribute("datalist", list);
		}
		else if(condition.equals("near")) {
			List<String> time = footrepo.findByCreated_time((Date) date);
			List<String> time2 = imagerepo.findByCreated_time((Date) date);
			time2.addAll(time);
			Set<String> set = new HashSet<>(time2);
			System.out.println(set);
			list = shoprepo.findByid(set);
//			mav.addAttribute("datalist", neartime);
		}else if(condition.equals("mylog")) {
			List<String> logId =footrepo.findByLogin_id((String) Login_id);
			List<String> logId2 =imagerepo.findByLogin_id((String) Login_id);
			logId2.addAll(logId);
			Set<String> set = new HashSet<>(logId2);
			list = shoprepo.findByid(set);
//			mav.addObject("datalist", mylog);
		}else if(condition.equals("both condition")) {
			List<String> con = footrepo.findByCreated_timeAndLogin_id((Date) date,(String)Login_id);
			List<String> con2 = imagerepo.findByCreated_timeAndLogin_id((Date) date,(String)Login_id);
			con2.addAll(con);
			Set<String> set = new HashSet<>(con2);
			list= shoprepo.findByid(set);
//			mav.addObject("datalist",both );
		}
		System.out.println("-----------------------");
		System.out.println(list.getClass());
		System.out.println(list);
		System.out.println("-----------------------");

		return list;
	}

//	画面内か判定
//	@ResponseBody
//	@RequestMapping(path="/menu/border" , method = RequestMethod.POST, produces="application/json")
//	public Iterable<Shop> ajax2(@RequestBody List<BigDecimal> latlng,Principal principal,
//			Model mav) {
//
//		return list;
//	}

}