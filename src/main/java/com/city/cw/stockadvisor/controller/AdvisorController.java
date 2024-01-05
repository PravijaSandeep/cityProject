package com.city.cw.stockadvisor.controller;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.city.cw.stockadvisor.service.PubSubService;
import com.city.cw.stockadvisor.service.SubscriptionService;
import com.city.cw.stockadvisor.service.UserSubscriptionService;

@Controller
public class AdvisorController {

    
    @Autowired
	PubSubService pubsubService;
    
    @Autowired
    SubscriptionService subscriptionService;
    
    
    @Autowired
    UserSubscriptionService userSubService;

    
    @GetMapping("/publish-message")
    public String showPublishMessageForm(Model model,RedirectAttributes redirectAttributes) throws IOException {
    	System.out.println("***showPublishMessageForm()***");
    	Set<String> topics = pubsubService.getPublishedShareNames();
        model.addAttribute("topics", topics);
        redirectAttributes.addFlashAttribute("publishSuccess", true);
        
    	return "publish-message";
    }



    @PostMapping("/publishMessage")
    public String publishMessage(Model model, RedirectAttributes redirectAttributes,@RequestParam String topicName, 
    		@RequestParam String stockMsg
    		){
    	System.out.println("TopicName :" + topicName);
    	this.pubsubService.publishMessage(topicName, stockMsg);
    	Set<String> topics = pubsubService.getPublishedShareNames();
        model.addAttribute("topics", topics);
        redirectAttributes.addFlashAttribute("publishSuccess", true);
        
        return "redirect:/publish-message";
    }
    


@GetMapping("/manage-stock")
public String manageStock(Model model) throws IOException {
	Set<String> topics = pubsubService.getPublishedShareNames();
    model.addAttribute("topics", topics);
    
    return "manage-stock";
	}

@PostMapping("/create-stock")
public String createStock(@RequestParam("topicName") String topicName, Model model) {
	pubsubService.createTopic(topicName);
	Set<String> topics = pubsubService.getPublishedShareNames();
    model.addAttribute("topics", topics);
    
    return "manage-stock";
	}

@PostMapping("/delete-stock")
public String deleteStock(@RequestParam("topicName") String topicName, Model model){
	pubsubService.deleteTopic(topicName);
	Set<String> topics = pubsubService.getPublishedShareNames();
    model.addAttribute("topics", topics);
    
    return "manage-stock";
	}


}
