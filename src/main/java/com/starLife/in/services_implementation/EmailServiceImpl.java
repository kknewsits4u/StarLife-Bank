package com.starLife.in.services_implementation;

import java.util.Properties;

import org.springframework.stereotype.Service;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Authenticator;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Transport;
import jakarta.mail.Message;
import com.starLife.in.service.EmailService;


@Service
public class EmailServiceImpl  implements EmailService{

  @Override
  public void sendmaill(String to, String message, String subject) {

  String from = "krishnakantsharma560@gmail.com";
  String host = "smtp.gmail.com";

  // get properties.....

  Properties p = System.getProperties();

  p.put("mail.smtp.host",host);
  p.put("mail.smtp.port","465");
  p.put("mail.smtp.ssl.enable","true");
  p.put("mail.smtp.auth","true");
 
// get session

  Session session = Session.getInstance(p, new Authenticator() {
    
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("krishnakantsharma560@gmail.com", "mwao jxrs ysey tnmf");
    }
    
  });

// debug
     
  session.setDebug(true);
     
     
// mimemassage 
     
     MimeMessage m = new MimeMessage(session);
     
     try {
    	 
    	 m.setFrom(from);
    	 
    	 m.setSubject(subject);
    	 
    	 m.setText(message);
       m.setContent(message, "text/html");
    	 
    	 m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
    	 
    	 
    	 Transport.send(m);
    	 
    	 System.out.println(" Your task is done .................................");
    	 
     }catch(Exception e) {
    	 e.printStackTrace();
     }

}

  
}
