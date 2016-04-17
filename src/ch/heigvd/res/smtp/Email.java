package ch.heigvd.res.smtp;

/**
 * @author Benjamin Schubert and Basile Vu
 */
public class Email {
    public final String subject;
    public final String content;

    public Email(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
}
