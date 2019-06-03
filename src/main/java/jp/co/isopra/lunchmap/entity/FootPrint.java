package jp.co.isopra.lunchmap.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="footprint")

public class FootPrint {

	// あしあとID
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "footprint_id")
	private long footprint_id;


	// 登録日時
	@Column(name = "created_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;
	
	public void setDate(Date date) {
		this.datetime = date;
	}

	// コメント
	@Column(name="comment")
	private String comment;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@ManyToMany(mappedBy = "shop")
	private FootPrint footPrint;






}



