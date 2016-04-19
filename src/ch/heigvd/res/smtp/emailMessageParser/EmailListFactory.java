package ch.heigvd.res.smtp.emailMessageParser;

import ch.heigvd.res.smtp.Email;

import java.util.ArrayList;

/**
 * @author Benjamin Schubert and Basile Vu
 *
 * Factory class to handle the creation of a list of emails
 */
public class EmailListFactory {
    /**
     * Given the file, tries to parse it and return a list of emails.
     *
     * @param file: the file to parse to get the emails
     * @return emails to send
     * @throws RuntimeException if the file extension is unknown
     */
    public static ArrayList<Email> getEmails(String file) {
        if(file.endsWith(".email")) {
            return new DefaultEmailParser(file).parse();
        } else {
            throw new RuntimeException("Unkown file extension");
        }
    }
}
