package models;

import javax.persistence.Column;
import javax.persistence.Transient;

import play.db.jpa.Model;

/**
 * 结算后每个用户的账单
 * @author zhou
 *
 */
public class UserBill extends Model{
	@Column
	public Long bill_id;
	
	@Column
	public Long user_id;
	
	/**
	 * 已付
	 */
	@Column
	public float already_pay;
	
	/**
	 * 应付
	 */
	@Column
	public float should_pay;
	
	/**
	 *还应付 
	 */
	@Column
	public float not_pay;
	
	@Transient
	public String username;
	
	public String getUsername(){
		return ((User)(User.findById(this.user_id))).username;
	}
}
