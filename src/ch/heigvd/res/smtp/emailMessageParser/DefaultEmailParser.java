package ch.heigvd.res.smtp.emailMessageParser;

import ch.heigvd.res.smtp.Email;

import java.io.*;
import java.util.ArrayList;

/**
 * @author Benjamin Schubert and Basile Vu
 */
public class DefaultEmailParser extends BaseEmailParser {
    private String file;
    private static final String subject = "[Ss]ubject( *)?(:)?( *)?";
    private static final String header = "[Tt]ype( *)?(:)?( *)?";

    public DefaultEmailParser(String file) {
        this.file = file;
    }

    @Override
    public ArrayList<Email> parse() {
        ArrayList<Email> emails = new ArrayList<>();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(this.file)))) {
            String line;
            String currentEmailSubject = null;
            String currentEmailHeader = null;
            String currentEmailContent = "";

            while((line = in.readLine()) != null) {
                if(line.matches(subject + ".*")) {
                    currentEmailSubject = line.replaceAll(subject, "");
                }
                else if(line.matches(header + ".*")) {
                    if(line.replaceAll(header, "").toLowerCase().trim().matches("html")) {
                        currentEmailHeader = "Mime-Version: 1.0\r\n";
                        currentEmailHeader += "Content-Type: text/html;\r\n";
                    }
                }
                else if(line.startsWith("#")) {}
                else if(!line.trim().startsWith("====")) {
                    currentEmailContent += "\n" + line;
                }
                else {
                    emails.add(new Email(currentEmailSubject, currentEmailContent, currentEmailHeader));
                    currentEmailContent = "";
                    currentEmailSubject = null;
                    currentEmailHeader = null;
                }
            }
            if(currentEmailHeader != null || currentEmailSubject != null ||
                    currentEmailContent.matches(".*[A-Za-z0-9].*")) {
                emails.add(new Email(currentEmailSubject, currentEmailContent, currentEmailHeader));
            }
        } catch (FileNotFoundException e) {
            System.err.println(String.format("Could not find email file %s", file));
            System.exit(4);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return emails;
    }
}