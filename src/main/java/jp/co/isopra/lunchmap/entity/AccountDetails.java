package jp.co.isopra.lunchmap.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AccountDetails extends User{

    //メンバー情報格納用変数
    private final Member member;

	/**
	 * コンストラクタ
	 * @param member memberテーブルのエンティティ
	 * @param authorities 認証情報
	 */
    public AccountDetails(Member member,
            Collection<GrantedAuthority> authorities) {

    	// 親クラスのコンストラクタを呼ぶ
        super(member.getLogin_id(), member.getPassword(),
                true, true, true, true, authorities);

        // DBデータをローカル変数に保持
        this.member = member;
    }

    // getter
    public Member getMember() {
        return member;
    }
}
