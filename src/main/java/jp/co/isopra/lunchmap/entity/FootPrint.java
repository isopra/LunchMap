package jp.co.isopra.lunchmap.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="footprint")

public class FootPrint {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer footprint_id;

	@Column(nullable=false)
	private String place_id;

	@Column(nullable=false)
	private String login_id;

	@Column(nullable = true)
	private String comment;


	@Column(nullable=false)
	private Date created_time;

	public FootPrint() {
		this.created_time  = new Date();
	}


	public Integer getFootprint_id() {
		return footprint_id;
	}

	public void setFootprint_id(Integer footprint_id) {
		this.footprint_id = footprint_id;
	}


	public String getPlace_id() {
		return place_id;
	}

	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}


	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}


	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}



}



