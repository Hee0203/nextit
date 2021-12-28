package com.study.home;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SimpleController {
	
	@RequestMapping("/abcd/eee")
	public String eee(Model model, HttpServletRequest req) {
		System.out.println("eee is executed");
		model.addAttribute("eee", "This is a Data at 'eee' is sending");
		req.setAttribute("url",req.getRequestURL());
		return "eee";
	}

	@Inject
	SimpleServiceImpl simpleService;
	
	@RequestMapping("/abcd/fff")
	public String fff(Model model) {
		System.out.println("fff is executed");
		String data = simpleService.getSimple();
		model.addAttribute("data",data);
		return "fff";
	}
	
}
