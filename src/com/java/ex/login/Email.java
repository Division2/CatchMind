package com.java.ex.login;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class Email {
	
	private String authCode;
	
	public String getAuthCode() {
		return authCode;
	}

	public Email(String authEmail) {
		
		//������ȣ �������� ����
		char ranChar1 = (char) ((int)(Math.random()*(90-65+1))+65); 
		char ranChar2 = (char) ((int)(Math.random()*(90-65+1))+65); 
		int ranNum1 = (int)(Math.random()*(9-1+1))+1;
		int ranNum2 = (int)(Math.random()*(9-1+1))+1;
		int ranNum3 = (int)(Math.random()*(9-1+1))+1;
		int ranNum4 = (int)(Math.random()*(9-1+1))+1;
		
		authCode = new String("" + ranChar1 + ranChar2 + ranNum1 + ranNum2 + ranNum3 + ranNum4);
		
		//�ý��� ����
		String userName = "yjsk7015@gmail.com";
		String password = "tjdehd1@";

		// SMTP ���� ������ ����
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com"); 
		prop.put("mail.smtp.port", 465); 
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.ssl.enable", "true"); 
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	        
		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName));

			//������ �̸��� �ּ�
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(authEmail)); 

			//���� ����
			message.setSubject("[CatchMind] ȸ������ ������ȣ");

			//���� ����
			message.setText("���� ��ȣ: " + authCode); 

			//���� ���ۺκ�
			Transport.send(message);
			JOptionPane.showMessageDialog(null, "������ȣ�� ���Ϸ� ���۵Ǿ����ϴ�.", "ȸ������", JOptionPane.INFORMATION_MESSAGE);
			
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}