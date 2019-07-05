package com.Asika.Edusystem.services;

import java.util.List;

import com.Asika.Edusystem.dao.User;
import com.jfinal.plugin.activerecord.Db;
/**
 * 
 * @author Asika
 * @retrun 0 修改出现问题 1 修改成功 2原密码与新密码相同
 */
public class ChangepassService {
    public  int Changepass(String newPass,int userId) {
    	String querySql ="select User_pwd from t_user where User_id=?";
    	try{
			List<User> list = User.dao.find(querySql, userId);
			String oriPass =list.get(0).getStr("User_pwd");
			System.out.println(oriPass);
			if(oriPass.equals(newPass)) {
				return 2;
			}else {
				int result=Db.update("update t_user set User_pwd=? where User_id=?", newPass, userId);
				return result;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return 0;
		}
	}
}
