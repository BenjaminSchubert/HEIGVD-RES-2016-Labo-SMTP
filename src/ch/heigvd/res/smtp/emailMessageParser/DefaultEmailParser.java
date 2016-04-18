package ch.heigvd.res.smtp.emailMessageParser;

import ch.heigvd.res.smtp.Email;

import java.io.*;
import java.util.ArrayList;

/**
 * @author Benjamin Schubert and Basile Vu
 */
public class DefaultEmailParser extends BaseEmailParser {
    private String file;

    public DefaultEmailParser(String file) {
        this.file = file;
    }

    @Override
    public ArrayList<Email> parse() {
        ArrayList<Email> emails = new ArrayList<>();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(this.file)))) {
            String emailLine;
            String currentEmailSubject = null;
            String currentEmailContent = "";

            while ((emailLine = in.readLine()) != null) {
                if (!emailLine.startsWith("====")) {
                    if(currentEmailSubject == null) {
                        currentEmailSubject = emailLine.replaceAll("Subject( *)?(:)?( *)?", "");
                    } else {
                        currentEmailContent += "\n" + emailLine;
                    }
                } else {
                    emails.add(new Email(currentEmailSubject, currentEmailContent));
                    currentEmailContent = "";
                    currentEmailSubject = null;
                }
            }
            emails.add(new Email(currentEmailSubject, currentEmailContent));
        } catch (FileNotFoundException e) {
            System.err.println(String.format("Could not find email file %s", file));
            System.exit(4);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return emails;
    }
}