package jp.co.isopra.lunchmap.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.isopra.lunchmap.entity.Member;
import jp.co.isopra.lunchmap.service.MemberRegistrationService;

@Controller
public class MemberRegistrationController {

	@Autowired
	private MemberRegistrationService service;

	/**
	 * メンバー登録の処理
	 * @param login_id ログインID
	 * @param password パスワード
	 * @param nickname 表示用氏名
	 * @param model 遷移先画面にセットするModel
	 * @return 遷移先画面名
	 */
	@RequestMapping("/member/register/result")
	public String registerMember(
			@RequestParam String login_id,
			@RequestParam String password,
			@RequestParam String nickname, HttpServletRequest request, Model model) {

		//memberテーブルにinsertする時の引数。
		Member entity = new Member();

		entity.setLogin_id(login_id);
		entity.setPassword(password);
		entity.setNickname(nickname);

		try {
			//memberテーブルにinsertする。
			service.registerMember(entity);
		} catch (DataIntegrityViolationException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "memberRegister";
		}

		//登録完了後自動でログインさせる
		try {
			request.login(login_id, password);
			return "redirect:/menu";
		} catch (ServletException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "登録には成功しましたが、ログインに失敗しました。");
			return "login";
		}
	}

	//登録画面表示
	@RequestMapping("/member/register")
	public String showMemberRegisterForm() {
		return "memberRegister";
	}

	//アカウント情報編集
	@RequestMapping("/member/edit")
	public String editMember(
			@RequestParam String login_id,
			@RequestParam String password,
			@RequestParam String nickname
			){
		Member entity = new Member();

		entity.setLogin_id(login_id);
		entity.setPassword(password);
		entity.setNickname(nickname);

		service.updateMember(entity, false);

		return "menu";
	}

	//メンバー管理からのアカウント情報編集画面表示
	@RequestMapping(value = "/member/edit_by_manager", method = RequestMethod.POST)
	public String editMemberByManager(@RequestParam String login_id, Model model) {
		Member member = service.findMember(login_id);
		model.addAttribute("id", member.getLogin_id());
		model.addAttribute("nickname", member.getNickname());
		model.addAttribute("admin_flag", member.isAdmin_flag());
		model.addAttribute("flag",  true);

		return "accountEdit";
	}

	//メンバー管理からのアカウント情報編集
		@RequestMapping("/member/editByAdministrator")
		public String editMemberByAdministrator(
				@RequestParam String login_id,
				@RequestParam String password,
				@RequestParam String nickname,
				@RequestParam(defaultValue = "off") String admin_flag,
				Model model)
		{
			Member entity = new Member();

			entity.setLogin_id(login_id);
			entity.setPassword(password);
			entity.setNickname(nickname);

			//checkboxのvalueで分岐
			if (admin_flag.equals("on")) {
				entity.setAdmin_flag(true);
			}else {
				entity.setAdmin_flag(false);
			}

			service.updateMember(entity, true);

			model.addAttribute("allMembers", service.findAllMembers());
			return "memberManager";
		}
}
