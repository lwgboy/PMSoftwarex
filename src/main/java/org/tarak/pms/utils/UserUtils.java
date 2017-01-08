package org.tarak.pms.utils;

import javax.servlet.http.HttpSession;

import org.tarak.pms.models.User;

public class UserUtils 
{
	public static boolean validSession(HttpSession session) {
		if (session.getAttribute("user") != null) {
			return true;
		}
		return false;
	}
	
	public static String getFinancialYear(HttpSession session)
	{
		if(validSession(session))
		{
			User user=(User)session.getAttribute("user");
			if(user!=null)
			{
				return user.getFinancialYear();
			}
		}
		return null;
	}
}
