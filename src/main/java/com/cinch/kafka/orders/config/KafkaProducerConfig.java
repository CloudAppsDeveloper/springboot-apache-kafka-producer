package com.cinch.kafka.orders.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.cinch.kafka.orders.model.Orders;



@Configuration
@EnableKafka
public class KafkaProducerConfig {
	
	@Value(value = "${kafka.bootstrap.address}")
    private String bootstrapAddress;

    @Value(value = "${kafka.orders.group-name-1}")
    private String groupId;
    
    @Value("${kafka.customer.consumer.topic-name1}")
    private String replyTopic1;

    @Value("${kafka.customer.consumer.topic-name2}")
    private String replyTopic2;

    @Value(value = "${kafka.customer.consumer.group-id1}")
    private String customerGroupId1;

    @Value(value = "${kafka.customer.consumer.group-id2}")
    private String customerGroupId2;
	
	@Bean
	KafkaTemplate<String, Orders> kafkaTemplate(){
	    return new KafkaTemplate<>(producerFactory());
	}
	@Bean
	public ProducerFactory<String, Orders> producerFactory(){
	    Map<String, Object> config = new HashMap<>();

	    config.put(ProducerConfig .BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	    config.put(ProducerConfig.ACKS_CONFIG, "all");
	    config.put(ProducerConfig.RETRIES_CONFIG, 0);
	    config.put(ProducerConfig.BATCH_SIZE_CONFIG, 1000);
	    config.put(ProducerConfig.LINGER_MS_CONFIG, 1);
	    config.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
	    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

	    return new DefaultKafkaProducerFactory(config);
	}
	
	 @Bean
	    public Map<String, Object> consumer1Configs() {
	        Map<String, Object> props = new HashMap<>();
	        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	        props.put(ConsumerConfig.GROUP_ID_CONFIG, customerGroupId1);
	        return props;
	    }

	    @Bean
	    public Map<String, Object> consumer2Configs() {
	        Map<String, Object> props = new HashMap<>();
	        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	        props.put(ConsumerConfig.GROUP_ID_CONFIG, customerGroupId2);
	        return props;
	    }

	    @Bean
	    public ConsumerFactory<String, String> replyConsumerFactory() {
	        return new DefaultKafkaConsumerFactory<>(consumer1Configs(), new StringDeserializer(),
	                new StringDeserializer());
	    }

	    @Bean
	    public ConsumerFactory<String, String> reply2ConsumerFactory() {
	        return new DefaultKafkaConsumerFactory<>(consumer2Configs(), new StringDeserializer(),
	                new StringDeserializer());
	    }

	    @Qualifier("listener1")
	    @Bean
	    public KafkaMessageListenerContainer<String, String> listener1() {
	        ContainerProperties containerProperties = new ContainerProperties(replyTopic1);
	        return new KafkaMessageListenerContainer<>(replyConsumerFactory(), containerProperties);
	    }

	    @Qualifier("listener2")
	    @Bean
	    public KafkaMessageListenerContainer<String, String> listener2() {
	        ContainerProperties containerProperties = new ContainerProperties(replyTopic2);
	        return new KafkaMessageListenerContainer<>(reply2ConsumerFactory(), containerProperties);
	    }
	   

	    @Bean
	    public ReplyingKafkaTemplate<String, String, String> template1(ProducerFactory<String, String> pf, KafkaMessageListenerContainer<String, String> listener1) {
	        return new ReplyingKafkaTemplate<>(pf, listener1);
	    }


	    @Bean
	    public ReplyingKafkaTemplate<String, String, String> template2(ProducerFactory<String, String> pf, KafkaMessageListenerContainer<String, String> listener2) {
	        return new ReplyingKafkaTemplate<>(pf, listener2);
	    }

}
