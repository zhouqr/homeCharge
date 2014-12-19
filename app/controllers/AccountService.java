package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import models.Account;
import models.Bill;
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
		Account account = new Account(date, money, userIds, payUserId, introduction);
		if(account.save().isPersistent()){
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
		List<Account> accounts = Account.find("isClosed=0").fetch();
		Map<String,Float> already = new HashMap<String,Float>();
		Map<String,Float> should = new HashMap<String,Float>();
		Map<String,Float> notpay = new HashMap<String,Float>();
		String[] userids; 
		Bill bill = new Bill();
		for(Account account:accounts){
			bill.totalMoney += account.money;
			userids = account.userIds.split(",");
			//already pay
			if(already.containsKey(account.payUserId)){
				already.put(account.payUserId.toString(), already.get(account.payUserId)+account.money);
			}else{
				already.put(account.payUserId.toString(), account.money);
			}
			
			//should pay
			for(String s: userids){
				if(should.containsKey(s)){
					should.put(s, should.get(s)+account.money/userids.length);
				}else{
					should.put(s,account.money/userids.length);
				}
			}
			
		}
		
		//保存
		bill.save().isPersistent();
		
		List<UserBill> userBills = new ArrayList<UserBill> ();
		//计算
		for(String id:should.keySet()){
			UserBill userBill = new UserBill();
			userBill.bill_id = bill.id;
			userBill.user_id = Long.parseLong(id);
			userBill.already_pay = already.get(id);
			userBill.should_pay = should.get(id);
			userBill.not_pay = userBill.already_pay - userBill.should_pay;
			userBill.save().isPersistent();
			userBills.add(userBill);
		}
		
		JSONObject obj = new JSONObject();
		obj.put("bill", bill);
		obj.put("userBills", userBills);
		renderJSON(ResultInfo.success(obj));
	}
}
