package com.city.cw.stockadvisor.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.city.cw.stockadvisor.model.Advice;
import com.google.cloud.spring.pubsub.PubSubAdmin;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.pubsub.v1.Topic;

@Configuration
@Service
public class PubSubService {

	private static final Log LOGGER = LogFactory.getLog(PubSubService.class);

	private final PubSubTemplate pubSubTemplate;

	private final PubSubAdmin pubSubAdmin;
	
	@Autowired
	SubscriptionService subscriptionService;
	
	@Autowired
	UserSubscriptionService userSubscriptionService;

	public PubSubService(PubSubTemplate pubSubTemplate, PubSubAdmin pubSubAdmin) {
		this.pubSubTemplate = pubSubTemplate;
		this.pubSubAdmin = pubSubAdmin;
	}


	public void createSubscriptions(Set<String> topics, String uName) {
		topics.forEach( t -> {
			createTopic(t);
			createSubscription(t);
		});
	}


	public Topic createTopic(String topicName) {
		Topic topic = this.pubSubAdmin.getTopic(topicName);
		if(topic == null) {
			topic = this.pubSubAdmin.createTopic(topicName);
			System.out.println(topicName + " topic created ");
		}
		return topic;
	}

	public void deleteTopic(String topicName) {
		Topic topic = this.pubSubAdmin.getTopic(topicName);
		if(topic != null) {
			this.pubSubAdmin.deleteTopic(topicName);
			Long subId = subscriptionService.getSubscriptionByName(topicName).getId();

			subscriptionService.deleteSubscription(topicName);
			userSubscriptionService.deleteUserSubsService(subId);
		}
	}
	private void createSubscription(String topic) {
		try {
			this.pubSubAdmin.createSubscription(topic, topic);
		}catch(Exception e) {
			LOGGER.error(e);
		}
	}

	/**
	 * Gets the list of published 
	 * share names.
	 * @return
	 */
	public Set<String> getPublishedShareNames() {
		//return this.pubSubAdmin.listTopics();
		
		Set<String> shares = new HashSet<>();
		try {
			this.pubSubAdmin.listTopics().forEach(t ->{
                String shortName = t.getName().substring(t.getName().lastIndexOf('/') + 1);
				shares.add(shortName);
			});
		}catch(Exception e) {
			LOGGER.error(e);
		}
		return shares;
	}
	
	
	public void publishMessage(String topic, String message) {
		String topicName = topic;
		if(topic.endsWith(",")) {
			topicName = topic.substring(0, topic.length() - 1);
		}else {
			 topicName = topic.substring(topic.lastIndexOf(',') + 1);
		}
		System.out.println("In publishMessage(), publishing advices to " + topicName);
		System.out.println("In publishMessage(), messagec " + message);
		String finalTopic = topicName;

		new Thread(() ->{
			createTopic(finalTopic);
			createSubscription(finalTopic);
			subscriptionService.addsubscription(finalTopic);
			this.pubSubTemplate.publish(finalTopic, message);

		}).start();
	}
	

	public Set<Advice> getAdvices(Set<String> subscriptions) {
	    System.out.println("In getAdvices(), getting advices for " + subscriptions);
	    Set<Advice> advices = Collections.synchronizedSet(new HashSet<>());


	    subscriptions.parallelStream().forEach(share -> {
	        pubSubTemplate.pull(share, 2, false)
	            .forEach(message -> {
	                // Process the received message
	                String payload = new String(message.getPubsubMessage().getData().toStringUtf8());
	                System.out.println("Received advice for share : "+ share + " advice=" + payload);
	                Advice ad = new Advice();
	                ad.setShareName(share);
	                List<String> desc = new ArrayList<>();
	                desc.add(payload);
	                ad.setAdviceList(desc);
	                advices.add(ad);
	            });
	    });

	    return advices;
	}


}
