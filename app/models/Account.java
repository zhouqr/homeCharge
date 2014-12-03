package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * 账单条目
 * @author zhou
 *
 */
@Entity
public class Account extends Model{
	
	/**
	 * 账单日期
	 */
	@Column(nullable=false)
	public Date date;
	
	/**
	 * 花费
	 */
	@Column(nullable=false)
	public float money;
	
	/**
	 * 参与人id串
	 */
	@Column(nullable=false)
	public String userIds;
	
	/**
	 * 付款人id
	 */
	@Column(nullable=false)
	public Long payUserId;
	
	/**
	 * 账单说明
	 */
	@Column(nullable=true)
	public String introduction;
	
	/**
	 * 是否已结算，默认0，未结算
	 */
	@Column(nullable=false)
	public int isClosed=0;
	
	@Transient
	public String usernames;
	
	@Transient
	public String payUsername;
	
	public Account(){
		
	}
	
	public Account(Date date, float money, String userIds, Long payUserId, String introduction){
		this.date = date;
		this.money = money;
		this.userIds = userIds;
		this.payUserId = payUserId;
		this.introduction = introduction;
		this.isClosed = 0;
	}
	
	public String getUsernames(){
		String[] ids = this.userIds.split(",");
		String usernames="";
		for(String id: ids){
			usernames +=((User)User.findById(Long.parseLong(id))).username +",";
		}
		
		if(usernames!="")
			usernames = usernames.substring(0, usernames.length()-1);
		
		return usernames;
	}
	
	public String getPayUsername(){
		return ((User)User.findById(this.payUserId)).username;
	}
}
