package com.vanilla.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vanilla.util.DateUtil;
import com.vanilla.web.dao.MainDao;
import com.vanilla.web.vo.UserVo;

@Controller
public class MainController {
	
	@Autowired
	MainDao mainDao;
	
	@RequestMapping("/main")
	public String main(
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model
	) {
		
		UserVo user = mainDao.getUser("yousend@nate.com");
		
		System.out.println("id : " + user.getId());
		System.out.println("email : " + user.getEmail());
		
		return "/home/main";
		
	}
	
	@RequestMapping("/devLog")
	public String log(
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model
	) {
		
		
		return "/home/devLog";
	}
	
}
