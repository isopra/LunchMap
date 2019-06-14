package jp.co.isopra.lunchmap.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<FootPrint> footPrintList;

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
}
