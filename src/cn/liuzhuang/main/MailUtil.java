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
 * �ʼ����͹�����
 */
public class MailUtil {

	private static final Logger log = Logger.getLogger(MailUtil.class);
	// �����ļ��������·��
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
	 * ��ʽʹ�õ�Mail����
	 * 
	 * �������壺
	 * args[0]:�ʼ�����;
	 * args[1]:�ʼ�����;
	 * args[2]:�ʼ����������ļ��ľ���·��;
	 * 
	 * ִ��Demo: java -jar mailSender.jar �ʼ�����  �ʼ����� ����1(Ҫʹ�þ���·��)
	 *     ע��: ����ж���1�������ĳ��ϣ�ͨ����config.properties���޸�attach.files=c:/a.txt,c:/b.txt�����;
	 * 
	 */
	public static void main(String[] args) {

		if (args.length < 3) {
			System.out.println("������������ȷ,����δִ��.");
			System.exit(0);
		}

		// ���������ļ�
		Map<String, String> config = PropertiesUtil.readProperties(filePath);
		String host = config.get("host");// �ʼ���������SMTP��
		String port = config.get("port");// �����������PORT��
		String mailFrom = config.get("mailFrom");// ������
		String password = config.get("password");// ����
		String mailTo = config.get("mailTo");// �ռ���

		String subject = "�����ʼ�New";
		String message = "����һ����javaMail�Զ������Ĳ����ʼ�������ظ���";
		subject = args[0];
		message = args[1];

		if (!datasCheck(host, mailFrom, mailTo))
			return;
		String[] mailToArgs = mailTo.split(",");

		// ����
		List<String> attachList = new ArrayList<String>();
		attachList.add(args[2]);

		String[] attachs = ((String) config.get("attach.files")).split(",");
		for (String vle : attachs) {
			attachList.add(vle);
		}
		String[] attachFiles = (String[]) attachList.toArray(new String[0]);

		try {
			log.info("׼���ʼ�����......");
			log.info("�ʼ���������" + mailTo);
			sendEmailWithAttachments(host, port, mailFrom, password,
					mailToArgs, subject, message, attachFiles);
			log.info("�ʼ����ͳɹ�.");
		} catch (Exception ex) {
			log.info("����ʧ��.");
			log.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
}
