package com.Asika.Edusystem.controllers;

import java.util.List;

import com.Asika.Edusystem.Util.JWUtil;
import com.Asika.Edusystem.config.Config;
import com.Asika.Edusystem.dao.UserInfo;
import com.alibaba.fastjson.JSONArray;
import com.jfinal.core.Controller;
public class IndexController extends Controller{
	 public void index() {
		   String username =getSessionAttr("username");
		   setAttr("username", username);
			try {
				Integer type=getSessionAttr("userlevel");
				JSONArray menuArray=Config.menuArray;
			    switch (type) {
				case 1:
					menuArray=JWUtil.readMenuToJson("student");
			    	List<UserInfo> userInfo =UserInfo.dao.find("select * from t_student where User_id=?",getSessionAttr("userid"));
			    	setSessionAttr("studentid", userInfo.get(0).getStr("id"));
					break;
				case 2:
					menuArray=JWUtil.readMenuToJson("teacher");
					break;
				case 3:
					menuArray=JWUtil.readMenuToJson("admin");
					break;

				default:
					break;
				}
				System.out.println(menuArray.toJSONString());
				setAttr("menuArray", menuArray);
			} catch (Exception e) {
				// TODO: handle exception
			}
	       render("/WEB-INF/view/index/index.html");
	    }
	 public void loginout() {
		 removeSessionAttr("username");
		 LoginController.userInfos.clear();
		 LoginController.userMap.clear();
		 redirect("/login");
	 }
} 