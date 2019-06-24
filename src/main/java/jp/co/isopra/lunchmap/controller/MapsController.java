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

//地図の表示のみを行う
	@RequestMapping(path = "/menu/mapView" , method = RequestMethod.GET)
	public ModelAndView map(ModelAndView mav) {
		mav.setViewName("map");
		String condition = conditionSession.getCondition();
		mav.addObject("data", condition);
		return mav;
	}
	@RequestMapping(path="/sessionAdd/{placeId}/{placeName}/{lat}/{lng}" , method = RequestMethod.GET)
	public String sessionadd(
			@PathVariable String placeId,
			@PathVariable String placeName,
			@PathVariable String lat,
			@PathVariable String lng ){
		BigDecimal lat1 = new BigDecimal(lat);
		BigDecimal lng1 = new BigDecimal(lng);
		System.out.println(placeName);
//		緯度経度、名前をセッションに保存
		conditionSession.setLatitude(lat1);
		conditionSession.setLongitude(lng1);
		conditionSession.setPlacename(placeName);
		return "redirect:/shopinfo/"+ placeId;
	}
//	検索条件の保存
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
//	検索時実行
	@ResponseBody
	@RequestMapping(path="/menu/search" , method = RequestMethod.POST, produces="application/json")
	public Iterable<Shop> ajax(@RequestBody String formData,Principal principal,
			Model mav) {
//		検索条件保存
		conditionSession.setCondition("Both");
		if(formData.equals("\"locate=Exist\"")) {
			conditionSession.setCondition("Exist");
		}else if(formData.equals("\"locate=Exist&near=near\"")) {
			conditionSession.setCondition("near");
		}else if(formData.equals("\"locate=Exist&mylog=mylog\"")) {
			conditionSession.setCondition("mylog");
		}else if(formData.equals("\"locate=Exist&near=near&mylog=mylog\"")) {
			conditionSession.setCondition("both condition");
		}
		Iterable<Shop> list = null;
		return list;
	}
//	Map表示時及びMap操作時実行　画面内にあるか判定
	@ResponseBody
	@RequestMapping(path="/menu/border" , method = RequestMethod.POST, produces="application/json")
	public Iterable<Shop> ajax2(@RequestBody List<String> test_arr,Principal principal,
			Model mav) {
		BigDecimal lat1 = new BigDecimal(test_arr.get(0));
		BigDecimal lng1 = new BigDecimal(test_arr.get(1));
		BigDecimal lat2 = new BigDecimal(test_arr.get(2));
		BigDecimal lng2 = new BigDecimal(test_arr.get(3));
		Iterable<Shop> list = null;
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
//		検索条件の判定
		if(condition == null || condition.equals("Both")||condition.equals("Exist")) {
			list = shoprepo.findBylatlng((BigDecimal) lat1,(BigDecimal) lat2,(BigDecimal) lng1,(BigDecimal) lng2);
		}
		else if(condition.equals("near")) {
			List<String> time = footrepo.findByCreated_time((Date) date);
			List<String> time2 = imagerepo.findByCreated_time((Date) date);
			time2.addAll(time);
			Set<String> set = new HashSet<>(time2);
			list = shoprepo.findBylatlngAndId(set,(BigDecimal) lat1,(BigDecimal) lat2,(BigDecimal) lng1,(BigDecimal) lng2);
		}else if(condition.equals("mylog")) {
			List<String> logId =footrepo.findByLogin_id((String) Login_id);
			List<String> logId2 =imagerepo.findByLogin_id((String) Login_id);
			logId2.addAll(logId);
			Set<String> set = new HashSet<>(logId2);
			list = shoprepo.findBylatlngAndId(set,(BigDecimal) lat1,(BigDecimal) lat2,(BigDecimal) lng1,(BigDecimal) lng2);
		}else if(condition.equals("both condition")) {
			List<String> con = footrepo.findByCreated_timeAndLogin_id((Date) date,(String)Login_id);
			List<String> con2 = imagerepo.findByCreated_timeAndLogin_id((Date) date,(String)Login_id);
			con2.addAll(con);
			Set<String> set = new HashSet<>(con2);
			list = shoprepo.findBylatlngAndId(set,(BigDecimal) lat1,(BigDecimal) lat2,(BigDecimal) lng1,(BigDecimal) lng2);
		}
	return list;
	}
}