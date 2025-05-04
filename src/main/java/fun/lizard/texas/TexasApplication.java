package fun.lizard.texas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableFeignClients
@EnableWebMvc
@EnableMongoRepositories
public class TexasApplication {

	public static void main(String[] args) {
		SpringApplication.run(TexasApplication.class, args);
	}

}
