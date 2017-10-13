package ru.gumerbaev.piano;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application's entry point.
 * @author agumerbaev
 */
@SpringBootApplication
public class PianoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PianoApplication.class, args);
	}
}
