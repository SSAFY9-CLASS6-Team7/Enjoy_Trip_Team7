package com.ssafy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class EnjoytripApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplicationBuilder()
				.sources(EnjoytripApplication.class)
				.listeners(new ApplicationPidFileWriter("/var/lib/jenkins/workspace/application.pid"))
				.build();
		application.run(args);
	}

}
