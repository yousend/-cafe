package com.vanilla.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CommonUtil {

	public static Map getParameter(HttpServletRequest request) {
		
		Map param = new HashMap();
		Enumeration e = request.getParameterNames();
		
		while (e.hasMoreElements()) {
			String key = (String)e.nextElement();
			String value = (String)request.getParameter(key);
			param.put(key, value);
		}

		return param;
	}
}
