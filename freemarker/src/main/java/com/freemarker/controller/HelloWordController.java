package com.freemarker.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.fabric.xmlrpc.base.Array;

@Controller
@RequestMapping(value = "/freemarker")
public class HelloWordController {
	
	@RequestMapping(value = "/hello")
	public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
		ModelAndView mv = new ModelAndView("hello");
		mv.addObject("title", "Spring MVC And Freemarker");
		mv.addObject("content", "aaa<br/>vvv");
		Map<String,String> test = new HashMap<String,String>();
		test.put("abc", "222");
		mv.addObject("map", test);
		mv.setViewName("hello");
		List<String> list = new ArrayList<String>();
		list.add("1111");
		list.add("2222");
		mv.addObject("listtest", list);
		Map<String,List> test2 = new HashMap<String,List>();
		test2.put("abc", list);
		mv.addObject("listtest2", test2);
		return mv;
	}
}
