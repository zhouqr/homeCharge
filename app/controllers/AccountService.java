package controllers;

import java.util.Date;
import java.util.List;

import models.Account;
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
}
