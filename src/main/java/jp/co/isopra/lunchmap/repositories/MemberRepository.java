package jp.co.isopra.lunchmap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.isopra.lunchmap.entity.Member;

/**
 * Memberテーブルへアクセスするためのリポジトリクラス
 * @author masumi.sato
 *
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
}
