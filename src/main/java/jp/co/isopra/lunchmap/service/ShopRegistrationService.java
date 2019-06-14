package jp.co.isopra.lunchmap.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.isopra.lunchmap.entity.Shop;
import jp.co.isopra.lunchmap.repositories.ShopRepository;

@Service
@Transactional
public class ShopRegistrationService {

	@Autowired
	private ShopRepository shopRepository;

	/**
	 * 店の情報をDBに登録。
	 */
	public Shop registerShop(Shop entity) {

		//会員情報をUSERテーブルにinsert。
		entity = shopRepository.save(entity);

//		// DEBUG テーブル内データの確認
//		java.lang.System.out.println("<MEMBER TABLE>");
//		for(Member mem : memberRepository.findAll()) {
//			java.lang.System.out.println(mem.getLogin_id() + "/" + mem.getPassword());
//		}
		return entity;

	}
}

