package ch.heigvd.res.smtp.emailMessageParser;

import ch.heigvd.res.smtp.Email;

import java.util.ArrayList;

/**
 * @author Benjamin Schubert and Basile Vu
 */
public class EmailMessageParserFactory {
    public static ArrayList<Email> getEmails(String file) {
        if(file.endsWith(".email")) {
            return new CustomEmailParser(file).parse();
        } else {
            throw new RuntimeException("Unkown file extension");
        }
    }
}
