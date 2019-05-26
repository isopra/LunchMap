package jp.co.isopra.lunchmap.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.isopra.lunchmap.entity.Member;
import jp.co.isopra.lunchmap.repositories.MemberRepository;

@Service
@Transactional
public class MemberRegistrationService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * メンバー情報をDBに登録。
	 */
	public Member registerMember(Member entity) {

		//パスワードをハッシュ化。
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));

		//会員情報をUSERテーブルにinsert。
		entity = memberRepository.save(entity);

//		// DEBUG テーブル内データの確認
//		java.lang.System.out.println("<MEMBER TABLE>");
//		for(Member mem : memberRepository.findAll()) {
//			java.lang.System.out.println(mem.getLogin_id() + "/" + mem.getPassword());
//		}
		return entity;

	}
}
