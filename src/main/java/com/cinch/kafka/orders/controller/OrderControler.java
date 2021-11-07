package com.cinch.kafka.orders.controller;


import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinch.kafka.orders.model.Orders;
import com.cinch.kafka.orders.producer.KafkaOrdersProducer;

@RestController
@RequestMapping(value = "/kafka")
public class OrderControler {

	@Autowired
	KafkaOrdersProducer kafkaOrdersProducer;

	@PostMapping(value = "/producer")
	public String produceOrders(@RequestBody Orders orders) {

		for (int i = 1; i < 20; i++) {
			orders.setOrderNumber(i);
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			orders.setTimestamp(timestamp.toString());
			kafkaOrdersProducer.sendOrders(orders);
		}

		return "Message sent to the Kafka Topic orders_topic Successfully";
	}

}
