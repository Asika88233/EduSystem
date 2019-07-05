package com.Asika.Edusystem.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User>{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5216041196613732284L;
	public static final User dao=new User();
	public boolean findUserLogin(String username,String password,List<User> userlist){
		String querysql="select * from t_user where User_name=? or User_Phone=? and User_pwd=?";
		List<User> list;
		try{
			list=this.find(querysql, username,username,password);
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