package com.Asika.Edusystem.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.Asika.Edusystem.dao.User;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.Asika.Edusystem.Interceptor.*;
import com.Asika.Edusystem.bean.UserInfos;
@Clear
@Before(IndexInterceptor.class)
public class LoginController extends Controller{
	public static Map<String,UserInfos> userInfos = new HashMap<String, UserInfos>();
	public static Map<String,List<Object>> userMap = new HashMap<String, List<Object>>();//缓存
	 public void index() {
	       render("/WEB-INF/view/login/Login.html");
	    }
	 public void login(){
			//获取登录者信息查询
			String username=getPara("username");	
			String password=getPara("password");
			List<User> userInfo=new ArrayList<User>();
			Iterator<Entry<String, List<Object>>> iterator = userMap.entrySet().iterator();
			if(!userMap.isEmpty()) {
				while(iterator.hasNext()) {
					System.out.println("调用了缓存方法");
					Map.Entry map = (Map.Entry) iterator.next();
					List<Object>list=(List<Object>) map.getValue();
					for (Object i :list) {
						if(i.toString().equals(username)&&map.getKey().equals(password)) {
							Date date = new Date();
							SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
							String time=dateFormat.format(date);
							//记录登录者登录信息
							String sql="insert into t_loginlog (User_name,Login_time)values(?,?)";
							//更新登陆信息
							//设置代表登录状态的信息
							for (Object j :list) {
								if(userInfos.get(j.toString())!=null) {
									this.setSessionAttr("username", userInfos.get(j.toString()).getUserName());
									this.setSessionAttr("userlevel", userInfos.get(j.toString()).getUserLevel());
									this.setSessionAttr("userid", userInfos.get(j.toString()).getUserId());
									this.setSessionAttr("userphone",userInfos.get(j.toString()).getUserPhone());
								}
							}
						}
						if(this.getSessionAttr("username")!=null) {
							setAttr("status",0);
						}
						else {
							setAttr("status",-1);
						}
						this.renderJson();
					}
			}
	}
			else {
			boolean bflag = User.dao.findUserLogin(username, password, userInfo);
			System.out.println("访问了数据库");
			//如果数据能查出来保持会话更新登陆时间并将登录者信息响应回去
			if(bflag){
				String userName=userInfo.get(0).getStr("User_name");
				//由于可以使用电话和用户名登陆，这里的userName是真正的用户名
				Integer userlevel = userInfo.get(0).getInt("User_level");
				Integer userid = userInfo.get(0).getInt("User_id");
				String userphone = userInfo.get(0).getStr("User_Phone");
				//获得从数据库中查询到的数据信息
				Date date = new Date();
				SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
				String time=dateFormat.format(date);
				//创建当前时间以字符串形式返回
				String ip = getRequest().getRemoteHost();
				System.out.println(ip);
				//记录登录者登录信息
				String sql="insert into t_loginlog (User_name,Login_time)values(?,?)";
				//更新登陆信息
				Db.update(sql,userName,time);
				//设置代表登录状态的信息
				setAttr("status",0);
				//储存登录者信息于请求中
				List<Object>list = new ArrayList<Object>();
				list.add(userName);
				list.add(userphone);
				userMap.put(password, list);
				UserInfos info =new UserInfos();
				info.setUserId(userid);
				info.setUserLevel(userlevel);
				info.setUserName(userName);
				info.setUserPhone(userphone);
				userInfos.put(info.getUserName(),info);
				this.setSessionAttr("username", userName);
				this.setSessionAttr("userlevel", userlevel);
				this.setSessionAttr("userid", userid);
				this.setSessionAttr("userphone",userphone);
				System.out.println(userlevel);
				//将登录者信息保存到会话session中
			}else{
				//如果失败登录状态为-1代表
				setAttr("status",-1);
			}
			//响应请求
			this.renderJson();
		}
	 }
}
