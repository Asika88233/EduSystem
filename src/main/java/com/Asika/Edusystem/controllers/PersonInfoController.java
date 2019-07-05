package com.Asika.Edusystem.controllers;

import java.util.List;

import com.Asika.Edusystem.dao.User;
import com.Asika.Edusystem.dao.UserInfo;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;

public class PersonInfoController  extends Controller{
	public void index() {
		 setAttr("username", getSessionAttr("username"));
		 User user = User.dao.findById(getSessionAttr("userid"));
		 setAttr("email", user.getStr("User_Email"));
		 setAttr("phone", user.getStr("User_Phone"));
		 List<UserInfo> userInfo =UserInfo.dao.find("select * from t_student where User_id=?",getSessionAttr("userid"));
		 setAttr("stuid", userInfo.get(0).getStr("id"));
		 setAttr("stuname", userInfo.get(0).getStr("stu_name"));
		 setAttr("stusex", userInfo.get(0).getStr("stu_sex"));
		 setAttr("birthday", userInfo.get(0).getStr("stu_birthday"));
		 System.out.println(userInfo.get(0).getStr("stu_id"));
		render("/WEB-INF/view/menu/personInfo/index.html");
	}
	public void saveNewInfo() {
		System.out.println(getPara("phoneNum"));
		int result=Db.update("update t_user set User_Email=? ,User_Phone=? where User_id=?", getPara("email"),getPara("phoneNum"),getSessionAttr("userid"));
		int reuslt2=Db.update("update t_student set stu_birthday=? where User_id=?",getPara("birthDay"),getSessionAttr("userid"));
		if(result==0||reuslt2==0) {
			result=0;
		}
		setAttr("status",result);
		this.renderJson();
	}
}
