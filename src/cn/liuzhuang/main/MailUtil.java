package cn.liuzhuang.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.stlz.util.config.PropertiesUtil;

/**
 * 邮件发送工具类
 */
public class MailUtil {

	private static final Logger log = Logger.getLogger(MailUtil.class);
	// 属性文件配置相对路径
	public static String filePath = "config/config.properties";

	public static void sendEmailWithAttachments(String host, String port,
			final String userName, final String password, String[] mailToArgs,
			String subject, String message, String[] attachFiles)
			throws AddressException, MessagingException {

		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", userName);
		properties.put("mail.password", password);

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};
		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(userName));

		InternetAddress[] toAddress = new InternetAddress[mailToArgs.length];
		int i = 0;
		for (String ads : mailToArgs) {
			toAddress[i++] = new InternetAddress(ads);
		}
		msg.setRecipients(Message.RecipientType.TO, toAddress);

		msg.setSubject(subject);
		msg.setSentDate(new Date());

		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html;charset=utf-8");

		// creates multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// adds attachments
		if (attachFiles != null && attachFiles.length > 0) {
			for (String filePath : attachFiles) {
				MimeBodyPart attachPart = new MimeBodyPart();

				try {
					attachPart.attachFile(filePath);
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				multipart.addBodyPart(attachPart);
			}
		}

		// sets the multi-part as e-mail's content
		msg.setContent(multipart);

		// sends the e-mail
		Transport.send(msg);

	}

	private static boolean datasCheck(String server, String email,
			String addresses) {
		// check for server
		// check for email
		// check for addresses
		return true;
	}
	
	/**
	 * 正式使用的Mail方法
	 * 
	 * 参数具体：
	 * args[0]:邮件标题;
	 * args[1]:邮件正文;
	 * args[2]:邮件附件物理文件的绝对路径;
	 * 
	 * 执行Demo: java -jar mailSender.jar 邮件标题  邮件正文 附件1(要使用绝对路径)
	 *     注意: 如果有多于1个附件的场合，通过在config.properties中修改attach.files=c:/a.txt,c:/b.txt来添加;
	 * 
	 */
	public static void main(String[] args) {

		if (args.length < 3) {
			System.out.println("参数个数不正确,程序未执行.");
			System.exit(0);
		}

		// 解析配置文件
		Map<String, String> config = PropertiesUtil.readProperties(filePath);
		String host = config.get("host");// 邮件服务器（SMTP）
		String port = config.get("port");// 邮箱服务器（PORT）
		String mailFrom = config.get("mailFrom");// 发件箱
		String password = config.get("password");// 密码
		String mailTo = config.get("mailTo");// 收件箱

		String subject = "测试邮件New";
		String message = "这是一封由javaMail自动发出的测试邮件，请勿回复。";
		subject = args[0];
		message = args[1];

		if (!datasCheck(host, mailFrom, mailTo))
			return;
		String[] mailToArgs = mailTo.split(",");

		// 附件
		List<String> attachList = new ArrayList<String>();
		attachList.add(args[2]);

		String[] attachs = ((String) config.get("attach.files")).split(",");
		for (String vle : attachs) {
			attachList.add(vle);
		}
		String[] attachFiles = (String[]) attachList.toArray(new String[0]);

		try {
			log.info("准备邮件发送......");
			log.info("邮件发送至：" + mailTo);
			sendEmailWithAttachments(host, port, mailFrom, password,
					mailToArgs, subject, message, attachFiles);
			log.info("邮件发送成功.");
		} catch (Exception ex) {
			log.info("发送失败.");
			log.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
}
