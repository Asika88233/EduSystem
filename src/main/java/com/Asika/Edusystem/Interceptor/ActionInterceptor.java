package com.Asika.Edusystem.Interceptor;

import javax.servlet.http.HttpSession;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class ActionInterceptor  implements Interceptor{

	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		HttpSession session = inv.getController().getSession();
		String user = (String) session.getAttribute("username");
		String action =inv.getController().getRequest().getServletPath();
         if(user!=null) {
        		System.out.println("拦截成功");
         }else {
        	 inv.getController().redirect("/login");
         }
		System.out.println(user+"执行了"+action.replaceAll("/menu/", ""));
		  inv.invoke();
	}
}
