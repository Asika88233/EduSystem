package com.Asika.Edusystem.Interceptor;

import javax.servlet.http.HttpSession;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
public class LoginInterceptor implements Interceptor {
 
	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		HttpSession session = inv.getController().getSession();
		if(session.getAttribute("username")== null){
			inv.getController().redirect("/login");
			return;
		}
       inv.invoke();
	}
}
