package models;

import javax.persistence.Column;
import javax.persistence.Entity;

import play.db.jpa.Model;

/**
 * 用户账单对照表
 * @author zhou
 *
 */
@Entity
public class UserAccount extends Model{
	
	/**
	 * 用户id
	 */
	@Column(nullable=false)
	public Long user_id;
	
	@Column(nullable=false)
	public Long account_id;
	
	@Column(nullable=false)
	public float orderPay;
	
	public UserAccount(){
		
	}
	
	public UserAccount(Long user_id,Long account_id,float orderPay){
		this.user_id = user_id;
		this.account_id = account_id;
		this.orderPay = orderPay;
	}

}
