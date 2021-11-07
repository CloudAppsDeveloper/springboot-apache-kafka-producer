package com.cinch.kafka.orders.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.cinch.kafka.orders.model.Orders;

@Service
public class KafkaOrdersProducer {

	@Autowired
	private KafkaTemplate<String, Orders> kafkaTemplate;
	
	
	@Value(value = "${kafka.orders.topic-name}")
    private String ordersTopicName;


	public void sendOrders(Orders order) {

		ListenableFuture<SendResult<String, Orders>> future = kafkaTemplate.send(ordersTopicName, order);
		
		future.addCallback(new ListenableFutureCallback<SendResult<String, Orders>>() {
			 
	        @Override
	        public void onSuccess(SendResult<String, Orders> result) {
	            System.out.println("Sent message=[" + order + 
	              "] with offset=[" + result.getRecordMetadata().offset() + "]");
	        }
	        @Override
	        public void onFailure(Throwable ex) {
	            System.out.println("Unable to send message=[" 
	              + order + "] due to : " + ex.getMessage());
	        }
	    });
	}

}
