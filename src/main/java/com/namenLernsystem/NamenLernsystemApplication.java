package com.namenLernsystem;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NamenLernsystemApplication {

	public static void main(String[] args) throws URISyntaxException, IOException {
		SpringApplication.run(NamenLernsystemApplication.class, args);
		Runtime rt = Runtime.getRuntime();
		String url = "http://localhost:8080";
		rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
	}

}
