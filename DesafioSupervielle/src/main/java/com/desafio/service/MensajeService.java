package com.desafio.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MensajeService {

	 @Autowired
	 MessageSource messageResource;

	 public String getMessage(String codigo) {
		 return messageResource.getMessage(codigo, null, new Locale("es", "AR"));
	 }

}
