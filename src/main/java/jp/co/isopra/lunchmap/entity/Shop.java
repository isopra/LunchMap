package jp.co.isopra.lunchmap.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "shop")
public class Shop {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(length = 100)
	private String place_id;

	@Column(length = 100, nullable = true)
	@NotNull
	private String place_name;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonManagedReference // 循環参照を止めるアノテーション
	private List<FootPrint> footprint;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonManagedReference // 循環参照を止めるアノテーション
	private List<Image> image;

	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}

	public String getPlace_id() {
		return place_id;
	}

	public void setPlace_name(String place_name) {
		this.place_name = place_name;
	}

	public String getPlace_name() {
		return place_name;
	}

	public void setFootprint(List<FootPrint> footprint) {
		this.footprint = footprint;
	}

	public List<FootPrint> getFootprint() {
		return footprint;
	}

	public void setImage(List<Image> image) {
		this.image = image;
	}

	public List<Image> getImage() {
		return image;
	}

}
