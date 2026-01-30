package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.smart.entities.User;
import com.smart.service.SmartService;

import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

@Controller
public class HomeController {
	@Autowired
	private SmartService smartService;
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home-Smart Contact Manager");
		return "home";
	}

	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About-Smart Contact Manager");
		return "about";
	}

	@GetMapping("/signup")
	public String Signup(Model model,User user) {
		model.addAttribute("title", "SignUp-Smart Contact Manager");
		 model.addAttribute("user", new User());
		return "signup";
	}

	@GetMapping("/signin")
	public String login(Model model) {
		model.addAttribute("title", "Login-Smart Contact Manager");
		return "signin";
	}

	@PostMapping("/signup")
	public String registerUser(@ModelAttribute User user, Model model) {
		String result = smartService.registerUser(user);

		if (result.equals("User registered successfully")) {
			//return "redirect:/login"; // Redirect after successful signup
			return "redirect:/signin";
		} else {
			model.addAttribute("error", result);
			return "signup"; // Stay on signup page, show error
		}
	}


}
