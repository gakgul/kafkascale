package tr.com.ga.kafkasample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;

@ComponentScan({"tr.com.ga*"})
@SpringBootApplication
@EnableMongoRepositories
public class KafkasampleApplication {
	private final InitService initService;

	public KafkasampleApplication(InitService initService) {
		this.initService = initService;
	}

	public static void main(String[] args) {
		SpringApplication.run(KafkasampleApplication.class, args);
	}

	@PostConstruct
	protected void init(){
		initService.init();
	}
}
