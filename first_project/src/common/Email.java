package common;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
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

/**
 * 회원가입 인삿말 보내기, 네이버 이메일
 * 이메일 설정에서 POP3/IMAP ->사용으로 변경 필요 
 * @author 송지은
 */
public class Email {
	private String title;		// 이메일 제목
	private String content;		// 이메일 내용
	private String email;		// 수신자 이메일
	private String filePath;	// 첨부파일 경로
	//".\\Java-Mail.txt"

	public Email(String title, String content, String email, String filePath){
		this.title = title;
		this.content = content;
		this.email = email;
		this.filePath = filePath;
	}
	
	public void sendNaver(){
		String host = "smtp.naver.com";
        final String user = "asdf1234@naver.com"; //계정메일
        final String password  = "******"; // 계정비번

       /**
       * Property에 SMTP 서버를 설정한다.
       * mail.smtp.host : 이메일 발송을 처리해줄 SMTP 서버를 나타낸다
       * naver를 SMTP 서버로 사용할 경우에는 smtp.naver.com으로 설정한다
       */
        // Get the session object
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");

		/**
		 * SMTP서버 정보와 사용자 정보를 기반으로 Session 클래스의 인스턴스를 생성
		 */
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(user, password);
					}
				});

      /**
       * Message 클래스의 객체를 이용하여 수신자와 내용, 제목의 메세지를 작성하고,
       * Transport를 이용하여 작성한 메세지를 전달
       */
        MimeMessage message = null;
        // Compose the message
		try {
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					email));

			// Subject
			message.setSubject(title);

			// Text
			message.setText(content);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
        
        
        //첨부 파일
        if(filePath!=null){
        	BodyPart mbody = new MimeBodyPart(); 
        	Multipart multi = new MimeMultipart();
        	File file = new File(filePath); // 첨부파일 위치 설정
        	FileDataSource fds = new FileDataSource(file);//캡슐
        	
        	try {
        		mbody.setDataHandler(new DataHandler(fds));
        		mbody.setFileName(file.getName());
        		multi.addBodyPart(mbody);
        		message.setContent(multi);
        	} catch (MessagingException e) {
        		System.out.println("첨부된 파일을 확인하세요.");
        		e.printStackTrace();
        		return;
        	}
        	
        }
        // send the message
        try {
			Transport.send(message);
			System.out.println("메일 발송이 되었습니다.");
		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("메세지 전송 실패");
		}
	}
}

