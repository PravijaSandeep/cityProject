package com.city.cw.stockadvisor.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.city.cw.stockadvisor.model.Adviser;
import com.city.cw.stockadvisor.model.RoleType;
import com.city.cw.stockadvisor.model.User;
import com.city.cw.stockadvisor.service.AdviserDetailsService;
import com.city.cw.stockadvisor.service.SubscriptionService;
import com.city.cw.stockadvisor.service.UserService;
import com.city.cw.stockadvisor.service.UserSubscriptionService;

@Controller
public class LoginController {

	@Autowired
    private  UserService userService;
	
	@Autowired
	private SubscriptionService subService;
	
	@Autowired
	private UserSubscriptionService userSubService;
	
	@Autowired
    private AdviserDetailsService adviserDetailsService;
	
	@GetMapping("/")
	public String userLogin(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
		if(oauth2User != null) {
			Map<String,Object> userData = oauth2User.getAttributes();
			System.out.println(userData.get("name"));
			System.out.println(userData.get("email"));
			String uName = userData.get("name").toString();
			
            
         // Check if the user already exists in the database
            Optional<User> existingUser = userService.findByUsername(uName);
            Set<String> subscriptions = new HashSet<>();
            
            if (existingUser.isEmpty()) {
                // User doesn't exist, create a new User entity and save it to the database
            	User user1 = new User();
                user1.setEmail(userData.get("email").toString());
                user1.setUsername(uName);
                user1.setRole(RoleType.USER);
                System.out.println("userLogin() Setting Role " + RoleType.USER);
                System.out.println("userLogin() Creating User " + user1.toString());
                userService.saveUser(user1);
            } else {
            	System.out.println("userLogin() User " + uName + "already exists.");
            	subscriptions = userSubService.getSubscriptions(existingUser.get().getId());
            	System.out.println("userLogin() User subscriptions " + subscriptions);
             }
			
			model.addAttribute("name", userData.get("name"));
			model.addAttribute("shareOptions", subService.getAllSubscriptions());
			model.addAttribute("subscriptions",subscriptions);
;			return "user-home";
		}else {
			return "home";
		}
	}

	@GetMapping("/adviser-reg")
    public String showRegistrationForm() {
        return "adviser-reg";
    }
	
	@PostMapping("/adviserReg")
	public String processRegistration(Adviser adviser, RedirectAttributes redirectAttributes) {
	    System.out.println("inside processReg");
	    adviserDetailsService.registerAdviser(adviser);
	    redirectAttributes.addFlashAttribute("registrationSuccess", true);
	    return "redirect:/adviser-login"; // Redirect to the login page after successful registration
	}
	
	@GetMapping("/adviser-login")
	public String showAdviserLogin() {
	    System.out.println("inside showAdviserLogin");
	    return "adviser-login";
	}
	
	
	@PostMapping("/adviserHome")
	public String precessAdviserLogin() {
		 System.out.println("inside processAdviserLogin");
		   
		return "adviser-home";
	}
	
	@GetMapping("/adviser-home")
	public String ShowAdviserHome() {
		return "adviser-home";
	}
	
}
