package cn.cosmos.service.impl;

import cn.cosmos.service.SendEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created with CosmosRay
 *
 * @author CosmosRay
 * @date 2020/01/14
 * Funciton:
 */
@Service
public class SendEmailServiceImpl implements SendEmailService {
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender javaMailSender;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String sendSimpleMail(String to, String subject, String content){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);

        mailMessage.setFrom(from);
        javaMailSender.send(mailMessage);

        return "successText";
    }

    @Override
    public String sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {

            helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            javaMailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "successHtml";
    }

    @Override
    public String sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {

            helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();

            helper.addAttachment(fileName,file);
            //多邮件 （这里可使用数组进行多附件的邮件发送）
            helper.addAttachment(fileName+"_test",file);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "successAttachments";
    }

    @Override
    public String sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {

            helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);

            FileSystemResource file = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId,file);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            logger.error("发送邮件失败",e);
        }
        return "successInlineResource";
    }
}
