package com.uikoo9.z.interceptor;

import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.uikoo9.manage.ac.model.AcAccountModel;
import com.uikoo9.manage.pro.model.ProDetailModel;
import com.uikoo9.util.jfinal.QInterceptor;
import com.uikoo9.z.MyContants;

public class MyInterceptor extends QInterceptor{

	@Override
	public void init(Controller controller) {
		super.init(controller);
		initProDetails(controller);
		initAccounts(controller);
	}
	
	/**
	 * 缓存项目明细列表
	 * @param controller
	 */
	private void initProDetails(Controller controller){
		controller.setAttr("proDetails", ProDetailModel.dao.findAllByCache());
	}
	
	/**
	 * 缓存账户列表
	 * @param controller
	 */
	private void initAccounts(Controller controller){
		controller.setAttr("accounts", AcAccountModel.dao.findAllByCache());
	}

	@Override
	public boolean authVisit(ActionInvocation ai) {
		Record user = ai.getController().getAttr("user");
		
		if(user == null){
			return false;
		}else{
			String type = user.getStr("user_type");
			if(type.equals(MyContants.USER_TYPE_CUSTOM) && !ai.getActionKey().startsWith("/ac")){
				return false;
			}else{
				return true;
			}
		}

	}

}
