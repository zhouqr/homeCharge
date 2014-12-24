package models;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import play.db.jpa.Model;

/**
 * 用户
 * @author zhou
 *
 */
@Entity
public class User extends Model{
	
	/**
	 * 用户名
	 */
	@Column(nullable=false)
	public String username;
	
	/**
	 * 密码
	 */
	@Column(nullable=false)
	public String password;
	
	/**
	 * 真实姓名
	 */
	@Column(nullable=false)
	public String name;
	
	@Transient
	public float alreadyPay;
	
	@Transient
	public float shouldPay;
	
	@Transient
	public float notPay;
	
}
