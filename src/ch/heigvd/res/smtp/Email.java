package ch.heigvd.res.smtp;

/**
 * @author Benjamin Schubert and Basile Vu
 */
public class Email {
    public final String subject;
    public final String content;
    public final String header;

    public Email(String subject, String content, String header) {
        this.subject = subject;
        this.content = content;
        this.header = header;
    }
}
