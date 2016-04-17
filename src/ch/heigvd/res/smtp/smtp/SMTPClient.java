package ch.heigvd.res.smtp.smtp;

import ch.heigvd.res.smtp.Email;
import ch.heigvd.res.smtp.groups.SpamGroup;

import java.io.*;

/**
 * @author Benjamin Schubert and Basile Vu
 */
public class SMTPClient {
    private final BufferedReader input;
    private final BufferedWriter output;
    private boolean authenticated = false;

    SMTPClient(BufferedReader input, BufferedWriter output) {
        this.input = input;
        this.output = output;
    }

    private void check_data(String s, String message) throws IOException {
        String data = input.readLine();
        System.out.println(data);
        if(!data.contains(s)) {
            throw new IOException(message + ": Received " + data);
        }
    }

    private void check_hello() throws IOException {
        check_data("220", "Likely not a SMTP Server");
        output.write("EHLO anonymous.com\r\n");
        output.flush();

        // comsume input we don't care about
        System.out.println(input.readLine());
        System.out.println(input.readLine());
        check_data("Ok", "Didn't receive message saying I can start sending email");
    }

    private void sendHeader(SpamGroup group) throws IOException {
        output.write(String.format("MAIL FROM: %s\r\n", group.getSender()));
        for(String rcpt: group.getRecipients()) {
            output.write(String.format("RCPT TO: %s\r\n", rcpt));
        }
        output.flush();

        check_data("Ok", "Couldn't validate sender");

        for(String ignored : group.getRecipients()) {
            check_data("Ok", "Couldn't validate receiver");
        }

    }

    private void sendBody(SpamGroup group, Email email) throws IOException {
        output.write("DATA\r\n");
        output.write(String.format("From: %s\r\n", group.getSender()));

        String users = "";
        for(String rcpt: group.getRecipients()) {
            users += rcpt + ", ";
        }
        output.write(String.format("To: %s\r\n", users));
        output.write(String.format("Subject: %s\r\n\r\n", email.subject));
        output.write(email.content);
        output.write("\r\n.\r\n");
        output.flush();

        // consume unused data
        System.out.println(input.readLine());
        check_data("Ok", "Couldn't send mail body");
    }

    public void sendEmail(SpamGroup group, Email email) throws IOException {
        if(!authenticated) {
            check_hello();
            authenticated = true;
        }
        sendHeader(group);
        sendBody(group, email);
    }
}
