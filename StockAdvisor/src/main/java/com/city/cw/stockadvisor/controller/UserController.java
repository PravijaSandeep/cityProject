package com.city.cw.stockadvisor.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	

	@GetMapping("/user")
	public Principal user(Principal principal) {
		System.out.println("Hello " + principal.getName());
		return principal;
	}
	
	 @GetMapping("/")
	    public String loginSuccess(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
	        // Retrieve user information from OAuth2User
		 Map<String,Object> userData = oauth2User.getAttributes();
		 System.out.println(userData.get("name"));
		 System.out.println(userData.get("email"));
	        model.addAttribute("name", userData.get("name"));
	        return "home";
	    }
}
