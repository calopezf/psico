package ec.edu.puce.professorCheck.servicio;
///*
// * ClienteQueueMailService.java.
// * 
// * PFPonline 2009.
// */
//package ec.edu.puce.syllabus.servicio;
//
//import javax.annotation.Resource;
//import javax.ejb.Stateless;
//import javax.jms.Connection;
//import javax.jms.ConnectionFactory;
//import javax.jms.JMSException;
//import javax.jms.MessageProducer;
//import javax.jms.ObjectMessage;
//import javax.jms.Queue;
//import javax.jms.Session;
//
//import ec.edu.puce.syllabus.utils.MailMessage;
//
///**
// * Clase que implementa los metdos de la interfaz ClienteQueueMailServicio.java
// *
// * @author Cristian 
// * @version $1.0$
// */
//@Stateless(name = "ClienteQueueMailServicio")
//public class ClienteQueueMailServicio {
//
//	@Resource(mappedName = "java:/JmsXA")
//    private ConnectionFactory connectionFactory;
//    @Resource(mappedName = "java:jboss/jms/queue/exampleQueue")
//    private Queue queue;
//
//    /*
//     * (non-Javadoc)
//     * 
//     * @see
//     * com.pfponline.ejb.service.ClienteQueueMailServiceLocal#encolarMail(com
//     * .pfponline.util.MailMessage)
//     */
//    public void encolarMail(MailMessage message) {
//        
//        System.out.println("encola mail ...");
//        
//        Connection connection = null;
//        Session session = null;
//
//        try {
//            connection = connectionFactory.createConnection();
//            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//            MessageProducer messageProducer = session.createProducer(queue);
//
//            ObjectMessage objectMessage = session.createObjectMessage();
//            objectMessage.setObject(message);
//            messageProducer.send(objectMessage);
//            System.out.println("mail encolado ok ...");
//        } catch (JMSException e) {
//            e.printStackTrace();
//        } finally {
//            if (session != null) {
//                try {
//                    session.close();
//                    connection.close();
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}
