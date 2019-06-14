package jp.co.isopra.lunchmap.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "member")
public class Member {

	@Id
	@Column(name="login_id")
	private String login_id;

	@Column(name="password")
	private String password;

	@Column(name="nickname")
	private String nickname;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonManagedReference // 循環参照を止めるアノテーション
	private List<FootPrint> footprint;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonManagedReference // 循環参照を止めるアノテーション
	private List<Image> image;

	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
