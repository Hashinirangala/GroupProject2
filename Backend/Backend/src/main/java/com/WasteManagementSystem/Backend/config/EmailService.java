package com.WasteManagementSystem.Backend.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

@Service
public class EmailService {
	@Value("${app.sendgrid.templateId}")
	private String templateId;
	
	@Autowired
	SendGrid sendGrid;
	
	public String sendEmail(String email) {
		try {
			Mail mail = prepareMail(email);
			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sendGrid.api(request);
			if(response!=null) {
				System.out.println("response code from sendgrid" + response.getHeaders());
			}
		} catch(IOException e) {
			e.printStackTrace();
			return "Error in sent";
		}
		return "Mail has been sent check your email!";
	}
	
	public Mail prepareMail(String email) {
		Mail mail = new Mail();
		Email fromEmail = new Email();
		fromEmail.setEmail("2017cs014@stu.ucsc.cmb.ac.lk");
		Email to = new Email();
		to.setEmail(email);
		
		Personalization personalization = new Personalization();
		personalization.addTo(to);
		mail.setTemplateId(templateId);
		return mail;
	}
}
