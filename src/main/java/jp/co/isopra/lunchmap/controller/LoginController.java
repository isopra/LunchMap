package jp.co.isopra.lunchmap.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.isopra.lunchmap.entity.AccountDetails;

@Controller
public class LoginController {

	/**
	 * ログインフォームに遷移する。
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
    public String showLoginForm(
    		@RequestParam(name="error", defaultValue="") String error,
    		Model model) {

		if(!error.equals("")) {
			// パラメータ"error"に値の指定があれば、認証エラーとみなす
			model.addAttribute("errorMessage", "認証に失敗しました");
		}

        //ログイン画面へ遷移
        return "login";
    }

    /**
     * メインページに遷移する。
     * ログインが成功した場合、このメソッドが呼び出される。
	 * @param model
	 * @return
	 */
    @RequestMapping("/")
    public String login(Model model,
    		@AuthenticationPrincipal AccountDetails accountDetails) {

    	// ログイン情報（memberテーブルのデータ）をModelにセット
    	model.addAttribute("loginMember", accountDetails.getMember());

    	return "index";
    }
}
