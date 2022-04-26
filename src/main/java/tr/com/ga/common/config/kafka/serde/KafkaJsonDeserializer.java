package tr.com.ga.common.config.kafka.serde;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Deserializer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

@Log4j2
public class KafkaJsonDeserializer<T> implements Deserializer {
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS a z");

    private TypeReference<T> type;

    public KafkaJsonDeserializer(TypeReference type) {
        this.type = type;
    }

    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public Object deserialize(String s, byte[] bytes) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(df);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        T obj = null;
        try {
            obj = mapper.readValue(new String(bytes),type);
        } catch (Exception e) {

            log.error(e.getMessage());
        }
        return obj;
    }

    @Override
    public void close() {

    }
}