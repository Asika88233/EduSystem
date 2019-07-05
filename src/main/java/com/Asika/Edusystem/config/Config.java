package com.Asika.Edusystem.config;

import java.io.File;

import com.Asika.Edusystem.Interceptor.LoginInterceptor;
import com.Asika.Edusystem.controllers.*;
import com.Asika.Edusystem.dao.User;
import com.Asika.Edusystem.dao.UserInfo;
import com.alibaba.fastjson.JSONArray;
import com.Asika.Edusystem.Util.JWUtil;
import com.Asika.Edusystem.dao.LoginLog;
import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;

public class Config extends JFinalConfig {
	public static JSONArray menuArray;
 
    public static void main(String[] args) {
        UndertowServer.start(Config.class, 802, true);
    }
 
    public void configConstant(Constants me) {
       me.setDevMode(true);
    }
    
    public void configRoute(Routes me) {
       me.add("/login", LoginController.class);
       me.add("/index", IndexController.class);
       me.add("/", IndexController.class);
       me.add("/menu/changepass", ChangepassController.class);
       me.add("/menu/personinfo", PersonInfoController.class);
       me.add("/menu/coursechoose", CourseChooseController.class);
    }
    
    public void configEngine(Engine me) {}
    public void configPlugin(Plugins me) {
    	String jdbcUrl="jdbc:mysql://localhost:3306/edusystem?useSSL=false&characterEncoding=utf-8&serverTimezone=GMT&allowPublicKeyRetrieval=true";
 	    DruidPlugin dp = new DruidPlugin(jdbcUrl,"root","88233");
 	    me.add(dp);
		    ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		    arp.addMapping("t_user","User_id",User.class);
		    arp.addMapping("t_loginlog",LoginLog.class);
		    arp.addMapping("t_student",UserInfo.class);
		    me.add(arp);
    }
    public void configInterceptor(Interceptors me) {
    	 me.add(new LoginInterceptor());
    }
    public void configHandler(Handlers me) {}
    public void onStart() {
		try {
			menuArray=JWUtil.readMenuToJson("admin");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
