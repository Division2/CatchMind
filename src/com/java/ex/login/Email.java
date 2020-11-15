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
		
		//인증번호 랜덤으로 생성
		char ranChar1 = (char) ((int)(Math.random()*(90-65+1))+65); 
		char ranChar2 = (char) ((int)(Math.random()*(90-65+1))+65); 
		int ranNum1 = (int)(Math.random()*(9-1+1))+1;
		int ranNum2 = (int)(Math.random()*(9-1+1))+1;
		int ranNum3 = (int)(Math.random()*(9-1+1))+1;
		int ranNum4 = (int)(Math.random()*(9-1+1))+1;
		
		authCode = new String("" + ranChar1 + ranChar2 + ranNum1 + ranNum2 + ranNum3 + ranNum4);
		
		//시스템 계정
		String userName = "yjsk7015@gmail.com";
		String password = "tjdehd1@";

		// SMTP 서버 정보를 설정
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

			//수신자 이메일 주소
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(authEmail)); 

			//메일 제목
			message.setSubject("[CatchMind] 회원가입 인증번호");

			//메일 내용
			message.setText("인증 번호: " + authCode); 

			//메일 전송부분
			Transport.send(message);
			JOptionPane.showMessageDialog(null, "인증번호가 메일로 전송되었습니다.", "회원가입", JOptionPane.INFORMATION_MESSAGE);
			
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}