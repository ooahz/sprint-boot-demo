package email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

/**
 * @author ahzoo
 * @create 2022/2/11
 * @desc 邮件发送
 */
@RestController
public class Email {

    //    构建邮件发送对象
    @Autowired
    JavaMailSender javaMailSender;

    /**
     * 发送简单邮件
     *
     * @return success
     */
    @PostMapping("/send")
    public String sendEmail() {
        // 构建一个邮件对
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件主题
        message.setSubject("测试邮件");
        // 设置邮件发送者，与配置文件中的邮箱保持一致
        message.setFrom("ooahz@qq.com");
        // 设置邮件接收者，可以有多个接收者，中间用逗号隔开，以下类似
        // message.setTo("123@qq.com","999qq.com");
        message.setTo("ooahz@qq.com");
        // 设置邮件抄送人，可以有多个抄送人
//        message.setCc("999@163.com");
        // 设置隐秘抄送人，可以有多个
//        message.setBcc("999@outlook.com");
        // 设置邮件发送日期
        message.setSentDate(new Date());
        // 设置邮件的正文
        message.setText("这是一封测试邮件————from Ahzoo");
        // 发送邮件
        javaMailSender.send(message);
        return "发送成功";
    }

    /**
     * 携带附件发送
     *
     * @return result
     */
    @PostMapping("/sendAttachment")
    public String sendFile() {
        // 使用MimeMessage对象发送复杂邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // 使用MimeMessageHelper对邮件进行配置，true表示构建一个可以带附件的邮件对象
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("测试附件");
            helper.setFrom("ooahz@qq.com");
            helper.setTo("ooahz@qq.com");
//        helper.setCc("999@163.com");
//        helper.setBcc("999@outlook.com");
            helper.setSentDate(new Date());
            helper.setText("这是一封测试邮件————from Ahzoo");
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            // 第一个参数是自定义的名称，后缀需要加上，第二个参数是文件的位置
            helper.addAttachment("file.txt", new File(path + "/static/z.txt"));
            javaMailSender.send(mimeMessage);
            return "发送成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "发送失败" + e.getMessage();
        }
    }

    /**
     * 携带静态资源发送
     *
     * @return
     */
    @PostMapping("sendResource")
    public String sendImg() {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("测试静态资源");
            helper.setFrom("ooahz@qq.com");
            helper.setTo("ooahz@qq.com");
//        helper.setCc("999@163.com");
//        helper.setBcc("999@outlook.com");
            helper.setSentDate(new Date());
            // 设置邮件正文内容。cid为占位符，对应下面的contentId。true表示正文为Html
            helper.setText("<p>图片1：</p><img src='cid:image1'/><p>图片2：</p><img src='cid:image2'/>", true);
            // contentId：对应上面占位符中的cid。file：静态资源路径

            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            helper.addInline("image1", new File(path + "/static/1.png"));
            helper.addInline("image2", new File(path + "/static/ahzoo.jpg"));
            javaMailSender.send(mimeMessage);
            return "发送成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "发送失败" + e.getMessage();
        }
    }




}
