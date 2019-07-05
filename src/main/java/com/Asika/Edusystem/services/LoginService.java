package com.Asika.Edusystem.services;

import java.util.List;

import com.Asika.Edusystem.dao.User;
public class LoginService {
	public boolean findUserLogin(String username,String password,List<User> userlist){
		String querysql="select * from user where username=? and pwd=?";
		List<User> list;
		try{
			list=User.dao.find(querysql, username,password);
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		if(list.isEmpty() || list==null){
			return false;
		}
		//将登录者数据添加到userlist集合中
		userlist.add(list.get(0));
		return true;		
	}

}
