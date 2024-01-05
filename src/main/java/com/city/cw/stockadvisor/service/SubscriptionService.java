package com.city.cw.stockadvisor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.city.cw.stockadvisor.model.Subscription;
import com.city.cw.stockadvisor.repository.SubscriptionRepository;

@Service
public class SubscriptionService {
	
	@Autowired
	SubscriptionRepository subRepository;
	
	public void saveSubscriptions(List<Subscription> subList) {
		
		subRepository.saveAll(subList);
	}
	
	public Subscription getSubscriptionByName(String subName) {
		return subRepository.findByName(subName);
	}

	public List<Subscription> getAllSubscriptions() {
		return subRepository.findAll();
	}
	
	public void addsubscription(String topic) {
		Subscription sub = new Subscription();
		sub.setName(topic);
		if(subRepository.findByName(topic) == null) {
			subRepository.save(sub);
		}
	}
	
	public void deleteSubscription(String topicName) {
		 Subscription sub = subRepository.findByName(topicName);
		System.out.println("indelete subscripton: "+sub.getName()+"  subId= "+sub.getId());
		if(sub!= null) {
			subRepository.deleteById(sub.getId());
		}
	}
}
