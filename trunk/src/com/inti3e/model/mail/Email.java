package com.inti3e.model.mail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	private static String host = "smtp.gmail.com";
	private static int port = 587;
	private static String username = "EIN2g.saxion@gmail.com";
	private static String password = "EIN2grules";
	
	public static void send() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(props);

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("EIN2g.saxion@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("fabian_mengerink@hotmail.com"));
			message.setSubject("Er is een brand!");
			message.setText("Beste Fabian,\n\nEr is een brand!\nBel de brandweer!\n\nGroeten,\nDe DK51");

			Transport transport = session.getTransport("smtp");
			transport.connect(host, port, username, password);
			transport.sendMessage(message, message.getAllRecipients());

			System.out.println("Fire mail sended");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}