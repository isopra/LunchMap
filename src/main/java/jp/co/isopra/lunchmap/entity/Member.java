package jp.co.isopra.lunchmap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "member")
public class Member {

	@Id
	@Column(name="login_id")
	private String login_id;

	@Column(name="password")
	@NotNull
	private String password;

	@Column(name="nickname")
	@NotNull
	private String nickname;

	@Column(name = "admin_flag", columnDefinition = "boolean default false")
	private Boolean admin_flag;

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
