package commons.test.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * http://commons.apache.org/proper/commons-email/userguide.html
 * <p>
 * 异常：http://help.163.com/09/1224/17/5RAJ4LMH00753VB8.html
 * <p>
 *     TODO 发送失败会退回到发送人，或者自行设定。因此，需要根据收件箱判断是否发送成功！！！
 * Created by zengbin on 2017/12/20.
 */
public class EmailTest {
    // http://help.163.com/09/1223/14/5R7P6CJ600753VB8.html
    private static final String hostname = "smtp.163.com";
    private static final int stmpPort = 25;//default
    private static final String sslStmpPort = "465";//163 smtp、ssl：465/994
    private static final String username = "xxx@163.com";//TODO
    private static final String password = "xxx";//TODO
    private static final String target = "xxx";//TODO

    //A simple text email
    @Test
    public void simpleEmail(){
        Email email = new SimpleEmail();
        //
        email.setHostName(hostname);
        email.setSmtpPort(stmpPort);
        email.setSslSmtpPort(sslStmpPort);
        email.setAuthenticator(new DefaultAuthenticator(username, password));
        email.setSSLOnConnect(true);

        try{
            email.setFrom(username);
            email.setSubject("Test Mail");
            email.setMsg("This is a test mail ... :-)");
            email.addTo(target);
            String send = email.send();
            System.out.println(send);
        } catch(EmailException e){
            e.printStackTrace();
        }
    }

    //Sending emails with attachments
    //You can also use EmailAttachment to reference any valid URL for files that you do not have locally.
    //When the message is sent, the file will be downloaded and attached to the message automatically.
    @Test
    public void attachmentFromLocalPath(){
        // 创建附件
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath("D:/下载/图片/timg.jpg"); //附件路径 TODO
        attachment.setDisposition(EmailAttachment.ATTACHMENT); //默认就是附件模式，也可以INLINE模式
        attachment.setDescription("Picture of Watch");
        attachment.setName("Watch.jpg");//附件的名字

        // 创建邮件消息
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName(hostname);
        email.setSmtpPort(stmpPort);
        email.setAuthenticator(new DefaultAuthenticator(username, password));
        email.setSSLOnConnect(true);

        try{
            email.addTo(target, "ZHANG Shaokun");
            email.setFrom(username, "ME ME ME");
            email.setSubject("The picture");
            email.setMsg("Here is the picture you wanted");

            // 添加附件
            email.attach(attachment);

            // 发送
            email.send();
        } catch(EmailException e){
            e.printStackTrace();
        }
    }

    //Sending emails with attachments
    @Test
    public void attachmentFromUrl() throws MalformedURLException{
        // Create the attachment
        EmailAttachment attachment = new EmailAttachment();
        //远程附件 - 会先下载下来，再添加到附件发送
        attachment.setURL(new URL("http://www.apache.org/images/asf_logo_wide.gif"));
        attachment.setDisposition(EmailAttachment.ATTACHMENT);//default; another is inline
        attachment.setDescription("Apache logo");
        attachment.setName("Apache logo.gif");

        // Create the email message
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName(hostname);
        email.setSmtpPort(stmpPort);
        email.setAuthentication(username, password);
        email.setSSLOnConnect(true);
        try{
            email.addTo(target, "John Doe");
            email.setFrom(username, "Me~");
            email.setSubject("The logo");
            email.setMsg("Here is Apache's logo");

            // add the attachment
            email.attach(attachment);

            // send the email
            email.send();
        } catch(EmailException e){
            e.printStackTrace();
        }
    }

    //Sending HTML formatted email ()
    @Test
    public void html() throws EmailException, MalformedURLException{ //FIXME 163：邮件内容包含了未被许可的信息，或被系统识别为垃圾邮件
        // Create the email message
        HtmlEmail email = new HtmlEmail();
        email.setHostName(hostname);
        email.setSmtpPort(stmpPort);
        email.setAuthentication(username, password);
        email.setSSLOnConnect(true);
        email.addTo(target, "John Doe");
        email.setFrom(username, "Me");
        email.setSubject("Test email with inline image");

        // embed the image and get the content id （远程附件？）
        URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
        String cid = email.embed(url, "Apache logo"); //用于inline内容，见下行

        // set the html message
        email.setHtmlMsg("<html>The apache logo - <img src=\"cid:" + cid + "\"></html>");

        // set the alternative message
        email.setTextMsg("Your email client does not support HTML messages");

        // send the email
        email.send();
    }

    //Sending HTML formatted email with embedded images
    @Test
    public void html2() throws MalformedURLException, EmailException{ //FIXME 163：邮件内容包含了未被许可的信息，或被系统识别为垃圾邮件
        // load your HTML email template
        String htmlEmailTemplate = ".... <img src=\"http://www.apache.org/images/feather.gif\"> ....";

        // define you base URL to resolve relative resource locations
        URL url = new URL("http://www.apache.org");

        // create the email message
        ImageHtmlEmail email = new ImageHtmlEmail();
        email.setDataSourceResolver(new DataSourceUrlResolver(url)); //设置数据源解析器？从URL中解析？？？
        email.setHostName(hostname);
        email.setSmtpPort(stmpPort);
        email.setAuthentication(username, password);
        email.setSSLOnConnect(true);

        email.addTo(target, "John Doe");
        email.setFrom(username, "Me");
        email.setSubject("Test email with inline image");

        // set the html message
        email.setHtmlMsg(htmlEmailTemplate);

        // set the alternative message
        email.setTextMsg("Your email client does not support HTML messages");

        // send the email
        email.send();
    }
}
