package ch.heigvd.res.smtp.emailMessageParser;

import ch.heigvd.res.smtp.Email;

import java.io.*;
import java.util.ArrayList;

import static com.sun.org.apache.xml.internal.serialize.OutputFormat.Defaults.Encoding;

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

            while ((line = in.readLine()) != null) {
                if(line.matches(subject + ".*")) {
                    currentEmailSubject = line.replaceAll(subject, "");
                }
                else if(line.matches(header + ".*")) {
                    if(line.replaceAll(header, "").toLowerCase().trim().matches("html")) {
                        currentEmailHeader = "Mime-Version: 1.0\r\n";
                        currentEmailHeader += "Content-Type: text/html; charset=\"UTF-8\"\r\n";
                        currentEmailHeader += "Content-Transfer-Encoding: 7bit";
                    }
                }
                else if (!line.startsWith("====")) {
                    currentEmailContent += "\n" + line;
                } else {
                    emails.add(new Email(currentEmailSubject, currentEmailContent, currentEmailHeader));
                    currentEmailContent = "";
                    currentEmailSubject = null;
                    currentEmailHeader = null;
                }
            }
            emails.add(new Email(currentEmailSubject, currentEmailContent, currentEmailHeader));
        } catch (FileNotFoundException e) {
            System.err.println(String.format("Could not find email file %s", file));
            System.exit(4);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return emails;
    }
}