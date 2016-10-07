package com.vanilla.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vanilla.util.CommonUtil;
import com.vanilla.util.DateUtil;
import com.vanilla.web.dao.MoneyDao;
import com.vanilla.web.vo.MoneyVo;

@Controller
public class MoneyController {
	
	@Autowired
	MoneyDao moneyDao;
	
	@RequestMapping("/moneyLog")
	public String moneylog(
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model
	) {
		
		if (request.getMethod().equals("POST")) {
			//등록
			Map param = CommonUtil.getParameter(request);
			
			if (((String)param.get("day")).length() == 1) {
				param.put("day", "0" + param.get("day"));
			}
			
			moneyDao.insertMoneyLog(param);
		}
		
		String today = "";
		String year = "";
		String month = "";
		String day = "";
		
		if (request.getParameter("month") != null && !request.getParameter("month").equals("")) {
			year = request.getParameter("year");
			month = request.getParameter("month");
			day = request.getParameter("day");
		} else {
			today = DateUtil.getToday();
			year = today.substring(0,4);
			month = today.substring(4,6);
			day = today.substring(6,8);
		}
		
		List<MoneyVo> list = new ArrayList<MoneyVo>();
		list = moneyDao.getMoneyLog(year, month);
		
		List<MoneyVo> titleSum = new ArrayList<MoneyVo>();
		titleSum = moneyDao.getTitleSum(year, month);
		
		List<MoneyVo> plusList = new ArrayList<MoneyVo>();
		plusList = moneyDao.getPlusminusList(year, "P");
		
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("day", day);
		model.addAttribute("list", list);
		model.addAttribute("titleSum", titleSum);
		model.addAttribute("plusList", plusList);
		
		return "/moneylog/main";
		
	}
	
	@RequestMapping("/deleteMoneyLog")
	public String deleteMoneyLog(
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model
	) {
		
		Map param = CommonUtil.getParameter(request);
		moneyDao.deleteMoneyLog(param);
		
		String today = DateUtil.getToday();
		String year = today.substring(0,4);
		String month = today.substring(4,6);
		String day = today.substring(6,8);
		
		List<MoneyVo> list = new ArrayList<MoneyVo>();
		list = moneyDao.getMoneyLog(year, month);
		
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("day", day);
		model.addAttribute("list", list);
		
		return "/moneylog/main";
		
	}
	
}
