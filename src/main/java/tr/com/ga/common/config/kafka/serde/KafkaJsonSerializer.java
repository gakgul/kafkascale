package tr.com.ga.common.config.kafka.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

@Log4j2
public class KafkaJsonSerializer implements Serializer {
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS a z");

    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, Object o) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(df);
        try {
            String val = objectMapper.writeValueAsString(o);
            if(!StringUtils.hasLength(val))
                return null;
            return val.getBytes();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return retVal;
    }

    @Override
    public void close() {

    }
}