package jp.co.isopra.lunchmap.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
	public Member registerMember(Member entity) throws DataIntegrityViolationException {

		//同一のlogin_idがないかチェック
		if (findMember(entity.getLogin_id()) != null) {
			throw new DataIntegrityViolationException("既に存在するIDです");
		}

		//パスワードをハッシュ化。
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));

		//会員情報をUSERテーブルにinsert。
		return memberRepository.save(entity);



//		// DEBUG テーブル内データの確認
//		java.lang.System.out.println("<MEMBER TABLE>");
//		for(Member mem : memberRepository.findAll()) {
//			java.lang.System.out.println(mem.getLogin_id() + "/" + mem.getPassword());
//		}
	}

	//メンバー情報のアップデート
		//ほぼ登録といっしょなのだが。。。
	public void updateMember(Member member) {
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		memberRepository.save(member);
	}

	/**
	 * IDをもとにメンバー情報を取得
	 */
	public Member findMember(String login_id) {
		Optional<Member> memberOpt = memberRepository.findById(login_id);

		if (memberOpt.isPresent()) {
			return memberOpt.get();
		}else {
			return null;
		}
	}

	//全てのメンバーを取得
	public List<Member> findAllMembers() {
		return memberRepository.findAll();
	}
}
