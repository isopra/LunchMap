package jp.co.isopra.lunchmap.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.isopra.lunchmap.entity.Member;
import jp.co.isopra.lunchmap.repositories.MemberRepository;

@Service
@Transactional
public class MemberRegistrationService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private EntityManagerFactory factory;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * メンバー情報をDBに登録。
	 */
	@Transactional(readOnly = false)
	public Member registerMember(Member entity) throws DataIntegrityViolationException {

		//同一のlogin_idがないかチェック
		if (findMember(entity.getLogin_id()) != null) {
			throw new DataIntegrityViolationException("既に存在するIDです");
		}

		//パスワードをハッシュ化。
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));

		//会員情報をUSERテーブルにinsert。
		EntityManager manager = factory.createEntityManager();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		manager.persist(entity);
		memberRepository.flush();
		tx.commit();
		return entity;
	}

	//メンバー情報のアップデート
	@Transactional(readOnly = false)
	public Member updateMember(Member member, boolean is_edit_by_administrator) {
		member.setPassword(passwordEncoder.encode(member.getPassword()));

		//更新処理
		EntityManager manager = factory.createEntityManager();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Member org =  manager.find(Member.class, member.getLogin_id());
		org.setNickname(member.getNickname());
		org.setPassword(member.getPassword());

		if (is_edit_by_administrator) {
			org.setAdmin_flag(member.isAdmin_flag());
		}

		memberRepository.flush();
		tx.commit();
		return org;
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
