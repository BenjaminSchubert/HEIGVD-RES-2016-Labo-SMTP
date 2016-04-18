package ch.heigvd.res.smtp.emailMessageParser;

import ch.heigvd.res.smtp.Email;

import java.util.ArrayList;

/**
 * @author Benjamin Schubert and Basile Vu
 */
public class EmailListFactory {
    public static ArrayList<Email> getEmails(String file) {
        if(file.endsWith(".email")) {
            return new DefaultEmailParser(file).parse();
        } else {
            throw new RuntimeException("Unkown file extension");
        }
    }
}
