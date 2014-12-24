package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import models.Account;
import models.Bill;
import models.User;
import models.UserAccount;
import models.UserBill;
import models.bean.ResultInfo;

import play.data.validation.Required;
import utils.JsonUtil;

/**
 * 账单
 * @author zhou
 *
 */
public class AccountService extends BaseController{
	
	/**
	 * 添加账单
	 * @param date
	 * @param money
	 * @param userIds
	 * @param payUserId
	 * @param introduction
	 */
	public static void addAccount(@Required Date date,@Required float money,@Required String userIds,@Required Long payUserId,String introduction){
		Account account = new Account(date, money, payUserId, introduction);
		if(account.save().isPersistent()){
			String[] ids = userIds.split(",");
			float orderPay = money/ids.length;
			UserAccount ua = null;
			for(String userId:ids){
				ua = new UserAccount(Long.parseLong(userId),account.id, orderPay);
				ua.save().isPersistent();
			}
			renderJSON(ResultInfo.success());
		}else
			renderJSON(ResultInfo.error());
			
	}
	
	/**
	 * 获取所有未结算账单
	 */
	public static void getAccounts(){
		List<Account> accounts = Account.find("isClosed=0").fetch();
		for(Account account:accounts){
			account.usernames = account.getUsernames();
			account.payUsername = account.getPayUsername();
		}
		renderJSON(JsonUtil.toViewJson(ResultInfo.success(accounts)));
	}
	
	/**
	 * 结算账单
	 */
	public static void chargeAll(){
		List<Long> accounts = Account.find("select id from Account where isClosed=0").fetch();
		
		List<User> users = User.findAll();
		Bill bill = new Bill();
		for(User user:users){
//			user.alreadyPay = Account.find("select sum(money) from Account where payUserId=? and isClosed=0 ", user.id);
			
		}
//		for(Account account:accounts){
//			bill.totalMoney += account.money;
//			userids = UserAccount.find("select user_id from UserAccount where account_id=?", account.id).fetch();
//			//already pay
//			if(already.containsKey(account.payUserId)){
//				already.put(account.payUserId.toString(), already.get(account.payUserId)+account.money);
//			}else{
//				already.put(account.payUserId.toString(), account.money);
//			}
//			
//			//should pay
//			for(Long s: userids){
//				if(should.containsKey(s)){
//					should.put(s, should.get(s)+account.money/userids.size());
//				}else{
//					should.put(s,account.money/userids.length);
//				}
//			}
//			
//		}
		
		//保存
		bill.save().isPersistent();
		
		List<UserBill> userBills = new ArrayList<UserBill> ();
		//计算
//		for(String id:should.keySet()){
//			UserBill userBill = new UserBill();
//			userBill.bill_id = bill.id;
//			userBill.user_id = Long.parseLong(id);
//			userBill.already_pay = already.get(id);
//			userBill.should_pay = should.get(id);
//			userBill.not_pay = userBill.already_pay - userBill.should_pay;
//			userBill.save().isPersistent();
//			userBills.add(userBill);
//		}
		
		JSONObject obj = new JSONObject();
		obj.put("bill", bill);
		obj.put("userBills", userBills);
		renderJSON(ResultInfo.success(obj));
	}
	
	public static void main(String[] args){
	}
}
