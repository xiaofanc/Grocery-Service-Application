package edu.gatech.streamingwars;

import java.util.Collections;
import java.util.Random;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class StreamingwarsApplication {

	// NOTE: autowired is a special annotation provided by the spring framework
	// for enable dependency injection

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(StreamingwarsApplication.class);
//		app.setDefaultProperties(Collections
//				.singletonMap("server.port", "3001"));
		app.run(args);
	}

}
