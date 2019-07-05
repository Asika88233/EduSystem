package com.Asika.Edusystem.controllers;

import com.Asika.Edusystem.Interceptor.ActionInterceptor;
import com.Asika.Edusystem.Interceptor.IndexInterceptor;
import com.Asika.Edusystem.services.ChangepassService;
import com.Asika.Edusystem.services.LoginService;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
@Before(ActionInterceptor.class)
public class ChangepassController extends Controller {
	public void index() {
		render("/WEB-INF/view/menu/changePass/index.html");
	}

	public void saveNewPass() {
		String newPass = getPara("newPass");
		Integer id = getSessionAttr("userid");
		int result=new ChangepassService().Changepass(newPass, id);
		System.out.println(result);
		setAttr("status",result);
		LoginController.userMap.clear();
		this.renderJson();
	}
 
}
