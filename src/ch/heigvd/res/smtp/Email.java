package ch.heigvd.res.smtp;

/**
 * @author Benjamin Schubert and Basile Vu
 *
 * This class represents the different parts of an email
 */
public class Email {
    /**
     * This is the subject of the email
     */
    public final String subject;

    /**
     * This is the content of the email
     */
    public final String content;

    /**
     * This is the header of the email (for instance for sending html emails
     */
    public final String header;

    /**
     * Creates a new email
     *
     * @param subject: subject of the email
     * @param content: content of the email
     * @param header: header of the email
     */
    public Email(String subject, String content, String header) {
        this.subject = subject;
        this.content = content;
        this.header = header;
    }
}
