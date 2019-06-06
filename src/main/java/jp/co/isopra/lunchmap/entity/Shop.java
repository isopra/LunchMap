package jp.co.isopra.lunchmap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="shop")
public class Shop {

	@Id
	@Column
	private String place_id;

	@Column(nullable = false)
	private String place_name;

	public String getPlace_id(){
		return place_id;
	}

	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}

	public String getPlace_name(){
		return place_name;
	}

	public void setPlace_name(String place_name) {
		this.place_name = place_name;
	}

}
