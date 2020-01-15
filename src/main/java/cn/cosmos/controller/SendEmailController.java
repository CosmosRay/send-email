package cn.cosmos.controller;

import cn.cosmos.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created with CosmosRay
 *
 * @author CosmosRay
 * @date 2020/01/14
 * Funciton:
 */
@RestController
public class SendEmailController {

    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private TemplateEngine templateEngine;


    @RequestMapping(value = "/sendText",name = "文本邮件发送", method = RequestMethod.GET)
    @ResponseBody
    public String sendText() {
        return sendEmailService.sendSimpleMail("cosmosray@aliyun.com","我的主题","内容内容内容");
    }

    @RequestMapping(value = "/sendHtml",name = "HTML邮件发送", method = RequestMethod.GET)
    @ResponseBody
    public String sendHtml() {
        String content  = "<html><body><h3>这是一封HTML邮件</h3></body></html>";
        return sendEmailService.sendHtmlMail("cosmosray@aliyun.com","我的主题",content);
    }

    @RequestMapping(value = "/sendAttachments",name = "附件邮件发送", method = RequestMethod.GET)
    @ResponseBody
    public String sendAttachments() {
        String content  = "<html><body><h3>这是一封Attachments邮件</h3></body></html>";
        String filePath = "E:\\pdftest\\12345.xml";
        return sendEmailService.sendAttachmentsMail("cosmosray@aliyun.com","我的主题",content,filePath);
    }

    @RequestMapping(value = "/sendInlineResource",name = "图片邮件发送", method = RequestMethod.GET)
    @ResponseBody
    public String sendInlineResource() {
        String imgPath = "E:\\pdftest\\123.jpg";
        String rscId = "new001";
        String content  = "<html><body><h3>这是一封InlineResource邮件</h3><img src=\'cid:"+rscId+"\'></img></body></html>";
        return sendEmailService.sendInlineResourceMail("cosmosray@aliyun.com","我的主题",content,imgPath,rscId);
    }

    @RequestMapping(value = "/sendTemplate",name = "图片邮件发送", method = RequestMethod.GET)
    @ResponseBody
    public String sendTemplate() {
        Context context = new Context();
        context.setVariable("id","006");

        String emailContent = templateEngine.process("emailTemplate",context);

        return sendEmailService.sendHtmlMail("cosmosray@aliyun.com","模板邮件",emailContent);
    }

}
