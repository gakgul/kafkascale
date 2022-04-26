package tr.com.ga.common.config.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import tr.com.ga.common.config.kafka.serde.KafkaJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

public class KafkaConsumerConfigBase {
    @Value("${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    private String isolationLevel="read_committed";
    private int maxPollRecords=1;
    private String autoOffsetReset="earliest";
    @Value("${kafka.maxPollIntervalMs}")
    private Integer maxPollIntervalMs;
    @Value("${kafka.requestTimeoutMs}")
    private Integer requestTimeoutMs;
    @Value("${kafka.sessionTimeoutSec}")
    private Integer sessionTimeoutSec;

    public <T> ConsumerFactory<String, T> consumerFactory(TypeReference<T> clzz, final String groupId) {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, new KafkaJsonDeserializer<T>(clzz).getClass());
        return getStringEventCommandConsumerFactory(clzz,props, groupId);
    }
    private <T> ConsumerFactory<String, T> getStringEventCommandConsumerFactory(TypeReference<T> clzz,Map<String, Object> props, String groupId) {
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, "20971520");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, isolationLevel);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalMs);
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeoutMs);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutSec * 1000);
        return new DefaultKafkaConsumerFactory<>(props,new StringDeserializer(),new KafkaJsonDeserializer<>(clzz));
    }


    public <T> ConcurrentKafkaListenerContainerFactory<String, T> kafkaListenerContainerFactory(TypeReference<T> clzz,String groupId) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(clzz,groupId));
        return factory;
    }



}