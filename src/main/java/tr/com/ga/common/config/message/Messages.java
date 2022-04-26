package tr.com.ga.common.config.message;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;


@Component
@Log4j2
public class Messages {

    @Autowired @Qualifier("customMessageSource") private MessageSource messageSource;

    private MessageSourceAccessor accessor;

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource, Locale.forLanguageTag("en-US"));
    }

    public String get(String code) {
        try {
            return accessor.getMessage(code);
        }catch (Throwable t){
            log.error("No message found for this code \"{}\" ",code);
        }
        return code;
    }
}