package jp.co.axa.apidemo;

import jp.co.axa.apidemo.controllers.EmployeeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@SpringBootApplication
@EnableCaching
public class ApiDemoApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiDemoApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Starting Application...");
		SpringApplication.run(ApiDemoApplication.class, args);
		LOGGER.info("Application started successfully!");
	}

}
