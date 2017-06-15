package ec.edu.puce.professorCheck.jms;
///*
// * AutomaticoMDB.java
// * Created on 13/10/2008
// * Copyright saviaSoft. All Rights Reserved.
// * SAVIASOFT cia ltda 
// * Quito-Ecuador
// * www.saviasoft.com
// */
//package ec.edu.puce.syllabus.jms;
//
//import javax.ejb.ActivationConfigProperty;
//import javax.ejb.EJB;
//import javax.ejb.MessageDriven;
//import javax.jms.JMSException;
//import javax.jms.Message;
//import javax.jms.MessageListener;
//import javax.jms.ObjectMessage;
//
//import ec.edu.puce.syllabus.servicio.EmailServicio;
//import ec.edu.puce.syllabus.utils.MailMessage;
//
///**
// * @author Juan Ochoa
// * @author Daniel Cardenas
// * @version $Revision: 1.2 $
// */
//@MessageDriven(mappedName = "jms/MailSyllabusQueue", activationConfig = {
//    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
//    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")})
//public class AutomaticoMDB implements MessageListener {
//
//    @EJB
//    private EmailServicio emailServicio;
//
//    /**
//     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
//     */
//    @Override
//    public void onMessage(Message message) {
//        System.out.println("procesa cola ...");
//        if (message instanceof ObjectMessage) {
//            try {
//                ObjectMessage objectMessage = (ObjectMessage) message;
//                Object obj = objectMessage.getObject();
//                if (obj instanceof MailMessage) {
//                    System.out.println("procesa email servicio ... ");
//                    MailMessage mailMessage = (MailMessage) obj;
//                    emailServicio.sender(mailMessage);
//                }
//            } catch (JMSException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
