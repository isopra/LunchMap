package jp.co.isopra.lunchmap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="footprint")

public class FootPrint {

	/* あしあとID
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "footprint_id")
	private long footprint_id;


	/* 登録日時
	@Column(name = "created_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;

	public void setDate(Date date) {
		this.datetime = date;
	} */

	// コメント
	@Column(name="comment")
	private String comment;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

/*	@ManyToMany(mappedBy = "shop")
	private FootPrint footPrint; */






}



