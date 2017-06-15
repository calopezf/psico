/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.puce.professorCheck.servicio;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ec.edu.puce.professorCheck.utils.MailMessage;

/**
 *
 * @author cristian
 */
@Stateless(name = "EmailServicio")
@LocalBean
public class EmailServicio {

    //Produccion
    //@Resource(name = "mail/socioEmpleoMail")
    //Desarrollo
    //@Resource(name = "mail/socioEmpleoMailDesa")
    @Resource(name = "mail/syllabusMail")
    private Session mailSession;

    public void sender(MailMessage message) {
        try {
            InternetAddress[] toArray = new InternetAddress[message.getTo().size()];
            int i = 0;
            for (String to : message.getTo()) {
                toArray[i] = new InternetAddress(to);
                i++;
            }

            // Create the message object
            MimeMessage javaMmessage = new MimeMessage(mailSession);

            // Adjust the recipients. Here we have only one
            // recipient. The recipient's address must be
            // an object of the InternetAddress class.
            javaMmessage.setRecipients(Message.RecipientType.TO, toArray);

            // Set the message's subject
            javaMmessage.setSubject(message.getSubject());

            // Insert the message's body
            javaMmessage.setContent(message.getText(), "text/html");

            // Adjust the date of sending the message
            Date timeStamp = new Date();
            javaMmessage.setSentDate(timeStamp);

            // Use the 'send' static method of the Transport
            // class to send the message
            Transport.send(javaMmessage);
            
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
