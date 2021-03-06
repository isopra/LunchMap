package jp.co.isopra.lunchmap.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "shop")
public class Shop {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(length = 100)
	private String place_id;

	@Column(length = 100, nullable = false)
	@NotNull
	private String place_name;

	@Column(precision=38, scale=35, nullable = false)
	@NotNull
	private BigDecimal latitude;

	@Column(precision=38, scale=35, nullable = false)
	@NotNull
	private BigDecimal longitude;

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

	public String getPlace_name(){
		return place_name;
	}

	public void setPlace_name(String place_name) {
		this.place_name = place_name;
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

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

}
