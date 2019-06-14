package jp.co.isopra.lunchmap.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="shop")
public class Shop {

	@Id
	@Column
	private String place_id;

	@Column(name = "place_name", nullable = false)
	private String place_name;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
	private List<FootPrint> FootPrintList;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
	private List<Image> imageList;


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
