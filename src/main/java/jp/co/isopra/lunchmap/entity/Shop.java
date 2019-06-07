package jp.co.isopra.lunchmap.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "shop")
public class Shop {

	@Id
	@Column
	private String place_id;

	@Column
	private String place_name;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Image> image;


	public List<FootPrint> getFootprint() {
		return getFootprint();
    }

    public void setImaga(List<Image> image) {
        this.image = image;
    }

    public List<Image> getImage() {
        return image;
    }

}
