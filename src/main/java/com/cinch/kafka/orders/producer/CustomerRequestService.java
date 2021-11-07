package com.cinch.kafka.orders.producer;

import java.time.Duration;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;

@Service
public class CustomerRequestService {

	@Value("${kafka.customer.producer.topic-name1}")
	private String requestTopic;

	@Value("${kafka.customer.producer.topic-name2}")
	private String requestTopic2;

	@Value("${kafka.customer.consumer.topic-name1}")
	private String replyTopic;

	@Value("${kafka.customer.consumer.topic-name2}")
	private String replyTopic2;

	@Autowired
	@Qualifier("template1")
	private ReplyingKafkaTemplate<String, String, String> template1;

	@Autowired
	@Qualifier("template2")
	private ReplyingKafkaTemplate<String, String, String> template2;

	public String getCustomers(Integer customerid) {
		String idString = Integer.toString(customerid);
		try {
			System.out.println("before send1 " + System.currentTimeMillis());
			RequestReplyFuture<String, String, String> sendAndReceive1 = sendMessage(idString);
			System.out.println("before send2 " + System.currentTimeMillis());
			RequestReplyFuture<String, String, String> sendAndReceive2 = sendMessage2(idString);

			System.out.println("before response1 " + System.currentTimeMillis());
			ConsumerRecord<String, String> consumerRecord = sendAndReceive1.get();
			String reply1 = consumerRecord.value();
			System.out.println("reply1" + reply1);

			System.out.println("before response2 " + System.currentTimeMillis());
			ConsumerRecord<String, String> consumerRecord2 = sendAndReceive2.get();
			String reply2 = consumerRecord2.value();
			System.out.println("reply2" + reply2);
			System.out.println("after response2 " + System.currentTimeMillis());
			return reply1 + System.lineSeparator() + reply2;
		} catch (Exception exception) {
			System.out.println("Exception details :" + exception.getMessage());
			return "Exception while send receive";
		}

	}

	public RequestReplyFuture<String, String, String> sendMessage(String msg) throws Exception {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(requestTopic, msg);
		record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, replyTopic.getBytes()));
		RequestReplyFuture<String, String, String> sendAndReceive = template1.sendAndReceive(record,
				Duration.ofSeconds(300));
		return sendAndReceive;
	}

	public RequestReplyFuture<String, String, String> sendMessage2(String msg) throws Exception {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(requestTopic2, msg);
		record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, replyTopic2.getBytes()));
		RequestReplyFuture<String, String, String> sendAndReceive = template2.sendAndReceive(record,
				Duration.ofSeconds(300));
		return sendAndReceive;
	}

//    public String sendMessage(String msg) throws Exception{
//        ProducerRecord<String, String> record = new ProducerRecord<String, String>(requestTopic, msg);
//        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, replyTopic.getBytes()));
//        RequestReplyFuture<String, String, String> sendAndReceive =template1.sendAndReceive(record);
//        ConsumerRecord<String, String> consumerRecord = sendAndReceive.get();
//        return consumerRecord.value();
//    }
//
//    public String sendMessage2(String msg) throws Exception{
//        ProducerRecord<String, String> record = new ProducerRecord<String, String>(requestTopic2, msg);
//        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, replyTopic2.getBytes()));
//        RequestReplyFuture<String, String, String> sendAndReceive = template2.sendAndReceive(record);
//        ConsumerRecord<String, String> consumerRecord = sendAndReceive.get();
//        return consumerRecord.value();
//    }

}
