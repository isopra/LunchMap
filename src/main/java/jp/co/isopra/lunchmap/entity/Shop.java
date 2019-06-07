package jp.co.isopra.lunchmap.entity;

import java.math.BigDecimal;
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

@Entity
@Table(name = "shop")
public class Shop {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(length = 100)
	private String place_id;

	@Column(length = 100, nullable = false)
	@NotNull
	private String place_name;

	@Column
	@NotNull
	private BigDecimal latitude;

	@Column
	@NotNull
	private BigDecimal longitude;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Footprint> footprint;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
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

	public void setFootprint(List<Footprint> footprint) {
		this.footprint = footprint;
	}

	public List<Footprint> getFootprint() {
		return footprint;
	}

	public void setImaga(List<Image> image) {
		this.image = image;
	}

	public List<Image> getImage() {
		return image;
	}

}
