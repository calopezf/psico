/*
 * MailMessage.java
 *
 * Created on 13/10/2008
 * Copyright saviaSoft. All Rights Reserved.
 * SAVIASOFT cia ltda 
 * Quito-Ecuador
 * www.saviasoft.com
 */
package ec.edu.puce.professorCheck.utils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * @author Cristian
 * @version $Revision: 1.1 $
 */
public class MailMessage implements Serializable {

    private static final long serialVersionUID = 8298138942139189647L;
    private String text;
    private String from;
    private List<String> to;
    private String subject;
    private HashMap<String, File> attachList;
    /**
     * The template value map.
     */
    private Map<String, String> templateValueMap;
    private String templateName;

    /**
     * @param text
     * @param from
     * @param to
     * @param subject
     * @param attachName
     * @param attachFile
     */
    public MailMessage(String text, String from, List<String> to,
            String subject, HashMap<String, File> attachFileList) {
        super();
        this.text = text;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.attachList = attachFileList;
    }

    /**
     * @param text
     * @param from
     * @param to
     * @param subject
     * @param attachName
     * @param attachFile
     */
    public MailMessage(String text, String from, List<String> to,
            String subject, HashMap<String, File> attachFileList, boolean bol) {
        super();
        this.text = text;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.attachList = attachFileList;
    }

    /**
     * @param text
     * @param from
     * @param to
     * @param subject
     */
    public MailMessage(String text, String from, List<String> to, String subject) {
        super();
        this.text = text;
        this.from = from;
        this.to = to;
        this.subject = subject;
    }

    public MailMessage() {
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the to
     */
    public List<String> getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(List<String> to) {
        this.to = to;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Returns the value of attachList.
     *
     * @return attachList
     */
    public HashMap<String, File> getAttachList() {
        return attachList;
    }

    /**
     * Sets the value for attachList.
     *
     * @param attachList
     */
    public void setAttachList(HashMap<String, File> attachList) {
        this.attachList = attachList;
    }

    public Map<String, String> getTemplateValueMap() {
        return templateValueMap;
    }

    public void setTemplateValueMap(Map<String, String> templateValueMap) {
        this.templateValueMap = templateValueMap;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public InternetAddress getFromAddres() throws AddressException {
        return new InternetAddress(this.from);
    }

    public InternetAddress[] getToAddresses() throws AddressException {
        List<InternetAddress> resultado = new ArrayList<InternetAddress>();

        for (String t : this.to) {
            resultado.add(new InternetAddress(t));
        }

        return resultado.toArray(new InternetAddress[this.to.size()]);
    }
}
