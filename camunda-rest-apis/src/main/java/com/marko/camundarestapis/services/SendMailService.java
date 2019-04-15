package com.marko.camundarestapis.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class SendMailService implements JavaDelegate {

	private Expression to;
	private Expression from;
	private Expression messageTxt;
	private Expression subject;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
//		System.out.println(execution.getVariable("testText"));
//		SimpleMailMessage message = new SimpleMailMessage(); 
//        message.setTo((String)to.getValue(execution)); 
//        message.setSubject((String)subject.getValue(execution)); 
//        message.setText((String)messageTxt.getValue(execution));
//        JavaMailSenderImpl impl = new JavaMailSenderImpl();
//        impl.send(message);
        
        final String username = "marko.martonosi@gmail.com";
        final String password = "Sinergy97";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtps.ssl.checkserveridentity", "true");
        props.put("mail.smtps.ssl.trust", "*");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        System.out.println("to" + to.getExpressionText());
        System.out.println("subject" + subject.getExpressionText());
        System.out.println("message" + messageTxt.getExpressionText());
        System.out.println("message mine " + execution.getVariable("mailMessage").toString());

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("smtp.gmail.com"));
            message.setRecipients(Message.RecipientType.TO, 
                                    InternetAddress.parse(to.getExpressionText()));
            message.setSubject(subject.getExpressionText());
            message.setText(execution.getVariable("mailMessage").toString());
            
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        
        
	}

	

	public Expression getTo() {
		return to;
	}



	public void setTo(Expression to) {
		this.to = to;
	}



	public Expression getFrom() {
		return from;
	}



	public void setFrom(Expression from) {
		this.from = from;
	}



	public Expression getMessageTxt() {
		return messageTxt;
	}



	public void setMessageTxt(Expression messageTxt) {
		this.messageTxt = messageTxt;
	}



	public Expression getSubject() {
		return subject;
	}



	public void setSubject(Expression subject) {
		this.subject = subject;
	}
}
