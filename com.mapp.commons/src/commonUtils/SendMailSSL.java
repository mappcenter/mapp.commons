/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonUtils;

import com.nct.framework.common.LogUtil;
import com.nct.framework.util.ConvertUtils;
import commonUtils.CommonUtils.AES256Cipher;
import java.io.File;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;
/**
 *
 * @author liempt
 */
public class SendMailSSL {
    public static Logger logger = LogUtil.getLogger(SendMailSSL.class);
    private static final String key = "abcdefghijklmnop";
    private static SendMailSSL instance;
    private static final String mailUsername = "socdientu";
    private String mailPassword = "ugR3kgqUBFIxD5RcN8ggxQ==";
    private final Properties props;
    public static String mailFrom = "socdientu@gmail.com";
    public static String mailTo = "liempt@nct.vn";
    
    public static String mail_SMTP_Host = "smtp.gmail.com";
    public static String mail_SMTP_SSL_Port = "465";
    public static String mail_SMTP_Auth = "true";

    public static SendMailSSL getInstance() {
        if (instance == null) {
            instance = new SendMailSSL();
        }
        return instance;
    }
    
    public static void main(String[] args) {
//        System.out.println(AES256Cipher.AES_Encode("j!c^n9d^0c", key));
        SendMailSSL sendmail = SendMailSSL.getInstance();
        sendmail.sendText("liempt@nct.vn", "Alooooo", "Ollaaaaaa", null);
    }

    private SendMailSSL() {
        if (CommonUtils.IsNullOrEmpty(mailUsername) || CommonUtils.IsNullOrEmpty(mailPassword)) {
            logger.info("Config FAIL: [mail.username/mail.password: <null> or empty]");
        }
        mailPassword = AES256Cipher.AES_Decode(mailPassword, key);
        props = new Properties();

        props.put("mail.smtp.host", mail_SMTP_Host);
        props.put("mail.smtp.socketFactory.port", mail_SMTP_SSL_Port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", mail_SMTP_Auth);
        props.put("mail.smtp.port", mail_SMTP_SSL_Port);
    }

    /**
     * Send Email via Gmail SMTP server using SSL connection with plain text
     * content.
     *
     * @param to list email address will be sent to (distinguished by
     * comma(<b>,</b>))
     * @param subject
     * @param content (plain text)
     * @param attachments
     */
    public void sendText(String to, String subject, String content, List<String> attachments) throws RuntimeException {
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailUsername, mailPassword);
            }
        });
        try {
            if (to.contains(";")) {
                to = to.replaceAll(";", ",");
            }
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailFrom));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            if (attachments == null || attachments.isEmpty()) {
                message.setText(content);
            } else {
                // Create the message part 
                BodyPart messageBodyPart = new MimeBodyPart();
                // Fill the message
                messageBodyPart.setText(content);

                // Create a multipar message
                Multipart multipart = new MimeMultipart();

                // Set text message part
                multipart.addBodyPart(messageBodyPart);

                for (String entry : attachments) {
                    try {
                        // Part two is attachment
                        messageBodyPart = new MimeBodyPart();
                        String filename = "";
                        try {
                            filename = entry.substring(entry.lastIndexOf(File.separator) + 1);
                        } catch (Exception e) {
                        }
                        DataSource source = new FileDataSource(entry);
                        messageBodyPart.setDataHandler(new DataHandler(source));
                        messageBodyPart.setFileName(filename.isEmpty() ? entry : filename);
                        multipart.addBodyPart(messageBodyPart);
                    } catch (Exception e) {
                        logger.error("Add Attachment to mail message FAIL", e);
                    }
                }

                // Send the complete message parts
                message.setContent(multipart);
            }

            Transport.send(message);

        } catch (MessagingException e) {
            logger.error(LogUtil.stackTrace(e));
        }
    }

    /**
     * Send Email via Gmail SMTP server using SSL connection with HTML content.
     *
     * @param to list email address will be sent to (distinguished by
     * comma(<b>,</b>))
     * @param subject
     * @param content (text/html)
     * @param attachments
     */
    public void sendHTML(String to, String subject, String content, List<String> attachments) throws RuntimeException {
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailUsername, mailPassword);
            }
        });
        try {
            if (to.contains(";")) {
                to = to.replaceAll(";", ",");
            }
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailFrom));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            if (attachments == null || attachments.isEmpty()) {
                message.setContent(content, "text/html");
            } else {
                // Create the message part 
                BodyPart messageBodyPart = new MimeBodyPart();
                // Fill the message
                messageBodyPart.setContent(content, "text/html");

                // Create a multipar message
                Multipart multipart = new MimeMultipart();

                // Set text message part
                multipart.addBodyPart(messageBodyPart);

                for (String entry : attachments) {
                    try {
                        // Part two is attachment
                        messageBodyPart = new MimeBodyPart();
                        String filename = "";
                        try {
                            filename = entry.substring(entry.lastIndexOf(File.separator) + 1);
                        } catch (Exception e) {
                        }
                        DataSource source = new FileDataSource(entry);
                        messageBodyPart.setDataHandler(new DataHandler(source));
                        messageBodyPart.setFileName(filename.isEmpty() ? entry : filename);
                        multipart.addBodyPart(messageBodyPart);
                    } catch (Exception e) {
                        logger.error("Add Attachment to mail message FAIL", e);
                    }
                }

                // Send the complete message parts
                message.setContent(multipart);
            }

            Transport.send(message);
        } catch (MessagingException e) {
            logger.error(LogUtil.stackTrace(e));
        }
    }
}
