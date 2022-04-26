package tr.com.ga.common.config.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import tr.com.ga.common.config.kafka.dto.EventDto;
import tr.com.ga.kafkasample.dto.CommentDto;
import tr.com.ga.kafkasample.dto.PostDto;

@EnableKafka
@Configuration
public class KafkaConsumerConfig extends KafkaConsumerConfigBase {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EventDto<CommentDto>> commentConsumerFactory() {
        return kafkaListenerContainerFactory(new TypeReference<>() {},"commentConsumerFactory1");
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EventDto<PostDto>> postConsumerFactory() {
        return kafkaListenerContainerFactory(new TypeReference<>() {},"postConsumerFactory");
    }
}
