package com.dhc.rad.common.utils.Email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMailTLS {


    public static void sendMail(String ema, String title, String content) {

         final String username = "mmis@pbts-crrc.com";
         final String password = "123.com";

         Properties props = new Properties();
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.starttls.enable", "true");
         props.put("mail.smtp.host", "mail.pbts-crrc.com");
         props.put("mail.smtp.port", "587");
//        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "mail.pbts-crrc.com");

         Session session = Session.getInstance(props,
               new javax.mail.Authenticator() {


          protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);

            }

        });
         try {
            Message message = new MimeMessage(session);
             message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(ema));
             message.setSubject(title);
             message.setText(content);

             Transport.send(message);
             System.out.println("Done");


        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
