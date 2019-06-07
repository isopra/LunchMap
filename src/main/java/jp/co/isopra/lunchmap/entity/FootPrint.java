package jp.co.isopra.lunchmap.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="footprint")
public class FootPrint {

	/**
	 * あしあとID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long footprint_id;

	/**
	 * 社員
	 */
	@ManyToOne
	private Member login_id;

	/**
	 * TODO: shopとの関連付
	 */
	@ManyToOne
	private Shop shop;


	/**
	 * コメント
	 */
	@Column(name="comment")
	private String comment;

	/**
	 *  登録日時
	 */
	@Column(name = "created_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;

	public void setLogin_id(Member member) {
		this.login_id = member;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
