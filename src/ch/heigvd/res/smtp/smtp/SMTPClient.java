package ch.heigvd.res.smtp.smtp;

import ch.heigvd.res.smtp.Email;
import ch.heigvd.res.smtp.groups.SpamGroup;

import java.io.*;

/**
 * @author Benjamin Schubert and Basile Vu
 */
public class SMTPClient implements Closeable {
    protected final BufferedReader input;
    protected final BufferedWriter output;
    private boolean authenticated = false;

    SMTPClient(BufferedReader input, BufferedWriter output) throws IOException {
        this.input = input;
        this.output = output;
    }

    public void authenticate() throws IOException {
        checkData("220", "Likely not a SMTP Server");
        output.write("EHLO anonymous.com\r\n");
        output.flush();

        // comsume input we don't care about
        String in;
        while((in = input.readLine()).startsWith("250-")) {}
        if(!in.startsWith("250")) {
            throw new IOException(String.format("authentication not successful. Got %s", in));
        }
    }

    protected void checkData(String s, String message) throws IOException {
        String data = input.readLine();
        if(!data.toLowerCase().contains(s.toLowerCase())) {
            throw new IOException(message + ": Received " + data);
        }
    }

    private void sendHeader(SpamGroup group) throws IOException {
        output.write(String.format("MAIL FROM: %s\r\n", group.getSender()));
        for(String rcpt: group.getRecipients()) {
            output.write(String.format("RCPT TO: %s\r\n", rcpt));
        }
        output.flush();

        checkData("250", "Couldn't validate sender");

        for(String ignored : group.getRecipients()) {
            checkData("250", "Couldn't validate receiver");
        }
    }

    private void sendBody(SpamGroup group, Email email) throws IOException {
        output.write("DATA\r\n");
        output.flush();
        checkData("354", "Didn't get authorization to send email");

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

        checkData("250", "Couldn't send mail body");
    }

    public void sendEmail(SpamGroup group, Email email) throws IOException {
        if(!authenticated) {
            authenticate();
            authenticated = true;
        }
        sendHeader(group);
        sendBody(group, email);
    }

    @Override
    public void close() throws IOException {
        output.write("quit\r\n");
        output.flush();
        input.readLine();
        input.readLine();
        output.close();
        input.close();
    }
}
