package com.city.cw.stockadvisor.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.city.cw.stockadvisor.model.Advice;
import com.city.cw.stockadvisor.service.PubSubService;
import com.city.cw.stockadvisor.service.UserSubscriptionService;



@Controller
public class UserController {
	
	@Autowired
	UserSubscriptionService userSubService;
	
	@Autowired
	PubSubService pubsubService;
	
	
	@PostMapping("/submitShares")
    public String submitShares(@RequestParam(value = "selectedShares", required = false) String[] selectedShares,
    		RedirectAttributes redirectAttributes) {
        // Process the selected shares here
		System.out.println("submitShares() called selectedShares: " + selectedShares.length );
		if (selectedShares != null) {
            for (String share : selectedShares) {
                System.out.println("Selected share: " + share);
                // Perform actions with the selected shares, e.g., store in database, process, etc.
            }
         // Retrieve the authentication object from the security context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
                Map<String,Object> userData = oauth2User.getAttributes();
    			System.out.println(userData.get("name"));
    			System.out.println(userData.get("email"));
    			userSubService.saveUserSubscription(userData.get("name").toString(), Set.of(selectedShares));
    			pubsubService.createSubscriptions(Set.of(selectedShares), userData.get("name").toString());
            }
            
            redirectAttributes.addAttribute("selectedShares",selectedShares);
            
        }
        System.out.println("redirecting: " );

        return "redirect:/share-advices"; 
    }
	
	
	@GetMapping("share-advices")
	public String getAdvices(@RequestParam(value="selectedShares",required = false) String[] selectedShares, Model model) {
        System.out.println("In getAdvices() selectedShares : " + selectedShares );
        
		Set<Advice> advises = pubsubService.getAdvices(Set.of(selectedShares));
        System.out.println("advises : " + advises );
		model.addAttribute("advices", advises);
		return "share-advices";
	}
	

}
