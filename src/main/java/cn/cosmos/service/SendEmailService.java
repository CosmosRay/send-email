package cn.cosmos.service;

import javax.mail.MessagingException;

/**
 * Created with CosmosRay
 *
 * @author CosmosRay
 * @date 2020/01/14
 * Funciton:
 */
public interface SendEmailService {
    /**
     *
     * @param to 目标邮箱
     * @param subject 主题
     * @param content 内容
     * @return
     */
    String sendSimpleMail(String to,String subject,String content);
    /**
     *
     * @param to 目标邮箱
     * @param subject 主题
     * @param content 内容
     * @return
     */
    String sendHtmlMail(String to,String subject,String content);
    /**
     *
     * @param to 目标邮箱
     * @param subject 主题
     * @param content 内容
     * @param filePath 文件路径
     * @return
     */
    String sendAttachmentsMail(String to,String subject,String content,String filePath);

    /**
     *
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     * @return
     */
    String sendInlineResourceMail(String to,String subject,String content,String rscPath,String rscId);
}
