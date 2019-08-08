package io.pivotal.notifier;

import com.google.gson.Gson;
import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.StringUtils;

import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
@Log
public class NotifierFunction {

	@Autowired
	public JavaMailSender emailSender;

	@Bean
	public Function<Message, String> emails() {
		return message -> {
			Gson gson = new Gson();
			String json = gson.toJson(message);
			log.info("Received: " + json);
			if(!StringUtils.isEmpty(message.getBody())) {
				SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
				simpleMailMessage.setTo(message.getTo());
				simpleMailMessage.setSubject(message.getSubject());
				simpleMailMessage.setText(message.getBody());
				simpleMailMessage.setFrom(message.getFrom());
				emailSender.send(simpleMailMessage);
				log.info("Sent email to " + message.getTo());
				return "Sent email to " + message.getTo();
			} else {
				log.info("Suppressing email, no body.");
				return "Suppressing email, no body.";
			}
		};
	}

}
