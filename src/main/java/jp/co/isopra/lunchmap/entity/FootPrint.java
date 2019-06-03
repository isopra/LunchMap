package jp.co.isopra.lunchmap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="footprint")

public class FootPrint {

	@Id
	@Column(name="comment")
	private String comment;



	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


}



